CREATE TABLE usuario(
     id BIGINT PRIMARY KEY AUTO_INCREMENT,
     nome VARCHAR(100) NOT NULL,
     data_nascimento TIMESTAMP NOT NULL,
     foto LONGBLOB
)ENGINE=InnoDB DEFAULT CHARSET=utf8;