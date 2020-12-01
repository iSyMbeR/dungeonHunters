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

-- insert into ITEM_BASE (ID, DMG, RARITY) values (1, 10, 50);
-- insert into ITEM_BASE (ID, DMG, RARITY) values (2, 12, 45);
-- insert into ITEM_BASE (ID, DMG, RARITY) values (3, 14, 40);
-- insert into ITEM_BASE (ID, DMG, RARITY) values (4, 16, 35);
-- insert into ITEM_BASE (ID, DMG, RARITY) values (5, 18, 30);
-- insert into ITEM_BASE (ID, DMG, RARITY) values (6, 10, 50);
-- insert into ITEM_BASE (ID, DMG, RARITY) values (7, 12, 45);
-- insert into ITEM_BASE (ID, DMG, RARITY) values (8, 14, 40);
-- insert into ITEM_BASE (ID, DMG, RARITY) values (9, 16, 35);
-- insert into ITEM_BASE (ID, DMG, RARITY) values (10, 18, 30);
-- insert into ITEM_BASE (ID, DMG, RARITY) values (11, 20, 25);
-- insert into ITEM_BASE (ID, DMG, RARITY) values (12, 22, 20);
-- insert into ITEM_BASE (ID, DMG, RARITY) values (13, 24, 15);
-- insert into ITEM_BASE (ID, DMG, RARITY) values (14, 26, 10);
-- insert into ITEM_BASE (ID, DMG, RARITY) values (15, 28, 8);
-- insert into ITEM_BASE (ID, DMG, RARITY) values (16, 30, 7);
-- insert into ITEM_BASE (ID, DMG, RARITY) values (17, 32, 6);
-- insert into ITEM_BASE (ID, DMG, RARITY) values (18, 34, 5);
-- insert into ITEM_BASE (ID, DMG, RARITY) values (19, 36, 4);
-- insert into ITEM_BASE (ID, DMG, RARITY) values (20, 38, 3);



-- CARDS{
insert into CARD (ID,name,description, type, cost, value)
values (1,'Dotyk Proboszcza','Lepiej tego unikac', 'Attack', 1, 29);
insert into CARD (ID,name,description, type, cost, value)
values (2,'Kołdra','Chyba nie widac mnie', 'Block', 0, 1);
insert into CARD (ID,name,description, type, cost, value)
values (3,'Mela na twarz','I cyk covid-19', 'Attack', 1, 25);
insert into CARD (ID,name,description, type, cost, value)
values (4,'Złoty usmiech','Po co myc zęby', 'ReducedDmg', 1, 2);
insert into CARD (ID,name,description, type, cost, value)
values (5,'Taczka','I wruuum', 'Block', 1, 1);
insert into CARD (ID,name,description, type, cost, value)
values (6,'Gwoździe','Przebija stopy', 'Miss', 1, 2);
insert into CARD (ID,name,description, type, cost, value)
values (7,'Rzut dziewczyną','Najblizej pod reka', 'Attack', 2, 25);
insert into CARD (ID, name,description,type, cost, value)
values (8,'4 dniowe slipy','I cyk na druga strone', 'Sleep', 1, 2);
insert into CARD (ID,name,description, type, cost, value)
values (9,'Ała nie bij','Nie w szczepionke', 'Miss', 1, 1);
insert into CARD (ID,name,description, type, cost, value)
values (10,'Formacja zółw','No przebij sie', 'Block', 1, 1);
insert into CARD (ID,name,description, type, cost, value)
values (11,'Beknięcie', 'Ale dobry obiad','Sleep', 1, 2);
insert into CARD (ID,name,description, type, cost, value)
values (12,'Pierdnięcie','Kapusta kiszona samo zdrowie', 'Sleep', 2, 4);
insert into CARD (ID, name,description,type, cost, value)
values (13,'Grzmot','Stan pod drzewem', 'Attack', 1, 25);
insert into CARD (ID,name,description, type, cost, value)
values (14,'Strajk', 'Jaki by tu marsz zrobic','ReducedDmg', 1, 2);
insert into CARD (ID,name,description, type, cost, value)
values (15,'Lwię','Krol lew to nie tylko bajka', 'Attack', 2, 35);
insert into CARD (ID,name,description, type, cost, value)
values (16,'Krowie muu','Muuuu', 'ReducedDmg', 2, 4);
insert into CARD (ID, name,description,type, cost, value)
values (17,'Usmiech bombelka','NALEZY MI SIE', 'ReducedDmg', 2, 3);
insert into CARD (ID, name,description,type, cost, value)
values (18,'Pchniecie kulą','Troche wazy', 'Attack', 2, 35);
insert into CARD (ID,name,description, type, cost, value)
values (19,'Odklepanie najmana','Nic dodac nic ujac', 'Sleep', 2, 3);
insert into CARD (ID,name,description, type, cost, value)
values (20,'Rzut kozą','Suchosc nosa','Attack', 2, 45);
insert into CARD (ID,name,description, type, cost, value)
values (21,'Kobiecy liść','Gdzie te rece wkladadasz', 'Sleep', 2, 4);
insert into CARD (ID,name,description, type, cost, value)
values (22,'Śpiew Stonogi','ZIOBRO TY .....', 'Attack', 2, 50);



insert into ITEM_BASE (ID, NAME, DMG, RARITY)
values (1, 'Stiletto',1, 50);
insert into ITEM_BASE (ID,name, dmg, rarity)
values (2, 'Axe',2, 45);
insert into ITEM_BASE (ID,name, dmg, rarity)
values (3, 'Wooden helmet',3, 40);
insert into ITEM_BASE (ID,name, dmg, rarity)
values (4, 'Boots',4, 50);
insert into ITEM_BASE (ID,name, dmg, rarity)
values (5, 'Amulet charm',5, 35);
insert into ITEM_BASE (ID,name, dmg, rarity)
values (6, 'Lightning charm',6, 30);
insert into ITEM_BASE (ID,name, dmg, rarity)
values (7, 'None charm',7, 25);
insert into ITEM_BASE (ID,name, dmg, rarity)
values (8, 'Wooden sword',8, 24);
insert into ITEM_BASE (ID,name, dmg, rarity)
values (9, 'Steel sword',9,23);
insert into ITEM_BASE (ID,name, dmg, rarity)
values (10, 'Silver sword',10, 22);
insert into ITEM_BASE (ID,name, dmg, rarity)
values (11, 'Steel helmet',11, 21);
insert into ITEM_BASE (ID,name, dmg, rarity)
values (12, 'Silver helmet',12, 18);
insert into ITEM_BASE (ID,name, dmg, rarity)
values (13, 'Dragon shield',13, 17);
insert into ITEM_BASE (ID,name, dmg, rarity)
values (14, 'Wooden armor',14, 15);
insert into ITEM_BASE (ID,name, dmg, rarity)
values (15, 'Steel armor',15, 13);
insert into ITEM_BASE (ID,name, dmg, rarity)
values (16, 'Silver armor',16, 11);
insert into ITEM_BASE (ID,name, dmg, rarity)
values (17, 'Wooden armor',17, 10);
insert into ITEM_BASE (ID, name, dmg, rarity)
values (18, 'Wooden pants',18, 5);
insert into ITEM_BASE (ID,name, dmg, rarity)
values (19, 'Steel pants',19, 4);
insert into ITEM_BASE (ID,name, dmg, rarity)
values (20,'Silver pants',20, 3);



-- insert into INVENTORY_ITEM_LIST (INVENTORY_ID, ITEM_LIST_ID)
-- values (2, 8);
-- insert into INVENTORY_ITEM_LIST (INVENTORY_ID, ITEM_LIST_ID)
-- values (2, 9);
-- insert into INVENTORY_ITEM_LIST (INVENTORY_ID, ITEM_LIST_ID)
-- values (2, 10);
-- insert into INVENTORY_ITEM_LIST (INVENTORY_ID, ITEM_LIST_ID)
-- values (2, 11);
-- insert into INVENTORY_ITEM_LIST (INVENTORY_ID, ITEM_LIST_ID)
-- values (2, 12);
-- insert into INVENTORY_ITEM_LIST (INVENTORY_ID, ITEM_LIST_ID)
-- values (2, 13);
-- insert into INVENTORY_ITEM_LIST (INVENTORY_ID, ITEM_LIST_ID)
-- values (2, 14);
