package ecommerce.Database.Entites.Sale;

public class DailySalesReportDTO {
  private String date;
  private int totalOrders;
  private double totalRevenue;

  public DailySalesReportDTO(String date, int totalOrders, double totalRevenue) {
    this.date = date;
    this.totalOrders = totalOrders;
    this.totalRevenue = totalRevenue;
  }

  public String getDate() {
    return date;
  }

  public int getTotalOrders() {
    return totalOrders;
  }

  public double getRevenue() {
    return totalRevenue;
  }
}
