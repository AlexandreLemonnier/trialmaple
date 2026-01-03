RENAME TABLE trial_map TO tm_map;
RENAME TABLE trial_map_authors TO tm_map_authors;
ALTER TABLE tm_map_authors RENAME COLUMN trial_map_id TO tm_map_id;
ALTER TABLE daily_map RENAME COLUMN trial_map_id TO tm_map_id;
ALTER TABLE tm_map RENAME COLUMN world_record TO wr_time;
-- ALTER TABLE tm_map ADD COLUMN wr_year INT(11);
-- ALTER TABLE tm_map ADD COLUMN map_list VARCHAR(50);
-- TODO Table tm_user et wr_holder_id
UPDATE tm_map SET map_list='TMNF_TRIAL_HARDEST';
UPDATE daily_map SET game_mode='CLASSIC_TMNF_TRIAL';