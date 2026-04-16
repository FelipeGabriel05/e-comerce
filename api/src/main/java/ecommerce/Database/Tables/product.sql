CREATE TABLE IF NOT EXISTS produto (
    id SERIAL PRIMARY KEY,
    descricao TEXT NOT NULL,
    preco NUMERIC(10,2) NOT NULL,
    foto VARCHAR(40),
    quantidade INTEGER NOT NULL DEFAULT 0,
    categoria_id INTEGER NOT NULL,
    CONSTRAINT fk_produto_categoria
        FOREIGN KEY (categoria_id)
        REFERENCES categoria(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);
