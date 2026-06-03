package ecommerce.UseCases;

import ecommerce.Database.DBConnection;
import ecommerce.Database.Entites.Cart.Cart;
import ecommerce.Database.Entites.Cart.CartItem;
import ecommerce.Database.Entites.Sale.Sale;
import ecommerce.Database.Entites.Sale.SaleItem;
import ecommerce.Database.Repositories.SaleRepository;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CreateSaleUseCase {

  public CreateSaleUseCase() {}

  public Sale execute(int userId, Cart cart) {
    Connection dbConnection = DBConnection.getConnection();
    SaleRepository saleRepository = new SaleRepository(dbConnection);

    Sale sale = new Sale();
    sale.setUserId(userId);
    sale.setDataHora(LocalDateTime.now().toString());
    sale.setItems(buildSaleItems(cart));
    sale.setTotal(cart.getTotal());

    return saleRepository.createSale(sale);
  }

  private List<SaleItem> buildSaleItems(Cart cart) {
    List<SaleItem> saleItems = new ArrayList<>();

    for (CartItem item : cart.getItems()) {
      saleItems.add(
          new SaleItem(
              item.getProduct().getId(), item.getProduct().getPreco(), item.getQuantity()));
    }

    return saleItems;
  }
}
