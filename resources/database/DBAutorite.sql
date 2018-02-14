CREATE DATABASE `autorite`;
USE `autorite`;

CREATE TABLE IF NOT EXISTS `prix_articles` (
  `code` varchar(15) NOT NULL,
  `prix` varchar(6) NOT NULL,
  `designation` varchar(20) NOT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `prix_articles`(
`code`,
`prix`,
`designation`)
VALUES
("FAMI21","150","Famir 25"),
("XENO25","850","Xenon 25"),
("ZENI77","500","Zenith 77");
