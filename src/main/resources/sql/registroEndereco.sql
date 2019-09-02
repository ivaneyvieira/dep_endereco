select R.id as rua_id, R.numero as rua,
       P.id as predio_id, P.numero as predio, P.lado,
       N.id AS nivel_id, N.numero as nivel, N.altura, N.tipo_nivel,
       A.id as apto_id, A.numero as apto, A.tipo_palet, A.tipo_altura,
       E.id as endereco_id, E.tipo_endereco, E.observacao, E.localizacao
from ruas AS R
         inner join predios AS P
                    ON R.id = P.rua_id
         inner join niveis AS N
                    ON P.id = N.predio_id
         inner join aptos AS A
                    ON N.id = A.nivel_id
         left join enderecos AS E
                   ON A.endereco_id = E.id