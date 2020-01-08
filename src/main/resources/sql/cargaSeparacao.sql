SELECT C.cargano, cast(C.dataEntrega AS DATE) AS data, C.xanoNfr AS xano, A.custno,
       CT.name AS cliente, N.prdno, N.grade, N.qtty / 1000 AS quant, N.storenoStk
from sqldados.eord AS E
         inner join sqldados.ctadd AS A
                    ON E.custno = A.custno
                        AND E.custno_addno = A.seqno
         inner join sqldados.route AS R
                    on R.no = A.routeno
         inner join sqldados.awnfr AS C
                    on C.storenoNfr = E.storeno
                        and C.ordno = E.ordno
         inner join sqldados.custp as CT
                    on CT.no = C.custno
         inner join sqldados.nfrprd AS N
                    ON N.xano = C.xanoNfr
                        and N.pdvno = C.pdvnoNfr
                        and N.storeno = C.storenoNfr
where C.cargano = :cargano
        AND C.storeno = 10
        AND C.statusCarga = 0