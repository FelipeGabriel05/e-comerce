package ecommerce.Database.Entites;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Category {

  private int id;
  private String descricao;

  public Category() {}

  public Category(int id, String descricao) {
    this.id = id;
    this.descricao = descricao;
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

  public String toJson() {
    Gson gson = new GsonBuilder().serializeNulls().create();
    return gson.toJson(this);
  }
}
