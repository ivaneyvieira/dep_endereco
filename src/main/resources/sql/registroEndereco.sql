SELECT R.id                           AS rua_id,
       R.numero                       AS rua,
       P.id                           AS predio_id,
       P.numero                       AS predio,
       P.lado,
       N.id                           AS nivel_id,
       N.numero                       AS nivel,
       N.altura,
       N.tipo_nivel,
       A.id                           AS apto_id,
       A.numero                       AS apto,
       A.tipo_palet,
       A.tipo_altura,
       E.id                           AS endereco_id,
       E.tipo_endereco,
       E.observacao,
       E.localizacao
FROM ruas                                  AS R
         INNER JOIN predios                AS P
                    ON R.id = P.rua_id
         INNER JOIN niveis                 AS N
                    ON P.id = N.predio_id
         INNER JOIN aptos                  AS A
                    ON N.id = A.nivel_id
         LEFT JOIN  enderecos              AS E
                    ON A.endereco_id = E.id
