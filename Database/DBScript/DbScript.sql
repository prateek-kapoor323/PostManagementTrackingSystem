CREATE DATABASE IF NOT EXISTS scgj_pmts;
GRANT ALL PRIVILEGES ON *.* TO 'pmts_db_user'@'localhost' IDENTIFIED BY 'pMtS1234$$' with GRANT option;
GRANT SELECT  ON `scgj_pmts`.* TO 'pmts_user'@'localhost' IDENTIFIED BY 'myteam';
GRANT SELECT,UPDATE,INSERT,DELETE  ON `scgj_pmts`.* TO 'pmts_app_user'@'localhost' IDENTIFIED BY 'pMtS@123$$#';

