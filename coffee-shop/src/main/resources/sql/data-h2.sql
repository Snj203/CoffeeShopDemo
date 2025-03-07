-- Warehouse Insert --------------------------------------------------------------------------

INSERT INTO warehouse (id, name, quantity, time)
SELECT 1,'Alpha',40,CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT FROM warehouse WHERE id = 1);

INSERT INTO warehouse (id, name, quantity, time)
SELECT 2,'Beta',55,CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT FROM warehouse WHERE id = 2);

INSERT INTO warehouse (id, name, quantity, time)
SELECT 3,'Gamma',32,CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT FROM warehouse WHERE id = 3);

-- Supplier Insert --------------------------------------------------------------------------

INSERT INTO supplier (id,city,name,state,street,zip)
SELECT 1,'Chicago','Acme,Inc.','IL','99 Market Street','95199'
    WHERE NOT EXISTS (SELECT FROM supplier WHERE id = 1);

INSERT INTO supplier (id,city,name,state,street,zip)
SELECT 2,'Stuttgart','Superior Coffee','ST','1 Party Place','95460'
    WHERE NOT EXISTS (SELECT FROM supplier WHERE id = 2);

INSERT INTO supplier (id,city,name,state,street,zip)
SELECT 3,'Geneva','The High Ground','GE','100 Coffee Lane','93966'
    WHERE NOT EXISTS (SELECT FROM supplier WHERE id = 3);

-- Connection Supplier-Warehouse Table Insert --------------------------------------------------------------------------

INSERT INTO supplier_warehouse (supplier_id, warehouse_id)
SELECT 1, 1
    WHERE NOT EXISTS(SELECT FROM supplier_warehouse WHERE supplier_id = 1 AND warehouse_id = 1);

INSERT INTO supplier_warehouse (supplier_id, warehouse_id)
SELECT 2, 2
    WHERE NOT EXISTS(SELECT FROM supplier_warehouse WHERE supplier_id = 2 AND warehouse_id = 2);

INSERT INTO supplier_warehouse (supplier_id, warehouse_id)
SELECT 3, 3
    WHERE NOT EXISTS(SELECT FROM supplier_warehouse WHERE supplier_id = 3 AND warehouse_id = 3);

INSERT INTO supplier_warehouse (supplier_id, warehouse_id)
SELECT 1, 3
    WHERE NOT EXISTS(SELECT FROM supplier_warehouse WHERE supplier_id = 1 AND warehouse_id = 3);

-- Coffee Table Insert --------------------------------------------------------------------------

INSERT INTO coffee(id, cof_name, price, sold, total_sold, sup_id, warehouse_id)
SELECT 1, 'Colombian', 7.99, 50, 1150, 1, 1
    WHERE NOT EXISTS(SELECT FROM coffee WHERE id = 1);

INSERT INTO coffee(id, cof_name, price, sold, total_sold, sup_id, warehouse_id)
SELECT 2, 'Colombian_Decaf', 8.99, 60, 450, 1, 3
    WHERE NOT EXISTS(SELECT FROM coffee WHERE id = 2);

INSERT INTO coffee(id, cof_name, price, sold, total_sold, sup_id, warehouse_id)
SELECT 3, 'French_Roast', 8.99, 35, 660, 2, 2
    WHERE NOT EXISTS(SELECT FROM coffee WHERE id = 3);

INSERT INTO coffee(id, cof_name, price, sold, total_sold, sup_id, warehouse_id)
SELECT 4, 'French_Roast_Decaf', 9.99, 10, 200, 2, 2
    WHERE NOT EXISTS(SELECT FROM coffee WHERE id = 4);

INSERT INTO coffee(id, cof_name, price, sold, total_sold, sup_id, warehouse_id)
SELECT 5, 'Espresso', 9.99, 120, 2530, 3, 3
    WHERE NOT EXISTS(SELECT FROM coffee WHERE id = 5);

-- Coffee House Table Insert --------------------------------------------------------------------------

INSERT INTO coffee_house(id, city, sold_coffee, sold_merch, total_sold)
SELECT 000011, 'Chicago', 1589, 1125, 2724
    WHERE NOT EXISTS(SELECT FROM coffee_house WHERE id = 000011);

INSERT INTO coffee_house(id, city, sold_coffee, sold_merch, total_sold)
SELECT 000021, 'Geneva', 2135, 525, 2660
    WHERE NOT EXISTS(SELECT FROM coffee_house WHERE id = 000021);

INSERT INTO coffee_house(id, city, sold_coffee, sold_merch, total_sold)
SELECT 000031, 'Stuttgart', 1266, 795, 2061
    WHERE NOT EXISTS(SELECT FROM coffee_house WHERE id = 000031);