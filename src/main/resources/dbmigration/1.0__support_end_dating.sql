-- apply changes
create table aptos (
  id                            bigint auto_increment not null,
  version                       integer not null,
  numero                        varchar(2) not null,
  tipo_palet                    varchar(1) not null,
  tipo_altura                   varchar(5) not null,
  nivel_id                      bigint,
  endereco_id                   bigint,
  created_at                    datetime(6) not null,
  updated_at                    datetime(6) not null,
  constraint ck_aptos_tipo_palet check ( tipo_palet in ('P','G','T','X')),
  constraint ck_aptos_tipo_altura check ( tipo_altura in ('ALTA','MEDIA','BAIXA')),
  constraint uq_aptos_endereco_id unique (endereco_id),
  constraint uq_aptos_nivel_id_numero unique (nivel_id,numero),
  constraint pk_aptos primary key (id)
);

create table enderecos (
  id                            bigint auto_increment not null,
  version                       integer not null,
  tipo_endereco                 varchar(11) not null,
  observacao                    varchar(100) not null,
  localizacao                   varchar(25) not null,
  tipo_nivel                    varchar(7) not null,
  created_at                    datetime(6) not null,
  updated_at                    datetime(6) not null,
  constraint ck_enderecos_tipo_endereco check ( tipo_endereco in ('DEPOSITO','RECEBIMENTO','EXPEDICAO')),
  constraint ck_enderecos_tipo_nivel check ( tipo_nivel in ('PICKING','PULMAO')),
  constraint uq_enderecos_tipo_endereco_localizacao_tipo_nivel unique (tipo_endereco,localizacao,tipo_nivel),
  constraint pk_enderecos primary key (id)
);

create table kardec (
  id                            bigint auto_increment not null,
  version                       integer not null,
  procduto_id                   bigint,
  date                          date not null,
  tipo                          varchar(2) not null,
  operacao                      varchar(2) not null,
  es                            varchar(1) not null,
  quant                         decimal(10,4) not null,
  created_at                    datetime(6) not null,
  updated_at                    datetime(6) not null,
  constraint ck_kardec_tipo check ( tipo in ('CP','MG','NT')),
  constraint ck_kardec_operacao check ( operacao in ('CP','VD','TF','DV','OU')),
  constraint ck_kardec_es check ( es in ('E','S')),
  constraint pk_kardec primary key (id)
);

create table movprodutos (
  id                            bigint auto_increment not null,
  version                       integer not null,
  quant_can                     decimal(10,4) not null,
  quant_mov                     decimal(10,4) not null,
  quant_palete                  decimal(10,4) not null,
  movimentacao_id               bigint,
  produto_id                    bigint,
  created_at                    datetime(6) not null,
  updated_at                    datetime(6) not null,
  constraint uq_movprodutos_produto_id_movimentacao_id unique (produto_id,movimentacao_id),
  constraint pk_movprodutos primary key (id)
);

create table movimentacoes (
  id                            bigint auto_increment not null,
  version                       integer not null,
  chave                         varchar(20) not null,
  documento                     varchar(20) not null,
  data                          date not null,
  observacao                    varchar(100) not null,
  tipo_mov                      varchar(13) not null,
  created_at                    datetime(6) not null,
  updated_at                    datetime(6) not null,
  constraint ck_movimentacoes_tipo_mov check ( tipo_mov in ('ENTRADA','SAIDA','PICKING','TRANSFERENCIA')),
  constraint uq_movimentacoes_chave unique (chave),
  constraint pk_movimentacoes primary key (id)
);

create table niveis (
  id                            bigint auto_increment not null,
  version                       integer not null,
  numero                        varchar(2) not null,
  altura                        decimal(10,4) not null,
  tipo_nivel                    varchar(7) not null,
  predio_id                     bigint,
  created_at                    datetime(6) not null,
  updated_at                    datetime(6) not null,
  constraint ck_niveis_tipo_nivel check ( tipo_nivel in ('PICKING','PULMAO')),
  constraint uq_niveis_predio_id_numero_tipo_nivel unique (predio_id,numero,tipo_nivel),
  constraint pk_niveis primary key (id)
);

create table ordensservico (
  id                            bigint auto_increment not null,
  version                       integer not null,
  transferencia_id              bigint,
  numero                        bigint not null,
  data_hora                     datetime(6) not null,
  data_hora_conf                datetime(6),
  user_id                       bigint,
  created_at                    datetime(6) not null,
  updated_at                    datetime(6) not null,
  constraint uq_ordensservico_numero unique (numero),
  constraint pk_ordensservico primary key (id)
);

create table predios (
  id                            bigint auto_increment not null,
  version                       integer not null,
  numero                        varchar(2) not null,
  lado                          varchar(5) not null,
  rua_id                        bigint,
  created_at                    datetime(6) not null,
  updated_at                    datetime(6) not null,
  constraint ck_predios_lado check ( lado in ('PAR','IMPAR')),
  constraint uq_predios_rua_id_numero unique (rua_id,numero),
  constraint pk_predios primary key (id)
);

create table produtos (
  id                            bigint auto_increment not null,
  version                       integer not null,
  codbar                        varchar(16) not null,
  grade                         varchar(8) not null,
  nome                          varchar(40) not null,
  prdno                         varchar(16) not null,
  clno                          integer not null,
  vendno                        integer not null,
  custo                         decimal(10,4) not null,
  preco                         decimal(10,4) not null,
  quant_volumes                 integer not null,
  estoque_minimo                decimal(10,4) not null,
  created_at                    datetime(6) not null,
  updated_at                    datetime(6) not null,
  constraint uq_produtos_prdno_grade unique (prdno,grade),
  constraint pk_produtos primary key (id)
);

create table roles (
  id                            bigint auto_increment not null,
  version                       integer not null,
  name                          varchar(20) not null,
  tag                           varchar(25) not null,
  created_at                    datetime(6) not null,
  updated_at                    datetime(6) not null,
  constraint uq_roles_name unique (name),
  constraint pk_roles primary key (id)
);

create table ruas (
  id                            bigint auto_increment not null,
  version                       integer not null,
  numero                        varchar(2) not null,
  created_at                    datetime(6) not null,
  updated_at                    datetime(6) not null,
  constraint uq_ruas_numero unique (numero),
  constraint pk_ruas primary key (id)
);

create table saldos (
  id                            bigint auto_increment not null,
  version                       integer not null,
  capacidade                    decimal(10,4) not null,
  saldo_confirmado              decimal(10,4) not null,
  saldo_nconfirmado             decimal(10,4) not null,
  ult_saida                     date,
  ult_entrada                   date,
  endereco_id                   bigint,
  produto_id                    bigint,
  created_at                    datetime(6) not null,
  updated_at                    datetime(6) not null,
  constraint uq_saldos_endereco_id_produto_id unique (endereco_id,produto_id),
  constraint pk_saldos primary key (id)
);

create table transferencias (
  id                            bigint auto_increment not null,
  version                       integer not null,
  data_hora_mov                 datetime(6) not null,
  observacao                    varchar(100),
  quant_mov                     decimal(10,4) not null,
  confirmacao                   tinyint(1) default 0 not null,
  endereco_e_id                 bigint,
  mov_produto_id                bigint,
  endereco_s_id                 bigint,
  created_at                    datetime(6) not null,
  updated_at                    datetime(6) not null,
  constraint pk_transferencias primary key (id)
);

create table users (
  id                            bigint auto_increment not null,
  version                       integer not null,
  user_name                     varchar(20) not null,
  foto_perfil                   longblob not null,
  chapa                         varchar(10) not null,
  user_saci                     varchar(15) not null,
  first_name                    varchar(60) not null,
  last_name                     varchar(60) not null,
  title                         varchar(60) not null,
  passw                         varchar(60) not null,
  created_at                    datetime(6) not null,
  updated_at                    datetime(6) not null,
  constraint uq_users_user_name unique (user_name),
  constraint uq_users_chapa unique (chapa),
  constraint uq_users_user_saci unique (user_saci),
  constraint pk_users primary key (id)
);

create table users_roles (
  users_id                      bigint not null,
  roles_id                      bigint not null,
  constraint pk_users_roles primary key (users_id,roles_id)
);

create index ix_kardec_date on kardec (date);
create index ix_produtos_codbar on produtos (codbar);
create index ix_transferencias_mov_produto_id_endereco_e_id_endereco_s_1 on transferencias (mov_produto_id,endereco_e_id,endereco_s_id);
create index ix_aptos_nivel_id on aptos (nivel_id);
alter table aptos add constraint fk_aptos_nivel_id foreign key (nivel_id) references niveis (id) on delete restrict on update restrict;

alter table aptos add constraint fk_aptos_endereco_id foreign key (endereco_id) references enderecos (id) on delete restrict on update restrict;

create index ix_kardec_procduto_id on kardec (procduto_id);
alter table kardec add constraint fk_kardec_procduto_id foreign key (procduto_id) references produtos (id) on delete restrict on update restrict;

create index ix_movprodutos_movimentacao_id on movprodutos (movimentacao_id);
alter table movprodutos add constraint fk_movprodutos_movimentacao_id foreign key (movimentacao_id) references movimentacoes (id) on delete restrict on update restrict;

create index ix_movprodutos_produto_id on movprodutos (produto_id);
alter table movprodutos add constraint fk_movprodutos_produto_id foreign key (produto_id) references produtos (id) on delete restrict on update restrict;

create index ix_niveis_predio_id on niveis (predio_id);
alter table niveis add constraint fk_niveis_predio_id foreign key (predio_id) references predios (id) on delete restrict on update restrict;

create index ix_ordensservico_transferencia_id on ordensservico (transferencia_id);
alter table ordensservico add constraint fk_ordensservico_transferencia_id foreign key (transferencia_id) references transferencias (id) on delete restrict on update restrict;

create index ix_ordensservico_user_id on ordensservico (user_id);
alter table ordensservico add constraint fk_ordensservico_user_id foreign key (user_id) references users (id) on delete restrict on update restrict;

create index ix_predios_rua_id on predios (rua_id);
alter table predios add constraint fk_predios_rua_id foreign key (rua_id) references ruas (id) on delete restrict on update restrict;

create index ix_saldos_endereco_id on saldos (endereco_id);
alter table saldos add constraint fk_saldos_endereco_id foreign key (endereco_id) references enderecos (id) on delete restrict on update restrict;

create index ix_saldos_produto_id on saldos (produto_id);
alter table saldos add constraint fk_saldos_produto_id foreign key (produto_id) references produtos (id) on delete restrict on update restrict;

create index ix_transferencias_endereco_e_id on transferencias (endereco_e_id);
alter table transferencias add constraint fk_transferencias_endereco_e_id foreign key (endereco_e_id) references enderecos (id) on delete restrict on update restrict;

create index ix_transferencias_mov_produto_id on transferencias (mov_produto_id);
alter table transferencias add constraint fk_transferencias_mov_produto_id foreign key (mov_produto_id) references movprodutos (id) on delete restrict on update restrict;

create index ix_transferencias_endereco_s_id on transferencias (endereco_s_id);
alter table transferencias add constraint fk_transferencias_endereco_s_id foreign key (endereco_s_id) references enderecos (id) on delete restrict on update restrict;

create index ix_users_roles_users on users_roles (users_id);
alter table users_roles add constraint fk_users_roles_users foreign key (users_id) references users (id) on delete restrict on update restrict;

create index ix_users_roles_roles on users_roles (roles_id);
alter table users_roles add constraint fk_users_roles_roles foreign key (roles_id) references roles (id) on delete restrict on update restrict;

