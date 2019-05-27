-- apply changes

update aptos set nivel_id = 0 where nivel_id is null;
alter table aptos modify nivel_id bigint not null;

update aptos set endereco_id = 0 where endereco_id is null;
alter table aptos modify endereco_id bigint not null;
alter table aptos drop index uq_aptos_nivel_id_numero;
alter table aptos add constraint uq_aptos_nivel_id_numero unique  (nivel_id,numero);

update kardec set procduto_id = 0 where procduto_id is null;
alter table kardec modify procduto_id bigint not null;

update movprodutos set movimentacao_id = 0 where movimentacao_id is null;
alter table movprodutos modify movimentacao_id bigint not null;

update movprodutos set produto_id = 0 where produto_id is null;
alter table movprodutos modify produto_id bigint not null;
alter table movprodutos drop index uq_movprodutos_produto_id_movimentacao_id;
alter table movprodutos add constraint uq_movprodutos_produto_id_movimentacao_id unique  (produto_id,movimentacao_id);

update niveis set predio_id = 0 where predio_id is null;
alter table niveis modify predio_id bigint not null;
alter table niveis drop index uq_niveis_predio_id_numero_tipo_nivel;
alter table niveis add constraint uq_niveis_predio_id_numero_tipo_nivel unique  (predio_id,numero,tipo_nivel);

update predios set rua_id = 0 where rua_id is null;
alter table predios modify rua_id bigint not null;
alter table predios drop index uq_predios_rua_id_numero;
alter table predios add constraint uq_predios_rua_id_numero unique  (rua_id,numero);

update saldos set endereco_id = 0 where endereco_id is null;
alter table saldos modify endereco_id bigint not null;

update saldos set produto_id = 0 where produto_id is null;
alter table saldos modify produto_id bigint not null;
alter table saldos drop index uq_saldos_endereco_id_produto_id;
alter table saldos add constraint uq_saldos_endereco_id_produto_id unique  (endereco_id,produto_id);

update transferencias set mov_produto_id = 0 where mov_produto_id is null;
alter table transferencias modify mov_produto_id bigint not null;

update transferencias set endereco_s_id = 0 where endereco_s_id is null;
alter table transferencias modify endereco_s_id bigint not null;
