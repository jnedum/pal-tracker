select '\n\nStarting to create databases...' AS '';

drop database if exists tracker_dev;
drop database if exists tracker_test;

create user 'tracker'@'localhost'
  identified by '';
grant all privileges on *.* to 'tracker' @'localhost';

create database tracker_dev;
create database tracker_test;

select '\n\nCompleted creating databases' AS '';