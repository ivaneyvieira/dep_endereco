SELECT C.cargano, cast(C.dataEntrega AS DATE) AS data, C.xanoNfr AS xano, A.custno,
       CT.name AS cliente, N.prdno, N.grade, N.qtty / 1000 AS quant, N.storenoStk, N.c1 AS marca,
       N.s1 AS destinoCarga, N.c2 AS enderecoCarga
FROM sqldados.eord           AS E
  INNER JOIN sqldados.ctadd  AS A
               ON E.custno = A.custno AND E.custno_addno = A.seqno
  INNER JOIN sqldados.route  AS R
               ON R.no = A.routeno
  INNER JOIN sqldados.awnfr  AS C
               ON C.storenoNfr = E.storeno AND C.ordno = E.ordno
  INNER JOIN sqldados.custp  AS CT
               ON CT.no = C.custno
  INNER JOIN sqldados.nfrprd AS N
               ON N.xano = C.xanoNfr AND N.pdvno = C.pdvnoNfr AND N.storeno = C.storenoNfr
WHERE C.cargano = :cargano
  AND C.storeno = 10
  AND C.statusCarga = 0