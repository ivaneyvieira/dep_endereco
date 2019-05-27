DO @ORIGEM := :origem;
DO @TIPO := :tipo;
DO @DESTINO := :destino;

DROP TABLE IF EXISTS TORIGEM;
CREATE TEMPORARY TABLE TORIGEM
    SELECT *
    FROM enderecos
    WHERE localizacao = @ORIGEM
          AND tipo_nivel = @TIPO;

DROP TABLE IF EXISTS TDESTINO;
CREATE TEMPORARY TABLE TDESTINO
    SELECT *
    FROM enderecos
    WHERE localizacao = @DESTINO
          AND tipo_nivel = @TIPO;

DROP TABLE IF EXISTS TSALDO_ORIGEM;
CREATE TEMPORARY TABLE TSALDO_ORIGEM
    SELECT S.*
    FROM saldos AS S
      INNER JOIN TORIGEM AS E
        ON E.id = S.endereco_id
    WHERE saldo_confirmado > 0;

DO @CHAVE := (SELECT CONCAT('TR', LPAD(IFNULL(MAX(MID(chave, 3, 10)), 0) + 1, 8, '0'))
              FROM movimentacoes
              WHERE chave LIKE 'TR%');

INSERT IGNORE INTO movimentacoes (chave, documento, data, observacao, tipo_mov,
                                  version, created_at, updated_at)
VALUES (@CHAVE, @CHAVE, current_date, CONCAT('TRANSFERENCIA DE ', @ORIGEM, ' PARA ', @DESTINO), "ENTRADA",
        0, current_timestamp, current_timestamp);

DO @ID_MOV := (SELECT id
               FROM movimentacoes
               WHERE chave = @CHAVE);

INSERT INTO movprodutos (produto_id, movimentacao_id, quant_can, quant_mov,
                         quant_palete, version, created_at, updated_at)
  SELECT
    produto_id,
    @ID_MOV         AS idMovimentacao,
    0               AS quantCan,
    saldo_confirmado AS quantMov,
    0               AS quantPalete,
    0,
    current_timestamp,
    current_timestamp
  FROM TSALDO_ORIGEM;

INSERT INTO transferencias (endereco_e_id, endereco_s_id, mov_produto_id, quant_mov, observacao,
                            data_hora_mov, confirmacao, version, created_at, updated_at)
  SELECT
    D.id                                                     AS idEnderecoEnt,
    O.id                                                     AS idEnderecoSai,
    M.id                                                     AS idMovProduto,
    quant_mov,
    CONCAT('TRANSFERENCIA DE ', @ORIGEM, ' PARA ', @DESTINO) AS observacao,
    current_timestamp                                        AS dataHoraMov,
    1                                                        AS confirmacao,
    0,
    current_timestamp,
    current_timestamp
  FROM TORIGEM AS O, TDESTINO AS D, movprodutos AS M
  WHERE movimentacao_id = @ID_MOV;

DO @TIPO := (SELECT MAX(tipo_palet)
             FROM aptos AS A
               INNER join TORIGEM AS O
                 ON A.endereco_id = O.id);

UPDATE aptos AS A
  INNER join TDESTINO AS D
    ON A.endereco_id = D.id
SET A.tipo_palet = @TIPO
