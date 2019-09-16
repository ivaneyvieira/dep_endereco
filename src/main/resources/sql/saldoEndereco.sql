select
    IFNULL(SUM(saldo_confirmado), 0) as saldo_confirmado,
    IFNULL(SUM(saldo_nconfirmado), 0) as saldo_nconfirmado
from saldos
where endereco_id = ?