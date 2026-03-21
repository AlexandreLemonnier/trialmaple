alter table tm_map add column uuid uuid NOT NULL;
update tm_map set uuid=(select UUID());