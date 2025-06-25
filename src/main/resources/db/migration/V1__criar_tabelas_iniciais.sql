-- =====================
-- Tabela de Personagem
-- =====================
CREATE TABLE personagem (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    chakra INT,
    vida INT,
    categoria_ninja VARCHAR(50) NOT NULL CHECK (
        categoria_ninja IN ('NINJA_DE_NINJUTSU', 'NINJA_DE_GENJUTSU', 'NINJA_DE_TAIJUTSU')
    ),
    idade INT
);

-- =================
-- Tabela de Jutsus
-- =================
CREATE TABLE jutsu (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    dano INT NOT NULL,
    consumo_de_chakra INT NOT NULL,
    personagem_id BIGINT,
    FOREIGN KEY (personagem_id) REFERENCES personagem(id)
);

-- =================
-- Dados de Exemplo
-- =================

-- Personagem 1
INSERT INTO personagem (nome, chakra, vida, categoria_ninja, idade)
VALUES ('saulo', 10, 10, 'NINJA_DE_TAIJUTSU',46);

INSERT INTO jutsu (nome, dano, consumo_de_chakra, personagem_id)
VALUES ('soco', 10, 5, (SELECT MAX(id) FROM personagem));

-- Personagem 2
INSERT INTO personagem (nome, chakra, vida, categoria_ninja, idade)
VALUES ('combatente01', 100, 100, 'NINJA_DE_NINJUTSU',25);

INSERT INTO jutsu (nome, dano, consumo_de_chakra, personagem_id)
VALUES
('chute', 10, 20, (SELECT MAX(id) FROM personagem)),
('murro', 10, 5, (SELECT MAX(id) FROM personagem));

-- Personagem 3
INSERT INTO personagem (nome, chakra, vida, categoria_ninja, idade)
VALUES ('combatente02', 100, 100, 'NINJA_DE_GENJUTSU',30);

INSERT INTO jutsu (nome, dano, consumo_de_chakra, personagem_id)
VALUES
('braçada', 10, 5, (SELECT MAX(id) FROM personagem)),
('cotuvelada', 10, 5, (SELECT MAX(id) FROM personagem));

-- ===================
-- Tabela de Jogos
-- ===================
CREATE TABLE tabela_de_jogos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    personagem1_id BIGINT,
    personagem2_id BIGINT,
    pontos_ninja01 INT,
    pontos_ninja02 INT,
    FOREIGN KEY (personagem1_id) REFERENCES personagem(id),
    FOREIGN KEY (personagem2_id) REFERENCES personagem(id)
);
