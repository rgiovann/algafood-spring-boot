set foreign_key_checks = 0;

delete from cidade;
delete from cozinha;
delete from estado;
delete from forma_pagamento;
delete from grupo;
delete from grupo_permissao;
delete from permissao;
delete from produto;
delete from restaurante;
delete from restaurante_forma_pagamento;
delete from usuario;
delete from usuario_grupo;

set foreign_key_checks = 1;

alter table cidade auto_increment = 1;
alter table cozinha auto_increment = 1;
alter table estado auto_increment = 1;
alter table forma_pagamento auto_increment = 1;
alter table grupo auto_increment = 1;
alter table permissao auto_increment = 1;
alter table produto auto_increment = 1;
alter table restaurante auto_increment = 1;
alter table usuario auto_increment = 1;

insert into grupo (id, nome) values (1, 'Gerente'), (2, 'Vendedor'), (3, 'Secretária'), (4, 'Cadastrador');

insert into usuario (id, nome, email, senha, data_cadastro) values
(1, 'João da Silva', 'joao.ger@algafood.com', '123', utc_timestamp),
(2, 'Maria Joaquina', 'maria.vnd@algafood.com', '123', utc_timestamp),
(3, 'José Souza', 'jose.aux@algafood.com', '123', utc_timestamp),
(4, 'Sebastião Martins', 'sebastiao.cad@algafood.com', '123', utc_timestamp); 

insert into cozinha(id,nome) values(1,'Tailandesa');
insert into cozinha(id,nome) values(2, 'Indiana');
insert into cozinha (id, nome) values (3, 'Argentina');
insert into cozinha (id, nome) values (4, 'Brasileira');

insert into estado(id,nome ) values(1,'Santa Catarina' );
insert into estado(id,nome ) values(2,'Paraná' );
insert into estado(id,nome ) values(3,'Rio Grande do Sul' );

insert into cidade(nome,estado_id ) values( 'Blumenau',1 );
insert into cidade(nome,estado_id ) values( 'Florianópolis',1 );
insert into cidade(nome,estado_id ) values( 'Porto Alegre',3 );
insert into cidade(nome,estado_id ) values( 'Curitiba',2 );

insert into restaurante(nome, taxa_frete,cozinha_id,data_cadastro,data_atualizacao,endereco_logradouro,endereco_numero,endereco_bairro,endereco_cep,endereco_cidade_id,ativo,aberto) values('Restaurante Immer Offen',15,1,utc_timestamp,utc_timestamp,'Rua Republica Pomerana','470','Fortaleza','89030-100',1,true,true);
insert into restaurante(nome, taxa_frete,cozinha_id,data_cadastro,data_atualizacao,endereco_logradouro,endereco_numero,endereco_bairro,endereco_cep,endereco_cidade_id,ativo,aberto) values('Restaurante Morro Azul',25,1,utc_timestamp,utc_timestamp,'Rua Angelo Vermont','1470','Altex','78030-200',2,true,true);
insert into restaurante(nome, taxa_frete,cozinha_id,data_cadastro,data_atualizacao,endereco_logradouro,endereco_numero,endereco_bairro,endereco_cep,endereco_cidade_id,ativo,aberto) values('Restaurante Pomerânia',10,2,utc_timestamp,utc_timestamp,'Avenida Bento XVI','200a','Vorstadt','88030-910',3,true,true);
insert into restaurante(nome, taxa_frete,cozinha_id,data_cadastro,data_atualizacao,endereco_logradouro,endereco_numero,endereco_bairro,endereco_cep,endereco_cidade_id,ativo,aberto) values ( 'Java Steakhouse', 12, 3, utc_timestamp, utc_timestamp,'Praça Paulo VI','400','Gaspar Alto','45030-910',3,true,true);
insert into restaurante(nome, taxa_frete,cozinha_id,data_cadastro,data_atualizacao,endereco_logradouro,endereco_numero,endereco_bairro,endereco_cep,endereco_cidade_id,endereco_complemento,ativo,aberto) values ( 'Lanchonete do Tio Sam', 11, 4, utc_timestamp, utc_timestamp,'Travessa Martin Luther King','1200c','Garcia','88030-235',1,'perto super Alberto',true,true);
insert into restaurante(nome, taxa_frete,cozinha_id,data_cadastro,data_atualizacao,endereco_logradouro,endereco_numero,endereco_bairro,endereco_cep,endereco_cidade_id,endereco_complemento,ativo,aberto) values ( 'Bar da Maria', 6, 4, utc_timestamp, utc_timestamp,'Avenida Japão','345','Santa Luzia','82080-910',3,'perto terminal Azul',true,true);

insert into forma_pagamento(id,descricao ) values(1, 'Cartão de crédito VISA' );
insert into forma_pagamento(id,descricao ) values(2, 'Cartão de crédito MASTERCARD' );
insert into forma_pagamento(id,descricao ) values(3, 'À vista em dinheiro' );

insert into permissao (id, nome, descricao) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert into permissao (id, nome, descricao) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

insert into grupo_permissao (grupo_id, permissao_id) values (1, 1), (1, 2), (2, 1), (2, 2), (3, 1); 


insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3), (4, 1), (4, 2), (5, 1), (5, 2), (6, 3);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, 1, 1);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Camarão tailandês', '16 camarões grandes ao molho picante', 110, 1, 1);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Salada picante com carne grelhada', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20, 1, 2);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, 1, 3);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, 1, 3);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé', 79, 1, 4);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('T-Bone', 'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89, 1, 4);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, 1, 5);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, 1, 6);

insert into usuario_grupo (usuario_id, grupo_id) values (1, 1), (1, 2), (2, 2);
