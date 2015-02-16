create table users (
	id bigint identity not null,
	username varchar(100) not null, 
	password varchar(32) not null, 
	is_account_expired boolean default false, 
	is_account_locked boolean default false, 
	is_credentials_expired boolean default false, 
	is_enabled boolean default false
);

insert into users (id, username, password, is_account_expired, is_account_locked, is_credentials_expired, is_enabled) values
(1, 'zakyalvan', 'rahasia', false, false, false, true),
(2, 'admin.user', 'rahasia', false, false, false, true),
(3, 'usual.user', 'rahasia', false, false, false, true);;

create table user_roles (
	user_id bigint not null,
	role_name varchar(100) not null,
	role_order int,
	primary key(user_id, role_order),
	foreign key(user_id) references users(id)
);

insert into user_roles (user_id, role_name, role_order) values
(1, 'ROLE_ADMIN', 0),
(1, 'ROLE_USER', 1),
(2, 'ROLE_ADMIN', 0),
(3, 'ROLE_USER', 0)
