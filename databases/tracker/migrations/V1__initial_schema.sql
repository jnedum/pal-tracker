select '\n\nCompleted creating table' AS '';

CREATE TABLE time_entries (
  id         BIGINT(20) NOT NULL AUTO_INCREMENT,
  project_id BIGINT(20),
  user_id    BIGINT(20),
  date       VARCHAR(255),
  hours      INT,

  PRIMARY KEY (id)
)
  ENGINE = innodb
  DEFAULT CHARSET = utf8;

select * from information_schema.tables
where table_name = 'time_entries' and table_schema='tracker_test';

select '\n\nCompleted creating table' AS '';