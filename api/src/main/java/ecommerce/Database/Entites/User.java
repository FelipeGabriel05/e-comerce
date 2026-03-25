package ecommerce.Database.Entites;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class User {

  private int id;
  private String nome;
  private String endereco;
  private String email;
  private String login;
  private String senha;
  private boolean administrador;

  public User() {}

  public User(
      int id,
      String nome,
      String endereco,
      String email,
      String login,
      String senha,
      boolean administrador) {
    this.id = id;
    this.nome = nome;
    this.endereco = endereco;
    this.email = email;
    this.login = login;
    this.senha = senha;
    this.administrador = administrador;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getEndereco() {
    return endereco;
  }

  public void setEndereco(String endereco) {
    this.endereco = endereco;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  public boolean isAdministrador() {
    return administrador;
  }

  public void setAdministrador(boolean administrador) {
    this.administrador = administrador;
  }

  public String toJson() {
    Gson gson = new GsonBuilder().serializeNulls().create();
    return gson.toJson(this);
  }
}
