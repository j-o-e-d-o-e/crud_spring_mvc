-- docker run --name mysqldb -p 3306:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -d mysql

-- Create db
-- CREATE DATABASE joe_dev;
-- CREATE DATABASE joe_prod;

-- Create service accounts using wildcard for host
-- CREATE USER 'joe_dev'@'%' IDENTIFIED BY 'doe';
-- CREATE USER 'joe_prod'@'%' IDENTIFIED BY 'doe';

-- Grants for users accessing their corresponding db
-- GRANT SELECT, INSERT, DELETE, UPDATE ON joe_dev.* TO 'joe_dev'@'%';
-- GRANT SELECT, INSERT, DELETE, UPDATE ON joe_prod.* TO 'joe_prod'@'%';