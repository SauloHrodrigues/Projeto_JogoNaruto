-- =====================
-- Tabela de Personagem
-- =====================
CREATE TABLE personagem (
    id BIGSERIAL PRIMARY KEY,
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
    id BIGSERIAL PRIMARY KEY,
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
VALUES ('saulo', 10, 10, 'NINJA_DE_TAIJUTSU', 46);

INSERT INTO jutsu (nome, dano, consumo_de_chakra, personagem_id)
VALUES ('soco', 10, 5, (SELECT id FROM personagem WHERE nome = 'saulo'));

-- Personagem 2
INSERT INTO personagem (nome, chakra, vida, categoria_ninja, idade)
VALUES ('combatente01', 100, 100, 'NINJA_DE_NINJUTSU', 25);

INSERT INTO jutsu (nome, dano, consumo_de_chakra, personagem_id)
VALUES
('chute', 10, 20, (SELECT id FROM personagem WHERE nome = 'combatente01')),
('murro', 10, 5, (SELECT id FROM personagem WHERE nome = 'combatente01'));

-- Personagem 3
INSERT INTO personagem (nome, chakra, vida, categoria_ninja, idade)
VALUES ('combatente02', 100, 100, 'NINJA_DE_GENJUTSU', 30);

INSERT INTO jutsu (nome, dano, consumo_de_chakra, personagem_id)
VALUES
('braçada', 10, 5, (SELECT id FROM personagem WHERE nome = 'combatente02')),
('cotuvelada', 10, 5, (SELECT id FROM personagem WHERE nome = 'combatente02'));

-- ===================
-- Tabela de Jogos
-- ===================
CREATE TABLE tabela_de_jogos (
    id BIGSERIAL PRIMARY KEY,
    personagem1_id BIGINT,
    personagem2_id BIGINT,
    pontos_ninja01 INT,
    pontos_ninja02 INT,
    FOREIGN KEY (personagem1_id) REFERENCES personagem(id),
    FOREIGN KEY (personagem2_id) REFERENCES personagem(id)
);
