package ecommerce.Database.Entites.Sale;

public class CustomerReportDTO {
    private int userId;
    private String userName;
    private int totalPurchases;

    public CustomerReportDTO(int userId, String userName, int totalPurchases){
        this.userId = userId;
        this.userName = userName;
        this.totalPurchases = totalPurchases;
    }

    public int getUserId(){
        return userId;
    }

    public String getUserName(){
        return userName;
    }

    public int getTotalPurchases(){
        return totalPurchases;
    }
}