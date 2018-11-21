DELETE FROM users;
DELETE FROM accounts;
DELETE FROM messages;
DELETE FROM account_histories;

ALTER SEQUENCE global_seq RESTART WITH 1;

INSERT INTO users (login, password, first_name, last_name, email, enabled, role) VALUES
  ('login', '$2a$10$6VQmTi9OFIlafm4ggjyxS.vdbPfxE9gD0K8LcS9f2Ckolt5hcgFZ2', 'Dmitriy', 'Bosenko', 'bosenkodmitriy@gmail.ru', true, 'ADMIN'),
  ('admin', '$2a$10$jmz0mFynjuMtSo1lZW.JRuf8OJeA1u..ceaIzyywUKNab4EDBorsC', 'Admin', 'Adminow', 'admi@mail.ru', true, 'ADMIN'),
  ('shapka', '$2a$10$sbrFSPcWL2cuqr4vLu95s.y5/PUJ0YVv.P..qF8vR2UclwmhGrBU2', 'Vitya', 'Yanukovich', 'mejigorje@mail.ru', true, 'CUSTOMER');

INSERT INTO accounts(name, account_number, currency, balance, user_id) VALUES
  ('USD_account', '20200000000000000011', 'USD', 0, 1),
  ('PLN_account', '40400000000000000012', 'PLN', 0, 1),
  ('UAH_account', '10100000000000000013', 'UAH', 0, 1),
  ('EUR_account', '30300000000000000014', 'EUR', 0, 1),
  ('RUB_account', '50500000000000000015', 'RUB', 0, 1),
  ('актуальный счет', '50500000000000000320', 'RUB', 0.00, 3),
  ('на каждый день', '30300000000000000316', 'EUR', 10000000.00, 3),
  ('на черный день', '20200000000000000314', 'USD', 20000000.00, 3),
  ('гривневый счет', '10100000000000000318', 'UAH', 15000000000.00, 3);
