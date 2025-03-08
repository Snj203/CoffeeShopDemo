-- State Insert --------------------------------------------------------------------------
INSERT INTO state (st_name,st_prefix)
SELECT 'IL',1000
    WHERE NOT EXISTS (SELECT FROM state WHERE st_name = 'IL');

INSERT INTO state (st_name,st_prefix)
SELECT 'GE',2000
    WHERE NOT EXISTS (SELECT FROM state WHERE st_name = 'GE');

INSERT INTO state (st_name,st_prefix)
SELECT 'ST',3000
    WHERE NOT EXISTS (SELECT FROM state WHERE st_name = 'ST');

-- City Insert --------------------------------------------------------------------------
INSERT INTO city (city_name,city_state)
SELECT 'Chicago','IL'
    WHERE NOT EXISTS (SELECT FROM city WHERE city_name = 'Chicago');

INSERT INTO city (city_name,city_state)
SELECT 'Stuttgart','ST'
    WHERE NOT EXISTS (SELECT FROM city WHERE city_name = 'Stuttgart');

INSERT INTO city (city_name,city_state)
SELECT 'Geneva','GE'
    WHERE NOT EXISTS (SELECT FROM city WHERE city_name = 'Geneva');

-- Supplier Insert --------------------------------------------------------------------------

INSERT INTO supplier (sup_id,sup_city,sup_name,sup_state,sup_street,sup_zip)
SELECT 40000,'Chicago','Acme,Inc.','IL','99 Market Street','95199'
    WHERE NOT EXISTS (SELECT FROM supplier WHERE sup_id = 40000);

INSERT INTO supplier (sup_id,sup_city,sup_name,sup_state,sup_street,sup_zip)
SELECT 40002,'Stuttgart','Superior Coffee','ST','1 Party Place','95460'
    WHERE NOT EXISTS (SELECT FROM supplier WHERE sup_id = 40002);

INSERT INTO supplier (sup_id,sup_city,sup_name,sup_state,sup_street,sup_zip)
SELECT 40001,'Geneva','The High Ground','GE','100 Coffee Lane','93966'
    WHERE NOT EXISTS (SELECT FROM supplier WHERE sup_id = 40001);

-- Coffee Table Insert --------------------------------------------------------------------------

INSERT INTO coffee(cof_name, cof_price, cof_sold, cof_total_sold, cof_sup_id)
SELECT 'Colombian', 7.99, 50, 1150, 40000
    WHERE NOT EXISTS(SELECT FROM coffee WHERE cof_name = 'Colombian');

INSERT INTO coffee(cof_name, cof_price, cof_sold, cof_total_sold, cof_sup_id)
SELECT 'Colombian_Decaf', 8.99, 60, 450, 40000
    WHERE NOT EXISTS(SELECT FROM coffee WHERE cof_name = 'Colombian_Decaf');

INSERT INTO coffee(cof_name, cof_price, cof_sold, cof_total_sold, cof_sup_id)
SELECT 'French_Roast', 8.99, 35, 660, 40002
    WHERE NOT EXISTS(SELECT FROM coffee WHERE cof_name = 'French_Roast');

INSERT INTO coffee(cof_name, cof_price, cof_sold, cof_total_sold, cof_sup_id)
SELECT  'French_Roast_Decaf', 9.99, 10, 200, 40002
    WHERE NOT EXISTS(SELECT FROM coffee WHERE cof_name = 'French_Roast_Decaf');

INSERT INTO coffee(cof_name, cof_price, cof_sold, cof_total_sold, cof_sup_id)
SELECT  'Espresso', 9.99, 120, 2530, 40001
    WHERE NOT EXISTS(SELECT FROM coffee WHERE cof_name = 'Espresso');

-- Coffee House Table Insert --------------------------------------------------------------------------

INSERT INTO coffee_house(ch_id, ch_city, ch_sold_coffee, ch_sold_merch, ch_total_sold, ch_state_prefix)
SELECT 40003, 'Chicago', 1589, 1125, 2724, 500
WHERE NOT EXISTS(SELECT FROM coffee_house WHERE ch_id = 40003);

INSERT INTO coffee_house(ch_id, ch_city, ch_sold_coffee, ch_sold_merch, ch_total_sold, ch_state_prefix)
SELECT 40002, 'Geneva', 2135, 525, 2660, 400
    WHERE NOT EXISTS(SELECT FROM coffee_house WHERE ch_id = 40002);

INSERT INTO coffee_house(ch_id, ch_city, ch_sold_coffee, ch_sold_merch, ch_total_sold, ch_state_prefix)
SELECT 40001, 'Stuttgart', 1266, 795, 2061, 300
    WHERE NOT EXISTS(SELECT FROM coffee_house WHERE ch_id = 40001);

--  Merch Table Insert --------------------------------------------------------------------------

INSERT INTO merch_inventory(mer_item_id,mer_item_name,mer_quantity,mer_time,mer_sup_id)
SELECT 40000, 'Cup_Large', 235, CURRENT_TIMESTAMP, 40000
    WHERE NOT EXISTS(SELECT FROM merch_inventory WHERE mer_item_id = 40000);

INSERT INTO merch_inventory(mer_item_id,mer_item_name,mer_quantity,mer_time,mer_sup_id)
SELECT 40002, 'Cup_Small', 435, CURRENT_TIMESTAMP, 40000
    WHERE NOT EXISTS(SELECT FROM merch_inventory WHERE mer_item_id = 40002);

INSERT INTO merch_inventory(mer_item_id,mer_item_name,mer_quantity,mer_time,mer_sup_id)
SELECT 40001, 'Towel', 125, CURRENT_TIMESTAMP, 40002
    WHERE NOT EXISTS(SELECT FROM merch_inventory WHERE mer_item_id = 40001);

INSERT INTO merch_inventory(mer_item_id,mer_item_name,mer_quantity,mer_time,mer_sup_id)
SELECT 40000, 'Napkin', 765, CURRENT_TIMESTAMP, 40001
    WHERE NOT EXISTS(SELECT FROM merch_inventory WHERE mer_item_id = 40000);

-- Coffee Inventory Insert --------------------------------------------------------------------------

INSERT INTO cof_inventory (ci_id, ci_warehouse_id, ci_coffee_name,ci_sup_id, ci_quantity, ci_time)
SELECT 40000,40000,'Colombian',40000,402,CURRENT_TIMESTAMP
    WHERE NOT EXISTS (SELECT FROM cof_inventory WHERE ci_id = 40000);

INSERT INTO cof_inventory (ci_id, ci_warehouse_id, ci_coffee_name,ci_sup_id, ci_quantity, ci_time)
SELECT 40002,40000,'Colombian_Decaf',40000,302,CURRENT_TIMESTAMP
    WHERE NOT EXISTS (SELECT FROM cof_inventory WHERE ci_id = 40002);

INSERT INTO cof_inventory (ci_id, ci_warehouse_id, ci_coffee_name,ci_sup_id, ci_quantity, ci_time)
SELECT 40001,40002,'French_Roast',40002,112,CURRENT_TIMESTAMP
    WHERE NOT EXISTS (SELECT FROM cof_inventory WHERE ci_id = 40001);

INSERT INTO cof_inventory (ci_id, ci_warehouse_id, ci_coffee_name,ci_sup_id, ci_quantity, ci_time)
SELECT 40000,40002,'French_Roast_Decaf',40002,56,CURRENT_TIMESTAMP
    WHERE NOT EXISTS (SELECT FROM cof_inventory WHERE ci_id = 40000);

INSERT INTO cof_inventory (ci_id, ci_warehouse_id, ci_coffee_name,ci_sup_id, ci_quantity, ci_time)
SELECT 40005,40001,'Espresso',40001,345,CURRENT_TIMESTAMP
    WHERE NOT EXISTS (SELECT FROM cof_inventory WHERE ci_id = 40005);