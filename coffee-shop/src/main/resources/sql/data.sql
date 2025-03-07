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
SELECT 1,'Chicago','Acme,Inc.','IL','99 Market Street','95199'
    WHERE NOT EXISTS (SELECT FROM supplier WHERE sup_id = 1);

INSERT INTO supplier (sup_id,sup_city,sup_name,sup_state,sup_street,sup_zip)
SELECT 2,'Stuttgart','Superior Coffee','ST','1 Party Place','95460'
    WHERE NOT EXISTS (SELECT FROM supplier WHERE sup_id = 2);

INSERT INTO supplier (sup_id,sup_city,sup_name,sup_state,sup_street,sup_zip)
SELECT 3,'Geneva','The High Ground','GE','100 Coffee Lane','93966'
    WHERE NOT EXISTS (SELECT FROM supplier WHERE sup_id = 3);

-- Coffee Table Insert --------------------------------------------------------------------------

INSERT INTO coffee(cof_name, cof_price, cof_sold, cof_total_sold, cof_sup_id)
SELECT 'Colombian', 7.99, 50, 1150, 1
    WHERE NOT EXISTS(SELECT FROM coffee WHERE cof_name = 'Colombian');

INSERT INTO coffee(cof_name, cof_price, cof_sold, cof_total_sold, cof_sup_id)
SELECT 'Colombian_Decaf', 8.99, 60, 450, 1
    WHERE NOT EXISTS(SELECT FROM coffee WHERE cof_name = 'Colombian_Decaf');

INSERT INTO coffee(cof_name, cof_price, cof_sold, cof_total_sold, cof_sup_id)
SELECT 'French_Roast', 8.99, 35, 660, 2
    WHERE NOT EXISTS(SELECT FROM coffee WHERE cof_name = 'French_Roast');

INSERT INTO coffee(cof_name, cof_price, cof_sold, cof_total_sold, cof_sup_id)
SELECT  'French_Roast_Decaf', 9.99, 10, 200, 2
    WHERE NOT EXISTS(SELECT FROM coffee WHERE cof_name = 'French_Roast_Decaf');

INSERT INTO coffee(cof_name, cof_price, cof_sold, cof_total_sold, cof_sup_id)
SELECT  'Espresso', 9.99, 120, 2530, 3
    WHERE NOT EXISTS(SELECT FROM coffee WHERE cof_name = 'Espresso');

-- Coffee House Table Insert --------------------------------------------------------------------------

INSERT INTO coffee_house(ch_id, ch_city, ch_sold_coffee, ch_sold_merch, ch_total_sold)
SELECT 10001, 'Chicago', 1589, 1125, 2724
    WHERE NOT EXISTS(SELECT FROM coffee_house WHERE ch_id = 000011);

INSERT INTO coffee_house(ch_id, ch_city, ch_sold_coffee, ch_sold_merch, ch_total_sold)
SELECT 20001, 'Geneva', 2135, 525, 2660
    WHERE NOT EXISTS(SELECT FROM coffee_house WHERE ch_id = 000021);

INSERT INTO coffee_house(ch_id, ch_city, ch_sold_coffee, ch_sold_merch, ch_total_sold)
SELECT 30001, 'Stuttgart', 1266, 795, 2061
    WHERE NOT EXISTS(SELECT FROM coffee_house WHERE ch_id = 000031);

--  Merch Table Insert --------------------------------------------------------------------------

INSERT INTO merch_inventory(mer_item_id,mer_item_name,mer_quantity,mer_time,mer_sup_id)
SELECT 1, 'Cup_Large', 235, NOW(), 1
    WHERE NOT EXISTS(SELECT FROM merch_inventory WHERE mer_item_id = 1);

INSERT INTO merch_inventory(mer_item_id,mer_item_name,mer_quantity,mer_time,mer_sup_id)
SELECT 2, 'Cup_Small', 435, NOW(), 1
    WHERE NOT EXISTS(SELECT FROM merch_inventory WHERE mer_item_id = 2);

INSERT INTO merch_inventory(mer_item_id,mer_item_name,mer_quantity,mer_time,mer_sup_id)
SELECT 3, 'Towel', 125, NOW(), 2
    WHERE NOT EXISTS(SELECT FROM merch_inventory WHERE mer_item_id = 3);

INSERT INTO merch_inventory(mer_item_id,mer_item_name,mer_quantity,mer_time,mer_sup_id)
SELECT 4, 'Napkin', 765, NOW(), 3
    WHERE NOT EXISTS(SELECT FROM merch_inventory WHERE mer_item_id = 4);

-- Coffee Inventory Insert --------------------------------------------------------------------------

INSERT INTO cof_inventory (ci_id, ci_warehouse_id, ci_coffee_name,ci_sup_id, ci_quantity, ci_time)
SELECT 1,1,'Colombian',1,402,NOW()
    WHERE NOT EXISTS (SELECT FROM cof_inventory WHERE ci_id = 1);

INSERT INTO cof_inventory (ci_id, ci_warehouse_id, ci_coffee_name,ci_sup_id, ci_quantity, ci_time)
SELECT 2,1,'Colombian_Decaf',1,302,NOW()
    WHERE NOT EXISTS (SELECT FROM cof_inventory WHERE ci_id = 2);

INSERT INTO cof_inventory (ci_id, ci_warehouse_id, ci_coffee_name,ci_sup_id, ci_quantity, ci_time)
SELECT 3,2,'French_Roast',2,112,NOW()
    WHERE NOT EXISTS (SELECT FROM cof_inventory WHERE ci_id = 3);

INSERT INTO cof_inventory (ci_id, ci_warehouse_id, ci_coffee_name,ci_sup_id, ci_quantity, ci_time)
SELECT 4,2,'French_Roast_Decaf',2,56,NOW()
    WHERE NOT EXISTS (SELECT FROM cof_inventory WHERE ci_id = 4);

INSERT INTO cof_inventory (ci_id, ci_warehouse_id, ci_coffee_name,ci_sup_id, ci_quantity, ci_time)
SELECT 5,3,'Espresso',3,345,NOW()
    WHERE NOT EXISTS (SELECT FROM cof_inventory WHERE ci_id = 5);