SELECT R.id                           AS rua_id,
       R.numero                       AS rua,
       P.id                           AS predio_id,
       P.numero                       AS predio,
       P.lado,
       N.id                           AS nivel_id,
       N.numero                       AS nivel,
       N.altura,
       N.tipo_nivel,
       L.layout,
       L.total,
       A.id                           AS apto_id,
       A.numero                       AS apto,
       A.tipo_palet,
       A.tipo_altura,
       E.id                           AS endereco_id,
       E.tipo_endereco,
       E.observacao,
       E.localizacao,
       IFNULL(S.saldo_confirmado, 0)  AS saldo_confirmado,
       IFNULL(S.saldo_nconfirmado, 0) AS saldo_nconfirmado
FROM ruas                                  AS R
         INNER JOIN predios                AS P
                    ON R.id = P.rua_id
         INNER JOIN niveis                 AS N
                    ON P.id = N.predio_id
         INNER JOIN aptos                  AS A
                    ON N.id = A.nivel_id
         LEFT JOIN  enderecos              AS E
                    ON A.endereco_id = E.id
         LEFT JOIN  layout_niveis          AS L
                    ON L.nivel_id = N.id
         LEFT JOIN  (SELECT endereco_id,
                            SUM(saldo_confirmado)  AS saldo_confirmado,
                            SUM(saldo_nconfirmado) AS saldo_nconfirmado
                     FROM saldos
                     GROUP BY endereco_id) AS S
                    USING (endereco_id)