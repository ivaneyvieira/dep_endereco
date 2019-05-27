select qtty_varejo/1000 as quant
from sqldados.stk
where storeno = 10
  and prdno   = :prdno
  and grade   = :grade

