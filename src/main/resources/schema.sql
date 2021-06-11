DROP TABLE IF EXISTS `employees`;

CREATE TABLE if not exists `employees`(
  `id` bigint(20) auto_increment NOT NULL primary key,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `github_id` INT,
  `yearly_income` bigint(20) NOT NULL
);
