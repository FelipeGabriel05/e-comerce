-- Dev seeds — run after all CREATE TABLE scripts
-- Clears data and resets sequences

TRUNCATE TABLE venda_produto, venda, produto, usuario, categoria
    RESTART IDENTITY CASCADE;

-- Categorias
INSERT INTO categoria (descricao) VALUES
    ('Ação'),
    ('RPG'),
    ('Estratégia'),
    ('Aventura'),
    ('Simulação'),
    ('Terror');

-- Usuarios
INSERT INTO usuario (nome, endereco, email, login, senha, administrador) VALUES
    ('Admin',       'Rua Admin, 1',       'admin@dev.local', 'admin', 'admin123', TRUE),
    ('João Silva',  'Rua das Flores, 42', 'joao@dev.local',  'joao',  'joao123',  FALSE);

-- Produtos
INSERT INTO produto (descricao, preco, quantidade, categoria_id, foto) VALUES
    ('Counter-Strike 2',          00.00, 999, 1, 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/730/header.jpg?t=1749053861'),
    ('GTA V',                    149.90, 999, 1, 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/3240220/header.jpg?t=1765479644'),
    ('Red Dead Redemption 2',    299.90, 200, 1, 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1174180/header.jpg?t=1759502961'),
    ('Elden Ring',               274.50, 150, 2, 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1245620/header.jpg?t=1767883716'),
    ('Cyberpunk 2077',           199.90, 200, 2, 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1091500/e9047d8ec47ae3d94bb8b464fb0fc9e9972b4ac7/header.jpg?t=1769690377'),
    ('The Witcher 3: Wild Hunt', 129.99, 300, 2, 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/292030/ad9240e088f953a84aee814034c50a6a92bf4516/header.jpg?t=1768303991'),
    ('Baldur''s Gate 3',         149.99, 120, 2, 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1086940/48a2fcbda8565bb45025e98fd8ebde8a7203f6a0/header.jpg?t=1777363040'),
    ('Hollow Knight',             46.99, 500, 4, 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/367520/3c3489495136b26b34f8a9543c7f5645b99d388c/header.jpg?t=1776125684'),
    ('Stardew Valley',            24.99, 999, 5, 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/413150/header.jpg?t=1754692865'),
    ('Resident Evil Requiem',    299.00, 100, 6, 'https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/3764200/ce5437442768e38eb575f205ab9397d0264017b0/header.jpg?t=1772587704');

-- Vendas
INSERT INTO venda (data_hora, usuario_id) VALUES
    ('2026-04-20 10:30:00', 2),
    ('2026-04-24 18:45:00', 2);

-- Itens das vendas
INSERT INTO venda_produto (venda_id, produto_id, preco, quantidade) VALUES
    (1, 4, 199.90, 1),   -- venda 1: Elden Ring
    (1, 7, 229.90, 1),   -- venda 1: Baldur's Gate 3
    (2, 1,  34.90, 1),   -- venda 2: CS2
    (2, 6,  79.90, 1),   -- venda 2: Witcher 3
    (2, 11, 27.90, 1);   -- venda 2: Stardew Valley
