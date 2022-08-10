create table if not exists boxes (id int8 GENERATED by default as identity, close_time time, coef float8, open_time time, primary key (id));
create table if not exists options (id int8 GENERATED by default as identity, discount int4, name varchar(255), price int4, time int4, options int8, primary key (id));
create table if not exists reservations (id int8 GENERATED by default as identity, full_cost int4, status varchar(255), date date, time_end time, time_start time, user_id int8, box_id int8, in_Time boolean, primary key (id));
create table if not exists slots (id int8 GENERATED by default as identity, date date, time_end time, time_start time, box_id int8, reservation_id int8, primary key (id));
create table if not exists users (id int8 GENERATED by default as identity, email varchar(255), name varchar(255), password varchar(255), role varchar(255), primary key (id));
create table if not exists reservations_options (options_id int8 not null, reservation_id int8 not null);
create table if not exists operators (id int8 GENERATED by default as identity, disc_max int4, disc_min int4, box_id int8 not null, user_id int8 not null, primary key (id));

alter table users add constraint UK6dotkott2kjsp8vw4d0m25fb7 unique (email);
alter table operators add constraint FK32uaagbwaw165nhbq5l13y9u2 foreign key (box_id) references boxes;
alter table operators add constraint FKj7p7gt1xe75lomlqfi0xw3mbx foreign key (user_id) references users;
alter table reservations_options add constraint FKrciw39v3ajh7ut90eebjyimpv foreign key (options_id) references options;
alter table reservations_options add constraint FKyqv9p5s4onvs8en1j05j53u7 foreign key (reservation_id) references reservations;
alter table reservations add constraint FK87hpt1rs6k4ubfvnyeieiibd9 foreign key (box_id) references boxes;
alter table reservations add constraint FKb5g9io5h54iwl2inkno50ppln foreign key (user_id) references users;
alter table slots add constraint FK6k1rdh3d80u7y4sanl4x4sg3j foreign key (box_id) references boxes;
alter table slots add constraint FKr41os4ph4avk6iawq2ub825wh foreign key (reservation_id) references reservations;

INSERT INTO boxes (open_time, close_time, coef)
VALUES ('09:00:00','21:00:00', 1),
       ('11:00:00','18:00:00', 1.2),
       ('00:00:00','00:00:00', 0.9);

INSERT INTO slots (date, time_end, time_start, box_id, reservation_id)
VALUES ('2022-08-03', '12:30:00', '12:00:00', 1, null),
('2022-08-03', '13:30:00', '12:30:00', 1, null),
('2022-08-03', '14:00:00', '13:30:00', 1, null),
( '2022-08-04', '14:30:00', '14:00:00', 1, null),
( '2022-08-03', '15:00:00', '14:30:00', 1, null),
( '2022-08-03', '15:30:00', '15:00:00', 1, null),
('2022-08-03', '16:00:00', '15:30:00', 1, null),
('2022-08-03', '16:30:00', '16:00:00', 1, null),
('2022-08-03', '17:00:00', '16:30:00', 1, null),
('2022-08-03', '17:30:00', '17:00:00', 2, null),
('2022-08-03', '12:30:00', '12:00:00', 2, null),
('2022-08-03', '13:30:00', '12:30:00', 2, null),
('2022-08-03', '14:00:00', '13:30:00', 2, null),
('2022-08-04', '14:30:00', '14:00:00', 2, null),
('2022-08-03', '15:00:00', '14:30:00', 2, null),
('2022-08-03', '15:30:00', '15:00:00', 2, null),
('2022-08-03', '16:00:00', '15:30:00', 2, null),
('2022-08-03', '16:30:00', '16:00:00', 2, null),
( '2022-08-03', '17:00:00', '16:30:00', 2, null),
('2022-08-03', '17:30:00', '17:00:00', 2, null),
('2022-08-03', '12:30:00', '12:00:00', 3, null),
('2022-08-03', '13:30:00', '12:30:00', 3, null),
('2022-08-03', '14:00:00', '13:30:00', 3, null),
('2022-08-04', '14:30:00', '14:00:00', 3, null),
('2022-08-03', '15:00:00', '14:30:00', 3, null),
('2022-08-03', '15:30:00', '15:00:00', 3, null),
('2022-08-03', '16:00:00', '15:30:00', 3, null),
('2022-08-03', '16:30:00', '16:00:00', 3, null),
('2022-08-03', '17:00:00', '16:30:00', 3, null),
('2022-08-03', '17:30:00', '17:00:00', 3, null);

INSERT INTO options (id, discount, name, price, time)
VALUES (DEFAULT, 5, 'Помывка', 1000, 45),
(DEFAULT, 10, 'Уборка', 2000, 45),
(DEFAULT, 20, 'Повывка салона', 1500, 45),
(DEFAULT, 10, 'Помывка Люкс', 700, 45);

INSERT INTO users (email, name, password, role)
VALUES ('test.ru','user','$2a$10$WNNS.3If6EnbizUfxqVWoeZ0sYrspvhnmROyv9Dgw17mJTm5Yk2s6','ROLE_USER'),
       ('test.com','admin','$2a$10$WNNS.3If6EnbizUfxqVWoeZ0sYrspvhnmROyv9Dgw17mJTm5Yk2s6','ROLE_ADMIN'),
       ('oper.com','admin','$2a$10$WNNS.3If6EnbizUfxqVWoeZ0sYrspvhnmROyv9Dgw17mJTm5Yk2s6','ROLE_OPERATOR')