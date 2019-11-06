drop table if exists T;
create temporary table T
(PRIMARY KEY(prdno, grade, data_hora_mov))
select prdno, grade, data_hora_mov, EE.tipo_nivel, EE.localizacao
from transferencias AS T
  inner join movprodutos AS M
    ON T.mov_produto_id = M.id
  inner join produtos AS P
    ON M.produto_id = P.id
  inner join enderecos AS EE
    ON T.endereco_e_id = EE.id
  inner join enderecos AS ES
    ON T.endereco_S_id = ES.id
WHERE ES.localizacao <> 'RECEBIMENTO'
  AND prdno = :prdno
  AND grade = :grade
GROUP BY prdno, grade, data_hora_mov;

drop table if exists T2;
create temporary table T2
(PRIMARY KEY(prdno, grade))
select prdno, grade, MAX(data_hora_mov) AS data_hora_mov
from T
GROUP BY prdno, grade;


drop table if exists TSEP;
create temporary table TSEP
(PRIMARY KEY(prdno, grade))
select * from T
  inner join T2
    USING(prdno, grade, data_hora_mov);