package ecommerce.Database.Entites;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Product {

  private int id;
  private String descricao;
  private double preco;
  private String foto;
  private int quantidade;
  private int categoriaId;

  public Product() {}

  public Product(
      int id, String descricao, double preco, String foto, int quantidade, int categoriaId) {
    this.id = id;
    this.descricao = descricao;
    this.preco = preco;
    this.foto = foto;
    this.quantidade = quantidade;
    this.categoriaId = categoriaId;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public double getPreco() {
    return preco;
  }

  public void setPreco(double preco) {
    this.preco = preco;
  }

  public String getFoto() {
    return foto;
  }

  public void setFoto(String foto) {
    this.foto = foto;
  }

  public int getQuantidade() {
    return quantidade;
  }

  public void setQuantidade(int quantidade) {
    this.quantidade = quantidade;
  }

  public int getCategoriaId() {
    return categoriaId;
  }

  public void setCategoriaId(int categoriaId) {
    this.categoriaId = categoriaId;
  }

  public String toJson() {
    Gson gson = new GsonBuilder().serializeNulls().create();
    return gson.toJson(this);
  }
}
