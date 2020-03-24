-- Populate items for the varied tables.
INSERT INTO category VALUES(1, 'sporting goods', true),(2, 'computer parts', true), (3, 'clothing', false);
INSERT INTO seller VALUES(1, 'Azadi', 'Bogolubov', false), (2, 'Jake', 'Hall', true), (3, 'Joe', 'Johnson', false);
INSERT INTO item VALUES(1, 1, 249.99, 2, 'Bosu'), (2, 2, 59.95, 3, 'Crucial SSD 500GB');
INSERT INTO minimum_price VALUES(1, 1, 4.99), (2, 2, 99.99), (3, 3, 9.99);