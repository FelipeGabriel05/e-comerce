package ecommerce.Database.Entites.Sale;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ecommerce.Database.Entites.Product;

public class SaleItem {
  private int productId;
  private double price;
  private int quantity;
  private Product product;

  public SaleItem(int productId, double price, int quantity) {
    this.productId = productId;
    this.price = price;
    this.quantity = quantity;
  }

  public int getProductId() {
    return productId;
  }

  public void setProductId(int productId) {
    this.productId = productId;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public String toJson() {
    Gson gson = new GsonBuilder().serializeNulls().create();
    return gson.toJson(this);
  }
}
