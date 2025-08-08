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
