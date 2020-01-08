SELECT EE.id AS endereco_id
FROM transferencias      AS T
  INNER JOIN enderecos   AS EE
               ON T.endereco_e_id = EE.id
  INNER JOIN enderecos   AS ES
               ON T.endereco_s_id = ES.id
  INNER JOIN movprodutos AS M
               ON M.id = T.mov_produto_id
  INNER JOIN produtos    AS P
               ON P.id = M.produto_id
WHERE EE.tipo_nivel = 'PICKING'
  AND ES.tipo_nivel = 'PULMAO'
  AND P.id = ?
  AND EE.localizacao NOT LIKE 'E%'
ORDER BY T.data_hora_mov DESC
LIMIT 1