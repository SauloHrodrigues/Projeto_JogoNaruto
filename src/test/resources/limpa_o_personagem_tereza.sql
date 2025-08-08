DELETE j
FROM jutsu j
JOIN personagem p ON j.personagem_id = p.id
WHERE p.nome = 'tereza';

-- Apaga o personagem
DELETE FROM personagem WHERE nome = 'tereza';