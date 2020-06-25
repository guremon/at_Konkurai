INSERT INTO user (username, password)
VALUES ('test', 999999),
       ('test2', 999999);

INSERT INTO item (id, itemname, category, total_amount, remaining_amount, unit, amount_to_use, amount_score, frequency_of_use, stock, registeredDateTime, lastModifiedDateTime)
VALUES (1, 'testitem1', 1, 200, 100, 0, 50, 0, 1, 2, '2020-06-20 19:00:00', '2020-06-20 19:00:00'),
       (1, 'testitem2', 1, 400, 100, 0, 100, 0, 1, 1, '2020-06-21 19:00:00', '2020-06-21 19:00:00'),
       (1, 'testitem3', 1, 100, 50, 0, 10, 0, 1, 3, '2020-06-22 19:00:00', '2020-06-22 19:00:00');


INSERT INTO item (id, itemname, category, total_amount, remaining_amount, unit, amount_to_use, amount_score, frequency_of_use, stock, registeredDateTime, lastModifiedDateTime)
VALUES (2, 'testitem4', 2, 200, 32, 0, 10, 0, 2, 4, '2020-06-22 19:00:00', '2020-06-22 19:00:00');