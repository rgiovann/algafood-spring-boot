insert into cozinha(id,nome) values(1,'Tailandesa');
insert into cozinha(id,nome) values(2, 'Indiana');


insert into restaurante(nome, taxa_frete,cozinha_id) values('Restaurante Immer Offen',15,1);
insert into restaurante(nome, taxa_frete,cozinha_id) values('Restaurante Morro Azul',25,1);
insert into restaurante(nome, taxa_frete,cozinha_id) values('Restaurante Pomerânia',10,2);

insert into estado(id,nome ) values(1,'Santa Catarina' );
insert into estado(id,nome ) values(2,'Paraná' );
insert into estado(id,nome ) values(3,'Rio Grande do Sul' );

insert into cidade(nome,estado_id ) values( 'Blumenau',1 );
insert into cidade(nome,estado_id ) values( 'Florianópolis',1 );
insert into cidade(nome,estado_id ) values( 'Porto Alegre',2 );
insert into cidade(nome,estado_id ) values( 'Curitiba',3 );

insert into forma_pagamento(id,descricao ) values(1, 'Cartão de crédito VISA' );
insert into forma_pagamento(id,descricao ) values(2, 'Cartão de crédito MASTERCARD' );
insert into forma_pagamento(id,descricao ) values(3, 'À vista em dinheiro' );

insert into permissao(id,nome,descricao ) values(1, 'ED/IN COZINHAS','Escrita/Leitura entidade cozinha' );
insert into permissao(id,nome,descricao ) values(2, 'RO COZINHAS','Somente leitura entidade cozinha' );