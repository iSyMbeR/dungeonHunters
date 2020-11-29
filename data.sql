--AREA
insert into AREA (ID, NAME)
values (1, 'Bagno ukraińców');
insert into AREA (ID, NAME)
values (2, 'Szkolna 17');
insert into AREA (ID, NAME)
values (3, 'Podlasie');
insert into AREA (ID, NAME)
values (4, 'Biały dom');
insert into AREA (ID, NAME)
values (5, 'Hogwart');


--BONUS
insert into BONUS (ID, DESCRIPTION, NAME, TYPE, VALUE)
values (1, 'Chcesz uderzyć mocniej? Nie ma problemu', 'bonus1', 'atak', 5);
insert into BONUS (ID, DESCRIPTION, NAME, TYPE, VALUE)
values (2, 'Chcesz się lepiej bronić? Nie ma problemu', 'bonus2', 'obrona', 5);
insert into BONUS (ID, DESCRIPTION, NAME, TYPE, VALUE)
values (3, 'Jak komus z tego uderzysz to go wlasny kot nie pozna', 'bonus3', 'atak', 10);
insert into BONUS (ID, DESCRIPTION, NAME, TYPE, VALUE)
values (4, 'Bron się krzesłem', 'bonus4', 'obrona', 10);


--ENEMY
insert into ENEMY (ID, NAME, BASE_LIFE, DMG, MIN_LEVEL)
values (1, 'Malfoj', 50, 2, 1);
insert into ENEMY (ID, NAME, BASE_LIFE, DMG, MIN_LEVEL)
values (2, 'Ron', 55, 3, 2);
insert into ENEMY (ID, NAME, BASE_LIFE, DMG, MIN_LEVEL)
values (3, 'Zgredek', 60, 4, 3);
insert into ENEMY (ID, NAME, BASE_LIFE, DMG, MIN_LEVEL)
values (4, 'Hagrid', 65, 5, 4);
insert into ENEMY (ID, NAME, BASE_LIFE, DMG, MIN_LEVEL)
values (5, 'Gryf', 70, 6, 5);

-- ITEM_BASE

insert into ITEM_BASE (ID, DMG, RARITY) values (1, 10, 50);
insert into ITEM_BASE (ID, DMG, RARITY) values (2, 12, 45);
insert into ITEM_BASE (ID, DMG, RARITY) values (3, 14, 40);
insert into ITEM_BASE (ID, DMG, RARITY) values (4, 16, 35);
insert into ITEM_BASE (ID, DMG, RARITY) values (5, 18, 30);
insert into ITEM_BASE (ID, DMG, RARITY) values (6, 10, 50);
insert into ITEM_BASE (ID, DMG, RARITY) values (7, 12, 45);
insert into ITEM_BASE (ID, DMG, RARITY) values (8, 14, 40);
insert into ITEM_BASE (ID, DMG, RARITY) values (9, 16, 35);
insert into ITEM_BASE (ID, DMG, RARITY) values (10, 18, 30);
insert into ITEM_BASE (ID, DMG, RARITY) values (11, 20, 25);
insert into ITEM_BASE (ID, DMG, RARITY) values (12, 22, 20);
insert into ITEM_BASE (ID, DMG, RARITY) values (13, 24, 15);
insert into ITEM_BASE (ID, DMG, RARITY) values (14, 26, 10);
insert into ITEM_BASE (ID, DMG, RARITY) values (15, 28, 8);
insert into ITEM_BASE (ID, DMG, RARITY) values (16, 30, 7);
insert into ITEM_BASE (ID, DMG, RARITY) values (17, 32, 6);
insert into ITEM_BASE (ID, DMG, RARITY) values (18, 34, 5);
insert into ITEM_BASE (ID, DMG, RARITY) values (19, 36, 4);
insert into ITEM_BASE (ID, DMG, RARITY) values (20, 38, 3);
-- CARDS{
insert into CARD (ID,name, type, cost, dmg, defense)
values (1,'Bash', 'Attack', 2, 8, 0);
insert into CARD (ID,name, type, cost, dmg, defense)
values (2,'Defend', 'Defense', 1, 0, 5);
insert into CARD (ID,name, type, cost, dmg, defense)
values (3,'Strike', 'Attack', 1, 6, 0);
insert into CARD (ID,name, type, cost, dmg, defense)
values (4,'Anger', 'Attack', 0, 6, 0);
insert into CARD (ID,name, type, cost, dmg, defense)
values (5,'Armaments', 'Defense', 1, 0, 5);
insert into CARD (ID,name, type, cost, dmg, defense)
values (6,'Body Slam', 'Attack', 1, 5, 0);
insert into CARD (ID,name, type, cost, dmg, defense)
values (7,'Cleave', 'Attack', 2, 8, 0);
insert into CARD (ID, name,type, cost, dmg, defense)
values (8,'Clothesline', 'Attack', 3, 12, 0);
insert into CARD (ID,name, type, cost, dmg, defense)
values (9,'Flex', 'Defense', 0, 0, 2);
insert into CARD (ID,name, type, cost, dmg, defense)
values (10,'Havoc', 'Defense', 2, 0, 8);
insert into CARD (ID,name, type, cost, dmg, defense)
values (11,'Headbutt', 'Attack', 1, 9, 0);
insert into CARD (ID,name, type, cost, dmg, defense)
values (12,'Heavy Blade', 'Attack', 3, 14, 0);
insert into CARD (ID, name,type, cost, dmg, defense)
values (13,'Iron Wave', 'Attack', 1, 5, 7);
insert into CARD (ID,name, type, cost, dmg, defense)
values (14,'Perfected Strike', 'Attack', 2, 6, 2);
insert into CARD (ID,name, type, cost, dmg, defense)
values (15,'Pommel Strike', 'Attack', 1, 9, 0);
insert into CARD (ID,name, type, cost, dmg, defense)
values (16,'Shrug it Off', 'Defense', 2, 0, 9);
insert into CARD (ID, name,type, cost, dmg, defense)
values (17,'Sword Boomerang', 'Attack', 1, 3, 0);
insert into CARD (ID, name,type, cost, dmg, defense)
values (18,'Thunderclap', 'Attack', 1, 6, 0);
insert into CARD (ID,name, type, cost, dmg, defense)
values (19,'True Grit', 'Defense', 4, 0, 20);
insert into CARD (ID,name, type, cost, dmg, defense)
values (20,'Twin Strike', 'Attack', 1, 10, 0);
insert into CARD (ID,name, type, cost, dmg, defense)
values (21,'Wild Strike', 'Attack', 4, 17, 0);
insert into CARD (ID,name, type, cost, dmg, defense)
values (22,'Blood for Blood', 'Attack', 6, 20, 0);

insert into ITEM (ID, NAME,ITEM_BASE_ID)
values (1, 'Stiletto',1);
insert into ITEM (ID, NAME,ITEM_BASE_ID)
values (2, 'Axe',2);
insert into ITEM (ID, NAME,ITEM_BASE_ID)
values (3, 'Wooden helmet',3);
insert into ITEM (ID, NAME,ITEM_BASE_ID)
values (4, 'Boots',4);
insert into ITEM (ID, NAME,ITEM_BASE_ID)
values (5, 'Amulet charm',5);
insert into ITEM (ID, NAME,ITEM_BASE_ID)
values (6, 'Lightning charm',6);
insert into ITEM (ID, NAME,ITEM_BASE_ID)
values (7, 'None charm',7);
insert into ITEM (ID, NAME,ITEM_BASE_ID)
values (8, 'Wooden sword',8);
insert into ITEM (ID, NAME,ITEM_BASE_ID)
values (9, 'Steel sword',9);
insert into ITEM (ID, NAME,ITEM_BASE_ID)
values (10, 'Silver sword',10);
insert into ITEM (ID, NAME,ITEM_BASE_ID)
values (11, 'Steel helmet',11);
insert into ITEM (ID, NAME,ITEM_BASE_ID)
values (12, 'Silver helmet',12);
insert into ITEM (ID, NAME,ITEM_BASE_ID)
values (13, 'Dragon shield',13);
insert into ITEM (ID, NAME,ITEM_BASE_ID)
values (14, 'Wooden armor',14);
insert into ITEM (ID, NAME,ITEM_BASE_ID)
values (15, 'Steel armor',15);
insert into ITEM (ID, NAME,ITEM_BASE_ID)
values (16, 'Silver armor',16);
insert into ITEM (ID, NAME,ITEM_BASE_ID)
values (17, 'Wooden armor',17);
insert into ITEM (ID, NAME,ITEM_BASE_ID)
values (18, 'Wooden pants',18);
insert into ITEM (ID, NAME,ITEM_BASE_ID)
values (19, 'Steel pants',19);
insert into ITEM (ID, NAME,ITEM_BASE_ID)
values (20, 'Silver pants',20);
