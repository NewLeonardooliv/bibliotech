CREATE TABLE IF NOT EXISTS `amigos` (
    `ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `nome` VARCHAR(2000) NOT NULL,
    `documento` VARCHAR(45) NOT NULL,
    `status` VARCHAR(15) NOT NULL,
    PRIMARY KEY (`ID`)
);
CREATE TABLE IF NOT EXISTS `editoras` (
    `ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `razao_social` VARCHAR(200) NOT NULL,
    `status` VARCHAR(15) NOT NULL,
    PRIMARY KEY (`ID`)
);
CREATE TABLE IF NOT EXISTS `autores` (
    `ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `nome` VARCHAR(200) NOT NULL,
    `documento` VARCHAR(50) NOT NULL,
    `status` VARCHAR(15) NOT NULL,
    PRIMARY KEY (`ID`)
);
CREATE TABLE IF NOT EXISTS `emprestimos` (
    `ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `data` Date NOT NULL,
    `status` VARCHAR(15) NOT NULL,
    `amigo` BIGINT(20) NOT NULL,
    PRIMARY KEY (`ID`)
);
CREATE TABLE IF NOT EXISTS `livros` (
    `ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `titulo` VARCHAR(250) NOT NULL,
    `status` VARCHAR(15) NOT NULL,
    `editora` BIGINT(20) NOT NULL,
    `autor` BIGINT(20) NOT NULL,
    PRIMARY KEY (`ID`)
);