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

insert into ITEM_BASE (ID, DMG, RARITY, AREA_ID) values (1, 10, 50, 1);
insert into ITEM_BASE (ID, DMG, RARITY, AREA_ID) values (2, 12, 45, 2);
insert into ITEM_BASE (ID, DMG, RARITY, AREA_ID) values (3, 14, 40, 3);
insert into ITEM_BASE (ID, DMG, RARITY, AREA_ID) values (4, 16, 35, 4);
insert into ITEM_BASE (ID, DMG, RARITY, AREA_ID) values (5, 18, 30, 5);

-- CARDS{
insert into CARD (ID,name, type, cost, dmg, defense)
values (1,'karta1', 'atakujaca', 5, 10, 5);
insert into CARD (ID,name, type, cost, dmg, defense)
values (2,'karta2', 'atak', 1, 2, 1);
insert into CARD (ID,name, type, cost, dmg, defense)
values (3,'karta3', 'hybrid', 2, 2, 2);
insert into CARD (ID,name, type, cost, dmg, defense)
values (4,'karta4', 'hybrid', 4, 4, 4);
insert into CARD (ID,name, type, cost, dmg, defense)
values (5,'karta5', 'atak', 2, 4, 2);
insert into CARD (ID,name, type, cost, dmg, defense)
values (6,'karta6', 'atak', 1, 2, 1);
insert into CARD (ID,name, type, cost, dmg, defense)
values (7,'karta7', 'atak', 5, 10, 5);
insert into CARD (ID, name,type, cost, dmg, defense)
values (8,'karta8', 'atak', 6, 12, 6);
insert into CARD (ID,name, type, cost, dmg, defense)
values (9,'karta9', 'obrona', 7, 7, 14);
insert into CARD (ID,name, type, cost, dmg, defense)
values (10,'karta10', 'atak', 1, 2, 1);
insert into CARD (ID,name, type, cost, dmg, defense)
values (11,'karta11', 'atak', 2, 4, 2);

--DECK
-- insert into DECK (ID) values (1);
-- insert into DECK (ID) values (2);
-- insert into DECK (ID) values (3);
-- insert into DECK (ID) values (4);
-- insert into DECK (ID) values (5);
-- insert into DECK (ID) values (6);

-- --DECK CARD
-- insert into DECK_CARD (ID, CARD_ID, DECK_ID) values (1, 1,1);
-- insert into DECK_CARD (ID, CARD_ID, DECK_ID) values (2, 2,1);
-- insert into DECK_CARD (ID, CARD_ID, DECK_ID) values (3, 3,1);
--
-- insert into DECK_CARD (ID, CARD_ID, DECK_ID) values (4, 4,2);
-- insert into DECK_CARD (ID, CARD_ID, DECK_ID) values (5, 5,2);
-- insert into DECK_CARD (ID, CARD_ID, DECK_ID) values (6, 6,2);
--
-- insert into DECK_CARD (ID, CARD_ID, DECK_ID) values (7, 7,3);
-- insert into DECK_CARD (ID, CARD_ID, DECK_ID) values (8, 8,3);
-- insert into DECK_CARD (ID, CARD_ID, DECK_ID) values (9, 1,3);





--
-- -- -- PLAYERS
-- -- insert into PLAYER (ID, EXPERIENCE, NAME, STAGE, DECK_ID, INVENTORY_ID)
-- -- values (1, 5000, 'Nora', 1, 1, 1);
-- -- insert into PLAYER (ID, EXPERIENCE, NAME, STAGE, DECK_ID, INVENTORY_ID)
-- -- values (2, 4000, 'Eamon', 1, 2, 2);
-- -- insert into PLAYER (ID, EXPERIENCE, NAME, STAGE, DECK_ID, INVENTORY_ID)
-- -- values (3, 3000, 'Nessa', 1, 1, 2);
-- -- insert into PLAYER (ID, EXPERIENCE, NAME, STAGE, DECK_ID, INVENTORY_ID)
-- -- values (4, 2000, 'Liam', 1, 3, 1);
-- -- insert into PLAYER (ID, EXPERIENCE, NAME, STAGE, DECK_ID, INVENTORY_ID)
-- -- values (5, 1000, 'Quinn', 1, 1, 4);
-- -- insert into PLAYER (ID, EXPERIENCE, NAME, STAGE, DECK_ID, INVENTORY_ID)
-- -- values (6, 500, 'Sinead', 1, 2, 3);
-- --

-- --
-- --
-- -- -- --DECK
-- -- -- insert into DECK_CARDS (DECK_ID, CARDS_ID)values (1, 1);
-- -- -- insert into DECK_CARDS (DECK_ID, CARDS_ID)values (1, 3);
-- -- -- insert into DECK_CARDS (DECK_ID, CARDS_ID)values (1, 5);
-- -- -- insert into DECK_CARDS (DECK_ID, CARDS_ID)values (1, 7);
-- -- -- insert into DECK_CARDS (DECK_ID, CARDS_ID)values (1, 9);
-- -- --
-- -- -- insert into DECK_CARDS (DECK_ID, CARDS_ID)values (2, 11);
-- -- -- insert into DECK_CARDS (DECK_ID, CARDS_ID)values (2, 4);
-- -- -- insert into DECK_CARDS (DECK_ID, CARDS_ID)values (2, 6);
-- -- -- insert into DECK_CARDS (DECK_ID, CARDS_ID)values (2, 8);
-- -- -- insert into DECK_CARDS (DECK_ID, CARDS_ID)values (2, 10);
-- -- --
-- -- -- insert into DECK_CARDS (DECK_ID, CARDS_ID)values (3, 1);
-- -- -- insert into DECK_CARDS (DECK_ID, CARDS_ID)values (3, 2);
-- -- -- insert into DECK_CARDS (DECK_ID, CARDS_ID)values (3, 3);
-- -- -- insert into DECK_CARDS (DECK_ID, CARDS_ID)values (3, 4);
-- -- -- insert into DECK_CARDS (DECK_ID, CARDS_ID)values (3, 5);
-- -- --
-- -- -- insert into DECK_CARDS (DECK_ID, CARDS_ID)values (4, 6);
-- -- -- insert into DECK_CARDS (DECK_ID, CARDS_ID)values (4, 7);
-- -- -- insert into DECK_CARDS (DECK_ID, CARDS_ID)values (4, 8);
-- -- -- insert into DECK_CARDS (DECK_ID, CARDS_ID)values (4, 9);
-- -- -- insert into DECK_CARDS (DECK_ID, CARDS_ID)values (4, 10);
-- --
-- -- -- ITEM BONUS
-- -- -- insert into ITEM_BONUS (ID, BONUS_ID, ITEM_ID)
-- -- -- values (1, 1, 1);
-- -- -- insert into ITEM_BONUS (ID, BONUS_ID, ITEM_ID)
-- -- -- values (2, 1, 2);
-- -- -- insert into ITEM_BONUS (ID, BONUS_ID, ITEM_ID)
-- -- -- values (3, 1, 3);
-- -- -- insert into ITEM_BONUS (ID, BONUS_ID, ITEM_ID)
-- -- -- values (4, 1, 4);
-- -- -- insert into ITEM_BONUS (ID, BONUS_ID, ITEM_ID)
-- -- -- values (5, 2, 1);
-- -- -- insert into ITEM_BONUS (ID, BONUS_ID, ITEM_ID)
-- -- -- values (6, 2, 3);
-- -- -- insert into ITEM_BONUS (ID, BONUS_ID, ITEM_ID)
-- -- -- values (7, 3, 4);
-- -- -- insert into ITEM_BONUS (ID, BONUS_ID, ITEM_ID)
-- -- -- values (8, 4, 4);
-- --
-- --
-- --
-- --
-- --
-- --
-- --
-- --
