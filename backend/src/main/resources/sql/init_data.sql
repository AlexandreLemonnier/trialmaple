-- Canopus

INSERT INTO trial_map(name, nb_checkpoints, difficulty, points, nb_finishers, world_record, active) VALUES ('Canopus', 13, 'CHALLENGING', 5, 166, 'PT6M29.65S', 1);

INSERT INTO trial_map_authors(trial_map_id, author) VALUES (LAST_INSERT_ID(), 'Techno');

-- Chaos Emeralds

INSERT INTO trial_map(name, nb_checkpoints, difficulty, points, nb_finishers, world_record, active) VALUES ('Chaos Emeralds', 20, 'EXPERT', 25, 44, 'PT30M50.17S', 1);

INSERT INTO trial_map_authors(trial_map_id, author) VALUES (LAST_INSERT_ID(), 'nixion');

-- tree house

INSERT INTO trial_map(name, nb_checkpoints, difficulty, points, nb_finishers, world_record, active) VALUES ('tree house', 6, 'EXTREME', 70, 14, 'PT25M44.09S', 1);

INSERT INTO trial_map_authors(trial_map_id, author) VALUES (LAST_INSERT_ID(), 'flex');