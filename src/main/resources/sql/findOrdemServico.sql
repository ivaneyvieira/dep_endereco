DO @numero := IFNULL((SELECT max(numero)
                      FROM ordensservico), 0);

INSERT INTO ordensservico (user_id, transferencia_id, numero,
                           data_hora, data_hora_conf, version, created_at, updated_at)
SELECT IFNULL(O.user_id, :user_id) AS user_id, IFNULL(O.transferencia_id, T.id) AS transferencia_id,
       IFNULL(@numero := @numero + 1, 0) AS numero,
       IFNULL(O.data_hora, current_timestamp) AS data_hora,
       IFNULL(O.data_hora_conf, current_timestamp) AS data_hora_conf,
       IFNULL(O.version, 0) AS version, IFNULL(O.created_at, current_timestamp) AS created_at,
       IFNULL(O.updated_at, current_timestamp) AS updated_at
FROM transferencias       AS T
  LEFT JOIN ordensservico AS O
              ON O.transferencia_id = T.id
WHERE O.id IS NULL
  AND T.confirmacao = '0'
