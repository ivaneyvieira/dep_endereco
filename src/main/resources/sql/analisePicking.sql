
drop table if exists T;
create temporary table T
select S.endereco_id, S.produto_id, E.tipo_endereco, E.localizacao, E.tipo_nivel,
       P.prdno, P.grade, P.nome,
       S.saldo_confirmado, S.ult_entrada
from saldos AS S
         inner join enderecos AS E
                    ON S.endereco_id = E.id
         inner join produtos AS P
                    ON P.id = S.produto_id
where S.saldo_confirmado > 0
        and localizacao not like 'EXPED%'
        and localizacao not like '00%'
order by tipo_nivel, ult_entrada;

drop table if exists T_tem_pk;
create temporary table T_tem_pk
select * from T
where tipo_nivel = 'PICKING'
group by produto_id;

drop table if exists T_n_tem_pk;
create temporary table T_n_tem_pk
select * from T
where produto_id not in (select produto_id from T_tem_pk)
group by produto_id;