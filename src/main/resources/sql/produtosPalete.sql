select T.quant_mov
from transferencias AS T
  inner join enderecos AS ES
    ON ES.id = T.endereco_s_id
       AND ES.tipo_endereco = 'RECEBIMENTO'
  inner join enderecos AS EE
    ON EE.id = T.endereco_e_id
       AND EE.tipo_endereco = 'DEPOSITO'
       AND EE.tipo_nivel = 'PULMAO'
  inner join movprodutos AS M
    ON M.id = T.mov_produto_id
  inner join produtos AS P
    ON P.id = M.produto_id
WHERE prdno = LPAD(TRIM(:prdno), 16, '0')
GROUP BY quant_mov
ORDER BY COUNT(*) desc, quant_mov desc
LIMIT 1