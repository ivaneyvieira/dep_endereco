SELECT endereco_id, E.localizacao, E.tipo_nivel,
       SUM(saldo_confirmado + saldo_nconfirmado) AS confirmado, MAX(T.data_hora_mov) AS ultEntrada,
       MIN(T.data_hora_mov) AS priEntrada
FROM saldos                 AS S
  INNER JOIN produtos       AS P
               ON S.produto_id = P.id
  INNER JOIN enderecos      AS E
               ON E.id = S.endereco_id
  INNER JOIN transferencias AS T
               ON T.endereco_e_id = S.endereco_id
  INNER JOIN movprodutos    AS M
               ON M.id = T.mov_produto_id AND M.produto_id = S.produto_id
WHERE P.id = ?
  AND tipo_nivel = 'PULMAO'
GROUP BY endereco_id
HAVING confirmado > 0
ORDER BY ultEntrada, confirmado
LIMIT 1