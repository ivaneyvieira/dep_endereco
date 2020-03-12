SELECT C.cargano,
       cast(C.dataEntrega AS DATE)                  AS data,
       C.xanoNfr                                    AS xano,
       A.custno,
       CT.name                                      AS cliente,
       N.prdno,
       N.grade,
       N.qtty / 1000                                AS quant,
       N.storenoStk,
       N.c1                                         AS marca,
       N.s1                                         AS destinoCarga,
       N.c2                                         AS enderecoCarga,
       IFNULL(MAX(IF(O.auxShort3 = 6, 1, NULL)), 0) AS miv
FROM sqldados.eord            AS E
  INNER JOIN sqldados.ctadd   AS A
               ON E.custno = A.custno AND E.custno_addno = A.seqno
  INNER JOIN sqldados.route   AS R
               ON R.no = A.routeno
  INNER JOIN sqldados.awnfr   AS C
               ON C.storenoNfr = E.storeno AND C.ordno = E.ordno
  INNER JOIN sqldados.custp   AS CT
               ON CT.no = C.custno
  INNER JOIN sqldados.nfrprd  AS N
               ON N.xano = C.xanoNfr AND N.pdvno = C.pdvnoNfr AND N.storeno = C.storenoNfr
  LEFT JOIN  sqldados.nfmprdm AS O
               ON N.xano = O.auxLong3 AND N.pdvno = O.auxShort2 AND N.storeno = O.auxShort1
WHERE C.cargano = :cargano
  AND C.storeno = 10
  AND C.statusCarga = 0
GROUP BY C.cargano, N.storeno, N.prdno, N.xano, N.prdno, N.grade
