UPDATE saldos
SET saldo_confirmado = 0,
    saldo_nconfirmado =0
WHERE produto_id = :idProduto;

DROP TEMPORARY TABLE IF EXISTS saldoRecalculado;
CREATE TEMPORARY TABLE saldoRecalculado
select produto_id, endereco_id, SUM(qtMovConf) as saldoConf, SUM(qtMovNConf) as saldoNConf,
MAX(ultSaida) as ultSaida, MAX(ultEntrada) AS ultEntrada
from (
select produto_id, endereco_s_id as endereco_id,
  SUM(IF(T.confirmacao = 1, -T.quant_mov, 0)) as qtMovConf,
  SUM(IF(T.confirmacao = 1, 0, -T.quant_mov)) as qtMovNConf,
  MAX(IF(T.confirmacao = 1, T.data_hora_mov, null)) as ultSaida,
  NULL                                              as ultEntrada
  from movprodutos AS M
    inner join transferencias AS T
      ON M.id = T.mov_produto_id
where produto_id = :idProduto
GROUP BY produto_id, endereco_s_id
UNION
select produto_id, endereco_e_id,
  SUM(IF(T.confirmacao = 1, T.quant_mov, 0)) as qtMovConf,
  SUM(IF(T.confirmacao = 1, 0, T.quant_mov)) as qtMovNConf,
  NULL                                               as ultSaida,
  MAX(IF(T.confirmacao = 1, T.data_hora_mov, null))  as ultEntrada
  from movprodutos AS M
    inner join transferencias AS T
      ON M.id = T.mov_produto_id
where produto_id = :idProduto
GROUP BY produto_id, endereco_e_id
) as D
group BY produto_id, endereco_id;

UPDATE saldos AS S
  INNER JOIN saldoRecalculado AS R
    USING(produto_id, endereco_id)
SET S.saldo_confirmado = R.saldoConf,
  S.saldo_nconfirmado = R.saldoNConf,
  S.ult_saida = R.ultSaida,
  S.ult_entrada = R.ultEntrada;

INSERT IGNORE INTO saldos(produto_id, endereco_id, capacidade, saldo_confirmado, saldo_nconfirmado,
                   version, created_at, updated_at)
SELECT produto_id, endereco_id, 0 as capacidade, saldoConf as saldo_confirmado, saldoNConf as saldo_nconfirmado,
  0 as version, current_timestamp as created_at, current_timestamp as updated_at
FROM saldoRecalculado;