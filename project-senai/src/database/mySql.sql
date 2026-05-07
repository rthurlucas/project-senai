DROP database cantinasenai;
CREATE DATABASE cantinasenai;
USE cantinasenai;

CREATE TABLE usuario (
                         id_usuario   BIGINT AUTO_INCREMENT PRIMARY KEY,
                         nome         VARCHAR(255) NOT NULL,
                         cpf          VARCHAR(14)  NOT NULL UNIQUE,
                         telefone     VARCHAR(20),
                         email        VARCHAR(255) NOT NULL UNIQUE,
                         tipo_usuario VARCHAR(20)  NOT NULL
);

CREATE TABLE produto (
                         id_produto        BIGINT AUTO_INCREMENT PRIMARY KEY,
                         nome_produto      VARCHAR(255) NOT NULL UNIQUE,
                         descricao_produto VARCHAR(255),
                         produto_ativo     BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE estoque (
                         id_estoque BIGINT AUTO_INCREMENT PRIMARY KEY,
                         id_produto BIGINT NOT NULL,
                         quantidade INT    NOT NULL DEFAULT 0,
                         FOREIGN KEY (id_produto) REFERENCES produto(id_produto)
);

CREATE TABLE pedido (
                        id_pedido     BIGINT AUTO_INCREMENT PRIMARY KEY,
                        id_usuario    BIGINT NOT NULL,
                        data_pedido   DATETIME NOT NULL,
                        status_pedido VARCHAR(20) NOT NULL DEFAULT 'PENDENTE',
                        FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

CREATE TABLE quantidade_produto (
                                    id_quantidade_produto BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    id_pedido             BIGINT NOT NULL,
                                    id_produto            BIGINT NOT NULL,
                                    quantidade            INT    NOT NULL,
                                    FOREIGN KEY (id_pedido)  REFERENCES pedido(id_pedido),
                                    FOREIGN KEY (id_produto) REFERENCES produto(id_produto)
);

SELECT * FROM produto WHERE id_produto = 1;
describe produto;