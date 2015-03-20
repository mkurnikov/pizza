
CREATE TABLE pizza_user (
--   user_id SERIAL NOT NULL PRIMARY KEY,
  login VARCHAR NOT NULL PRIMARY KEY,
  name VARCHAR NOT NULL,
  password VARCHAR NOT NULL,
  role INTEGER NOT NULL
);


CREATE TABLE district (
--   district_id SERIAL NOT NULL PRIMARY KEY,
  name VARCHAR NOT NULL PRIMARY KEY
);


CREATE TABLE path (
  district_1 VARCHAR REFERENCES district(name),
  district_2 VARCHAR REFERENCES district(name),
  travelling_time REAL NOT NULL CHECK (travelling_time > 0),
  PRIMARY KEY(district_1, district_2)
);


CREATE TABLE pizza_order (
  order_id SERIAL NOT NULL PRIMARY KEY,
  client VARCHAR NOT NULL REFERENCES pizza_user(login),
  district VARCHAR NOT NULL REFERENCES district(name),
  completed BOOLEAN DEFAULT FALSE
);

