####  提前先在hive test的库下创建好test的表
~~~~
create table  test(
id int,
name string
)
ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t';
~~~~


~~~~sql
CREATE CATALOG testmyhive WITH (
    'type' = 'hive',
    'default-database' = 'test',
    'hive-conf-dir' = '/alidata/server/zhp/catalog/config'
);
USE CATALOG testmyhive;

insert into test.test values(4,'n2');

~~~~
