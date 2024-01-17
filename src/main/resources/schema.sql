
-- Insert 5 products
INSERT INTO Product (product_id, name, brand, model, price, sku) VALUES (1001, 'Product 1', 'Brand 1', 'Model 1', 50, 'SKU1');
INSERT INTO Product (product_id, name, brand, model, price, sku) VALUES (1002, 'Product 2', 'Brand 2', 'Model 2', 75, 'SKU2');
INSERT INTO Product (product_id, name, brand, model, price, sku) VALUES (1003, 'Product 3', 'Brand 3', 'Model 3', 100, 'SKU3');
INSERT INTO Product (product_id, name, brand, model, price, sku) VALUES (1004, 'Product 4', 'Brand 4', 'Model 4', 120, 'SKU4');
INSERT INTO Product (product_id, name, brand, model, price, sku) VALUES (1005, 'Product 5', 'Brand 5', 'Model 5', 90, 'SKU5');

-- Insert 5 stores
INSERT INTO Store (store_id, name, address, phone, city, state, zip_code) VALUES (1001, 'Store 1', '123 Main St', '555-1234', 'City1', 'State1', '12345');
INSERT INTO Store (store_id, name, address, phone, city, state, zip_code) VALUES (1002, 'Store 2', '456 Oak St', '555-5678', 'City2', 'State2', '56789');
INSERT INTO Store (store_id, name, address, phone, city, state, zip_code) VALUES (1003, 'Store 3', '789 Pine St', '555-9876', 'City3', 'State3', '98765');
INSERT INTO Store (store_id, name, address, phone, city, state, zip_code) VALUES (1004, 'Store 4', '101 Elm St', '555-4321', 'City4', 'State4', '43210');
INSERT INTO Store (store_id, name, address, phone, city, state, zip_code) VALUES (1005, 'Store 5', '202 Maple St', '555-6789', 'City5', 'State5', '67890');

INSERT INTO Stock (stock_id, product_id, store_id, quantity) VALUES (1001, 1001, 1001, 10);
INSERT INTO Stock (stock_id, product_id, store_id, quantity) VALUES (1002, 1002, 1002, 20);
INSERT INTO Stock (stock_id, product_id, store_id, quantity) VALUES (1003, 1002, 1001, 30);
INSERT INTO Stock (stock_id, product_id, store_id, quantity) VALUES (1004, 1001, 1005, 40);
INSERT INTO Stock (stock_id, product_id, store_id, quantity) VALUES (1005, 1003, 1003, 50);