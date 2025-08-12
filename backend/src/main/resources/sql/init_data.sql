-- DROP DATABASE trialmaple_db;
-- CREATE DATABASE trialmaple_db;
-- Canopus

INSERT INTO trial_map(name, nb_checkpoints, difficulty, points, nb_finishers, world_record, active) VALUES ('Canopus', 13, 'CHALLENGING', 5, 166, 'PT6M29.65S', 1);

INSERT INTO trial_map_authors(trial_map_id, author) VALUES (LAST_INSERT_ID(), 'Techno');

-- Elysium

INSERT INTO trial_map(name, nb_checkpoints, difficulty, points, nb_finishers, world_record, active) VALUES ('Elysium', 10, 'CHALLENGING', 5, 166, 'PT5M40.52S', 1);

INSERT INTO trial_map_authors(trial_map_id, author) VALUES (LAST_INSERT_ID(), 'simo');

-- Lavender Town

INSERT INTO trial_map(name, nb_checkpoints, difficulty, points, nb_finishers, world_record, active) VALUES ('Lavender Town', 21, 'CHALLENGING', 5, 285, 'PT8M46.72S', 1);

INSERT INTO trial_map_authors(trial_map_id, author) VALUES (LAST_INSERT_ID(), 'simo');
INSERT INTO trial_map_authors(trial_map_id, author) VALUES (LAST_INSERT_ID(), 'igntuL');

-- Chaos Emeralds

INSERT INTO trial_map(name, nb_checkpoints, difficulty, points, nb_finishers, world_record, active) VALUES ('Chaos Emeralds', 20, 'EXPERT', 25, 44, 'PT30M50.17S', 1);

INSERT INTO trial_map_authors(trial_map_id, author) VALUES (LAST_INSERT_ID(), 'nixion');

-- Decimation

INSERT INTO trial_map(name, nb_checkpoints, difficulty, points, nb_finishers, world_record, active) VALUES ('Decimation', 188, 'EXTREME', 70, 19, 'PT3H33M33.29S', 1);

INSERT INTO trial_map_authors(trial_map_id, author) VALUES (LAST_INSERT_ID(), 'simo');
INSERT INTO trial_map_authors(trial_map_id, author) VALUES (LAST_INSERT_ID(), 'loe');
INSERT INTO trial_map_authors(trial_map_id, author) VALUES (LAST_INSERT_ID(), 'nixion');

-- tree house

INSERT INTO trial_map(name, nb_checkpoints, difficulty, points, nb_finishers, world_record, active) VALUES ('tree house', 6, 'EXTREME', 70, 14, 'PT25M44.09S', 1);

INSERT INTO trial_map_authors(trial_map_id, author) VALUES (LAST_INSERT_ID(), 'flex');

-- zurg

INSERT INTO trial_map(name, nb_checkpoints, difficulty, points, nb_finishers, world_record, active) VALUES ('zurg', 4, 'INHUMANE', 120, 9, 'PT30M02.41S', 1);

INSERT INTO trial_map_authors(trial_map_id, author) VALUES (LAST_INSERT_ID(), 'simo');
