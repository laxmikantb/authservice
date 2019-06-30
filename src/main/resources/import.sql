insert into Authority(name) values ('ROLE_ADMIN');
insert into Authority(name) values ('ROLE_DATASCIENTIST');
insert into Authority(name) values ('ROLE_PRODUCTOWNER');

insert into USER_DETAIL(user_id, password, first_name, last_name, email, phone) values ('admin', '$2a$10$QRtRWTRo8YK3ZA1rMyPI2e6RCc22uwQde1Z8abPfn8.L2zUQXmb4K', 'John', 'Doe', 'jdoe@mailinator.com', '1111111111');
insert into USER_DETAIL(user_id, password, first_name, last_name, email, phone) values ('superman', '$2a$10$QRtRWTRo8YK3ZA1rMyPI2e6RCc22uwQde1Z8abPfn8.L2zUQXmb4K', 'Superman', 'Doe', 'sdoe@mailinator.com', '2222222222');
insert into USER_DETAIL(user_id, password, first_name, last_name, email, phone) values ('carlos', '$2a$10$QRtRWTRo8YK3ZA1rMyPI2e6RCc22uwQde1Z8abPfn8.L2zUQXmb4K', 'Carlos', 'Doe', 'cdoe@mailinator.com', '3333333333');
insert into USER_DETAIL(user_id, password, first_name, last_name, email, phone) values ('veronica', '$2a$10$QRtRWTRo8YK3ZA1rMyPI2e6RCc22uwQde1Z8abPfn8.L2zUQXmb4K', 'Veronica', 'Doe', 'vdoe@mailinator.com', '4444444444');

insert into user_authority(user_id, authority_name) values('admin','ROLE_ADMIN');
insert into user_authority(user_id, authority_name) values('superman','ROLE_DATASCIENTIST');
insert into user_authority(user_id, authority_name) values('superman','ROLE_PRODUCTOWNER');
insert into user_authority(user_id, authority_name) values('carlos','ROLE_DATASCIENTIST');
insert into user_authority(user_id, authority_name) values('veronica','ROLE_PRODUCTOWNER');
