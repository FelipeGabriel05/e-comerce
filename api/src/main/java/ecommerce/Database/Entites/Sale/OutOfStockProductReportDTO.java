package ecommerce.Database.Entites.Sale;

public class OutOfStockProductReportDTO {
  private int id;
  private String descricao;
  private double preco;
  private String foto;
  private int quantidade;
  private int categoriaId;

  public OutOfStockProductReportDTO(
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

  public String getDescricao() {
    return descricao;
  }

  public double getPreco() {
    return preco;
  }

  public String getFoto() {
    return foto;
  }

  public int getQuantidade() {
    return quantidade;
  }

  public int getCategoriaId() {
    return categoriaId;
  }
}
