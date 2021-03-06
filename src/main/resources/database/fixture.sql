-- users
INSERT INTO pizza_user (login, name, password, role)
VALUES ('admin', 'Админ', 'admin', 1);

INSERT INTO pizza_user (login, name, password, role)
VALUES ('client', 'Клиент', 'client', 0);

-- districts
INSERT INTO district (name) VALUES
  ('Красносельский'),
  ('Кронштадский'),
  ('Курортный'),
  ('Московский'),
  ('Невский'),
  ('Петроградский'),
  ('Петродворцовый'),
  ('Приморский'),
  ('Пушкинский'),
  ('Фрунзенский'),
  ('Центральный');

-- paths
INSERT INTO path (district_1, district_2, travelling_time) VALUES
  ('Красносельский', 'Кронштадский', 10),
  ('Кронштадский', 'Курортный', 10),
  ('Курортный', 'Московский', 13),
  ('Курортный', 'Невский', 15),
  ('Невский', 'Петроградский', 10),
  ('Невский', 'Петродворцовый', 23),
  ('Невский', 'Приморский', 18),
  ('Приморский', 'Петродворцовый', 15),
  ('Приморский', 'Центральный', 26),
  ('Курортный', 'Пушкинский', 15),
  ('Пушкинский', 'Фрунзенский', 18),
  ('Фрунзенский', 'Центральный', 28),

  ('Красносельский', 'Красносельский', 0.0),
  ('Кронштадский', 'Кронштадский', 0.0),
  ('Курортный', 'Курортный', 0.0),
  ('Московский', 'Московский', 0.0),
  ('Невский', 'Невский', 0.0),
  ('Петроградский', 'Петроградский', 0.0),
  ('Петродворцовый', 'Петродворцовый', 0.0),
  ('Пушкинский', 'Пушкинский', 0.0),
  ('Фрунзенский', 'Фрунзенский', 0.0),
  ('Приморский', 'Приморский', 0.0),
  ('Центральный', 'Центральный', 0.0);

-- orders
INSERT INTO pizza_order (client, district, pizza_title) VALUES
  ('client', 'Приморский', 'Гавайская'),
  ('client', 'Петродворцовый', 'С анчоусами'),
  ('client', 'Невский', 'С салями'),
  ('client', 'Фрунзенский', 'Гавайская');
--   ('client', 'Центральный', 'С анчоусами'),
--   ('client', 'Приморский', 'С салями'),
--   ('client', 'Пушкинский', 'Гавайская'),
--   ('client', 'Невский', 'С анчоусами'),
--   ('client', 'Московский', 'С салями')