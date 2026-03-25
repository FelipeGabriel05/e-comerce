CREATE TABLE venda_produto (
    venda_id INTEGER NOT NULL,
    produto_id INTEGER NOT NULL,
    preco NUMERIC(10,2) NOT NULL,
    quantidade INTEGER NOT NULL,
    PRIMARY KEY (venda_id, produto_id),
    CONSTRAINT fk_vp_venda
        FOREIGN KEY (venda_id)
        REFERENCES venda(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_vp_produto
        FOREIGN KEY (produto_id)
        REFERENCES produto(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);
