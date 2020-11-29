--AREA
insert into AREA (ID, NAME)
values (1, 'Plebania');
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
values (1, 'Chcesz uderzyć mocniej? Nie ma problemu', 'Rzuć żelazkiem', 'Atak', 5);
insert into BONUS (ID, DESCRIPTION, NAME, TYPE, VALUE)
values (2, 'Chcesz się lepiej bronić? Nie ma problemu', 'Broń się krzesłem', 'Obrona', 5);
insert into BONUS (ID, DESCRIPTION, NAME, TYPE, VALUE)
values (3, 'Jak komus z tego uderzysz to go wlasny kot nie pozna', 'Rzut teściową', 'Atak', 10);
insert into BONUS (ID, DESCRIPTION, NAME, TYPE, VALUE)
values (4, 'Klep jak najman', 'Mistrz europy', 'Atak', 15);



--ENEMY
insert into ENEMY (ID, NAME,HP, DMG, EXPERIENCE_DROP, GOLD_DROP, STAGE, DEFENSE)
values (1, 'Malfoj', 20, 2, 100, 5, 1, 1);
insert into ENEMY (ID, NAME,HP, DMG, EXPERIENCE_DROP, GOLD_DROP, STAGE, DEFENSE)
values (2, 'Ron', 30, 3, 150, 6, 2,2);
insert into ENEMY (ID, NAME,HP, DMG, EXPERIENCE_DROP, GOLD_DROP, STAGE, DEFENSE)
values (3, 'Zgredek', 40, 4, 200, 7, 3,3);
insert into ENEMY (ID, NAME,HP, DMG, EXPERIENCE_DROP, GOLD_DROP, STAGE, DEFENSE)
values (4, 'Hagrid', 50, 5, 250, 8, 4,4);
insert into ENEMY (ID, NAME,HP, DMG, EXPERIENCE_DROP, GOLD_DROP, STAGE, DEFENSE)
values (5, 'Gryf', 60, 6, 300, 9, 5,5);

insert into ENEMY (ID, NAME,HP, DMG, EXPERIENCE_DROP, GOLD_DROP, STAGE, DEFENSE)
values (6, 'Passat 1.8 tdi', 70, 7, 350, 10, 6,6);
insert into ENEMY (ID, NAME,HP, DMG, EXPERIENCE_DROP, GOLD_DROP, STAGE, DEFENSE)
values (7, 'Golf III gaz', 80, 8, 400, 11, 7,7);
insert into ENEMY (ID, NAME,HP, DMG, EXPERIENCE_DROP, GOLD_DROP, STAGE, DEFENSE)
values (8, 'Stonoga', 90, 9, 450, 12, 8,8);
insert into ENEMY (ID, NAME,HP, DMG, EXPERIENCE_DROP, GOLD_DROP, STAGE, DEFENSE)
values (9, 'Najman', 100, 10, 500, 13, 9,9);
insert into ENEMY (ID, NAME,HP, DMG, EXPERIENCE_DROP, GOLD_DROP, STAGE, DEFENSE)
values (10, 'Korwin Klejn', 110, 11, 550, 14, 10,10);

insert into ENEMY (ID, NAME,HP, DMG, EXPERIENCE_DROP, GOLD_DROP, STAGE, DEFENSE)
values (11, 'Demon', 120, 12, 600, 15, 11, 11);
insert into ENEMY (ID, NAME,HP, DMG, EXPERIENCE_DROP, GOLD_DROP, STAGE, DEFENSE)
values (12, 'Orshabaal', 130, 13, 650, 16, 12, 12);
insert into ENEMY (ID, NAME,HP, DMG, EXPERIENCE_DROP, GOLD_DROP, STAGE, DEFENSE)
values (13, 'Tesciowa', 140, 14, 700, 17, 13, 13);
insert into ENEMY (ID, NAME,HP, DMG, EXPERIENCE_DROP, GOLD_DROP, STAGE, DEFENSE)
values (14, 'Była dziewczyna', 150, 15, 750, 18, 14, 14);
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
values (1,'Dotyk Proboszcza', 'Attack', 2, 8, 0);
insert into CARD (ID,name, type, cost, dmg, defense)
values (2,'Kołdra', 'Defense', 1, 0, 5);
insert into CARD (ID,name, type, cost, dmg, defense)
values (3,'Mela na twarz', 'Attack', 1, 6, 0);
insert into CARD (ID,name, type, cost, dmg, defense)
values (4,'Złoty usmiech', 'Attack', 0, 6, 0);
insert into CARD (ID,name, type, cost, dmg, defense)
values (5,'Postaw taczkę', 'Defense', 1, 0, 5);
insert into CARD (ID,name, type, cost, dmg, defense)
values (6,'Rozsyp gwoździe', 'Attack', 1, 5, 0);
insert into CARD (ID,name, type, cost, dmg, defense)
values (7,'Rzut dziewczyną', 'Attack', 2, 8, 0);
insert into CARD (ID, name,type, cost, dmg, defense)
values (8,'4 dniowe slipy', 'Attack', 3, 12, 0);
insert into CARD (ID,name, type, cost, dmg, defense)
values (9,'Ała nie bij', 'Defense', 0, 0, 2);
insert into CARD (ID,name, type, cost, dmg, defense)
values (10,'Formacja zółw', 'Defense', 2, 0, 8);
insert into CARD (ID,name, type, cost, dmg, defense)
values (11,'Beknięcie', 'Attack', 1, 9, 0);
insert into CARD (ID,name, type, cost, dmg, defense)
values (12,'Pierdnięcie', 'Attack', 3, 14, 0);
insert into CARD (ID, name,type, cost, dmg, defense)
values (13,'Grzmot', 'Attack', 1, 5, 7);
insert into CARD (ID,name, type, cost, dmg, defense)
values (14,'Perfected Strike', 'Attack', 2, 6, 2);
insert into CARD (ID,name, type, cost, dmg, defense)
values (15,'Lwię ', 'Attack', 1, 9, 0);
insert into CARD (ID,name, type, cost, dmg, defense)
values (16,'Krowie muu', 'Defense', 2, 0, 9);
insert into CARD (ID, name,type, cost, dmg, defense)
values (17,'Boomerang', 'Attack', 1, 3, 0);
insert into CARD (ID, name,type, cost, dmg, defense)
values (18,'Pchniecie kulą', 'Attack', 1, 6, 0);
insert into CARD (ID,name, type, cost, dmg, defense)
values (19,'Odklepanie najmana', 'Defense', 4, 0, 20);
insert into CARD (ID,name, type, cost, dmg, defense)
values (20,'Rzut kozą', 'Attack', 1, 10, 0);
insert into CARD (ID,name, type, cost, dmg, defense)
values (21,'Kobiecy liść', 'Attack', 4, 17, 0);
insert into CARD (ID,name, type, cost, dmg, defense)
values (22,'Śpiew Stonogi', 'Attack', 6, 20, 0);




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
