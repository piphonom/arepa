#CREATE USER 'piphonom'@'localhost' IDENTIFIED BY 'monohpip';
GRANT CREATE, DROP, ALTER, SELECT, INSERT, UPDATE, EXECUTE, SHOW VIEW, LOCK TABLES ON `arepa%`.* to 'piphonom'@'%';
