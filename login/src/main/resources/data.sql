

INSERT INTO user(name, email, password, creation_date, modified) VALUES ('user01', 'user01@example.com', '$2a$10$e7xWy7UEHNguKI6AMuk2Tu0hNjOhoRmYyeQu96J7u/LdWauCtillW', '2019-09-20 16:02:00', '2019-09-20 16:02:00');
INSERT INTO user(name, email, password, creation_date, modified) VALUES ('user02', 'user02@example.com', '$2a$10$HQZN.QHy7dbNSxvXfc6WBe1o/uCqRzDHa4lpCzJoi1V0hRZnTQ2L6', '2019-09-20 16:02:00', '2019-09-20 16:02:00');

INSERT INTO login_Session(last_login, token, user_id) VALUES ( '2019-09-20 16:02:00', 'abcdefg', 1);
INSERT INTO login_Session(last_login, token, user_id) VALUES ( '2019-09-20 16:02:00', 'qwertyuiop', 2);

--UPDATE user SET LOGIN_SESSION_ID = (SELECT user_id from login_Session where token = 'abcdefg');
--UPDATE user SET LOGIN_SESSION_ID = (SELECT user_id from login_Session where token = 'qwertyuiop');

INSERT INTO PHONE (DDD, NUMBER, USER_ID) VALUES (81, '32312121', 1);
INSERT INTO PHONE (DDD, NUMBER, USER_ID) VALUES (81, '32414141', 2);
