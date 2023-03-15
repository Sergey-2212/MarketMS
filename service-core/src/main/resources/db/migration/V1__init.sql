CREATE TABLE IF NOT EXISTS products
(id BIGSERIAL, title VARCHAR(255), price numeric(8, 2), date_of_adding TIMESTAMP NOT NULL DEFAULT (CURRENT_TIMESTAMP), PRIMARY KEY (id));
INSERT INTO products (title, price) VALUES
('1A', 22.15), ('2A', 56.50), ('3A', 89.99), ('4A', 555.55), ('5A', 12.12),
('6A', 51.55), ('7A', 8.99), ('8A', 33.33), ('9A', 56.56), ('10A', 566.55),
('11A', 22.00), ('12A', 56.45), ('13A', 89.78), ('14A', 987.89), ('15A', 129.56),
('16A', 255.00), ('17A', 89.00), ('18A', 3325.00), ('19A', 59.00), ('20A', 5669.99);

-- CREATE TABLE IF NOT EXISTS users (
--     id BIGSERIAL,
--     username VARCHAR(255) NOT NULL UNIQUE,
--     password VARCHAR(255) NOT NULL,
--     created_at timestamp default current_timestamp,
--     updated_at timestamp default current_timestamp,
--     PRIMARY KEY (id));
--
-- CREATE TABLE IF NOT EXISTS roles (
--     id SERIAL,
--     name VARCHAR(255) NOT NULL,
--     created_at timestamp default current_timestamp,
--     updated_at timestamp default current_timestamp,
--     PRIMARY KEY (id));
--
--
-- CREATE TABLE IF NOT EXISTS users_roles
-- (user_id BIGINT NOT NULL,
--  role_id INT NOT NULL,
--  PRIMARY KEY (user_id, role_id),
--  FOREIGN KEY (user_id) REFERENCES users (id),
--  FOREIGN KEY (role_id) REFERENCES roles (id));
--
--
-- INSERT INTO users (username, password) VALUES
-- ('admin', '$2a$10$sMJY8vpsBVW/LyQwD5eak.cYrdHZUJw2JdEA0/fa2TOHOO7hMIXqq'),
-- ('user', '$2a$10$pNc3OQ2MYGxnExFx6MyCWuiiohnMfA4sIMYph.1qmxGEL98uSpc/S'),
-- ('superadmin', '$2a$10$KWHh0xwFxNtA0IYlSgDUN.ogyapW7br2/ORYr4.vynLG9tXvFXgJm'),
-- ('mamager', '$2a$10$ke9VxhYd7Gy1X7BpmVI1S.WjMNfiVdy3DhegVbsLb0KC2qlg4ZCQO');
--
-- INSERT INTO roles (name) VALUES ('ROLE_ADMIN'), ('ROLE_USER'), ('ROLE_SUPERADMIN'), ('ROLE_MANAGER');
--
-- INSERT INTO users_roles (user_id, role_id) VALUES
-- (1, 1), (2, 2), (3, 3), (4, 4);