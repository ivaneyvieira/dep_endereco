CREATE OR REPLACE VIEW ocupacao_enderecos AS
  SELECT A.nivel_id, A.id as apto_id, N.predio_id, P.rua_id, P.lado, A.numero, A.endereco_id, A.tipo_palet, A.tipo_altura,
                     IF(SUM(S.saldo_confirmado) <> 0 OR SUM(S.saldo_nconfirmado) <> 0, 'S', 'N') as ocupado
  FROM aptos AS A
    INNER JOIN niveis AS N
      ON N.id = A.nivel_id
    INNER JOIN predios AS P
      ON P.id = N.predio_id
    LEFT JOIN enderecos AS E
      ON E.id = A.endereco_id
    LEFT join saldos AS S
      ON E.id = S.endereco_id
  WHERE N.tipo_nivel = 'PULMAO'
  GROUP BY A.id;
