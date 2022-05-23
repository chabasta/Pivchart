begin;

insert into users (id, created_at, email, sex, username) values
    (1, '2022-03-11 15:46:57.897480', 'tomaspalivec@gmail.com', 'MAN', 'Tomáš Palivec'),
    (2, '2022-03-11 15:46:57.897480', 'hugo.mrazek@gmail.com', 'MAN', 'Hugo Mrázek'),
    (3, '2022-03-11 15:46:57.897480', 'dimadima12345678943@gmail.com', 'MAN', 'Dmitriy Shevchenko'),
    (4, '2022-03-11 15:46:57.897480', 'stanchab49@gmail.com', 'MAN', 'Stanislav Chaban'),
    (5, '2022-03-11 15:46:57.897480', 'neohawk51@gmail.com', 'MAN', 'Nikita Iastrebov'),
    (6, '2022-03-11 15:46:57.897480', 'hodspodska@gmail.com', 'WOMAN', 'Veronika Freimanová');
SELECT setval('users_id_seq', 6);

insert into pub_owners (id, created_at, bio, user_id) values
    (1, '2022-03-11 15:46:57.897480', 'No idea bio', 6);
SELECT setval('pub_owners_id_seq', 1);

insert into pubs (id, created_at, latitude, longitude, name, city, country, postal_code, street, pub_owner_id) values
    (1, '2022-03-11 15:43:55.093239', 50.09902, 14.34523, 'Kaštan', 'Prague', 'Czechia', '16000', 'V Kruhu 220/2', 1),
    (2, '2022-03-11 15:43:55.093239', 50.09707, 14.34658, 'U Řezníka', 'Prague', 'Czechia', '16000', 'V Nových Vokovicích 254/4', 1),
    (3, '2022-03-11 15:46:57.881856', 50.07983, 14.41532, 'Propaganda', 'Prague', 'Czechia', '16000', 'Pštrossova 220/29', 1);
SELECT setval('pubs_id_seq', 3);

insert into drinks (id, alcohol_volume, created_at, name) values
    (1, 70, '2022-03-11 15:43:55.020297','Teleport');
SELECT setval('drinks_id_seq', 1);

insert into pub_drinks (id, created_at, price, volume, pub_id, drink_id) values
    (1, '2022-03-11 15:43:55.114535', 75, 40, 1, 1),
    (2, '2022-03-11 15:43:55.114535', 75, 40, 2, 1),
    (3, '2022-03-11 15:43:55.114535', 75, 40, 3, 1);
SELECT setval('pub_drinks_id_seq', 3);

commit;