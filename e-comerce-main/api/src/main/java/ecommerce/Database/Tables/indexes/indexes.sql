CREATE INDEX IF NOT EXISTS idx_produto_categoria ON produto(categoria_id);
CREATE INDEX IF NOT EXISTS idx_venda_usuario ON venda(usuario_id);
CREATE INDEX IF NOT EXISTS idx_vp_produto ON venda_produto(produto_id);
