DROP TABLE IF EXISTS jutsu;
DROP TABLE IF EXISTS personagem;

CREATE TABLE personagem (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    chakra INT,
    vida INT,
    categoria_ninja ENUM('NINJA_DE_NINJUTSU', 'NINJA_DE_GENJUTSU', 'NINJA_DE_TAIJUTSU') NOT NULL,
    idade INT
);

CREATE TABLE jutsu (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    dano INT NOT NULL,
    consumo_de_chakra INT NOT NULL,
    personagem_id BIGINT,
    FOREIGN KEY (personagem_id) REFERENCES personagem(id)
);