DROP TABLE IF EXISTS A1;
CREATE TEMPORARY TABLE A1
(
  primary keY (tipo_altura)
)
    select
      'ALTA' as tipo_altura,
      22     as peso
    union
    select
      'MEDIA',
      18
    union
    select
      'BAIXA',
      10;

DROP TABLE IF EXISTS A2;
CREATE TEMPORARY TABLE A2
    SELECT *
    from A1
    where tipo_altura = :tipoAltura;

DROP TABLE IF EXISTS A3;
CREATE TEMPORARY TABLE A3
(
  PRIMARY KEY (tipo_altura)
)
    select
      A1.tipo_altura,
      A1.peso - A2.peso + 1 as peso
    FROM A1
      INNER JOIN A2
        ON A1.peso >= A2.peso;


DROP TABLE IF EXISTS T;
CREATE TEMPORARY TABLE T
(
  PRIMARY KEY (rua, predio, nivel, apto)
)
    select
      E.id                                                                      as idEndereco,
      r.id                                                                      as idRua,
      CONCAT(r.numero, '-', p.numero, '-', n.numero, '-', a.numero)             as endereco,
      tipo_palet,
      tipo_altura,
      r.numero                                                                  as rua,
      p.numero                                                                  as predio,
      n.numero                                                                  as nivel,
      a.numero                                                                  as apto,
      IF(SUM(saldo_confirmado) > 0 OR SUM(saldo_nconfirmado) > 0, tipo_palet, '.') as ocupacao,
      A3.peso
    from enderecos AS E
      inner join aptos AS a
        ON a.endereco_id = E.id
      inner join niveis AS n
        ON n.id = a.nivel_id
      inner join A3
      using (tipo_altura)
      inner join predios AS p
        ON p.id = n.predio_id
      inner join ruas AS r
        ON r.id = p.rua_id
      left join saldos AS S
        on E.id = S.endereco_id
    where n.tipo_nivel = 'PULMAO'
          AND rua_id IN (:ruas)
          AND (p.lado = :lado OR :lado = '')
    GROUP BY endereco;

DROP TABLE IF EXISTS T2;
CREATE TEMPORARY TABLE T2
(
  INDEX (paletes)
)
    select *
    from (
           select
             'P'  as paletes,
             '02' as apto,
             'P'  as tipoPalet,
             1    as peso
           from dual
           union
           select
             'P'  as paletes,
             '02' as apto,
             'T',
             1    as peso
           from dual
           union
           select
             'P'  as paletes,
             '02' as apto,
             'G',
             2    as peso
           from dual

           union
           select
             '.P' as paletes,
             '01' as apto,
             'P',
             4    as peso
           from dual
           union
           select
             '.P' as paletes,
             '03' as apto,
             'P',
             6    as peso
           from dual
           union
           select
             '..P' as paletes,
             '02'  as apto,
             'P',
             4     as peso
           from dual
           union
           select
             '..P' as paletes,
             '01'  as apto,
             'T',
             6     as peso
           from dual
           union
           select
             '..P' as paletes,
             '01'  as apto,
             'G',
             2     as peso
           from dual

           union
           select
             '.G' as paletes,
             '01' as apto,
             'G',
             1    as peso
           from dual
           union
           select
             '.G' as paletes,
             '01' as apto,
             'P',
             2    as peso
           from dual

           union
           select
             'G'  as paletes,
             '02' as apto,
             'G',
             1    as peso
           from dual
           union
           select
             'G'  as paletes,
             '02' as apto,
             'P',
             2    as peso
           from dual

           union
           select
             'T'  as paletes,
             '02' as apto,
             'P',
             1    as peso
           from dual
           union
           select
             '.T' as paletes,
             '01' as apto,
             'P',
             1    as peso
           from dual

           union
           select
             ''   as paletes,
             '01' as apto,
             'P',
             1    as peso
           from dual
           union
           select
             ''   as paletes,
             '01' as apto,
             'G',
             1    as peso
           from dual
           union
           select
             ''   as paletes,
             '01' as apto,
             'T',
             1    as peso
           from dual
           union
           select
             ''   as paletes,
             '01' as apto,
             'X',
             1    as peso
           from dual
         ) as D
    where tipoPalet = :tipoPalet;

DROP TABLE IF EXISTS T3;
CREATE TEMPORARY TABLE T3
(
  INDEX (paletes)
)
    select
      rua,
      predio,
      nivel,
      CAST(TRIM(TRAILING '.' FROM GROUP_CONCAT(ocupacao order by apto separator '')) AS CHAR) as paletes
    from T
    GROUP BY rua, predio, nivel;

DROP TABLE IF EXISTS T4;
CREATE TEMPORARY TABLE T4
select
  E.*,
  T.peso * T2.peso as recomendado,
  T.peso as peso2,
  T2.peso,
  T.tipo_altura,
  paletes,
  T.tipo_palet
from T3
  inner join T2
  USING (paletes)
  inner join T
  USING (rua, predio, nivel, apto)
  inner join enderecos AS E
    ON E.id = T.idEndereco
GROUP BY E.id
order by recomendado, endereco;

SELECT id, tipo_endereco, observacao, localizacao, tipo_nivel, created_at, updated_at, version
  FROM T4