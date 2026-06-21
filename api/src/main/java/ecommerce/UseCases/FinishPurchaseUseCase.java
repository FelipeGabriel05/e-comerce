package ecommerce.UseCases;

import ecommerce.Database.DBConnection;
import ecommerce.Database.Entites.Cart.Cart;
import ecommerce.Database.Entites.Cart.CartItem;
import ecommerce.Database.Entites.Product;
import ecommerce.Database.Entites.Sale.Sale;
import ecommerce.Database.Entites.Sale.SaleItem;
import ecommerce.Database.Repositories.ProductRepository;
import ecommerce.Database.Repositories.SaleRepository;
import ecommerce.Exceptions.InternalServerException;
import ecommerce.Exceptions.NotFoundException;
import java.sql.Connection;

public class FinishPurchaseUseCase {

  public void execute(int userId, Cart cart) throws NotFoundException, InternalServerException {

    Connection con = null;

    try {
      con = DBConnection.getConnection();
      con.setAutoCommit(false);

      ProductRepository productRepository = new ProductRepository(con);
      SaleRepository saleRepository = new SaleRepository(con);

      Sale sale = new Sale();
      sale.setUserId(userId);

      double total = 0.0;

      for (CartItem item : cart.getItems()) {
        Product cartProduct = item.getProduct();

        Product product = productRepository.findById(cartProduct.getId());

        if (product == null) {
          throw new NotFoundException("Product not found");
        }

        if (product.getQuantidade() < item.getQuantity()) {
          throw new NotFoundException("Insufficient stock");
        }

        SaleItem saleItem = new SaleItem(product.getId(), product.getPreco(), item.getQuantity());

        sale.getItems().add(saleItem);

        total += product.getPreco() * item.getQuantity();

        boolean stockUpdated =
            productRepository.decreaseProductStock(product.getId(), item.getQuantity());

        if (!stockUpdated) {
          throw new NotFoundException("Insufficient stock");
        }
      }

      sale.setTotal(total);

      saleRepository.createSale(sale);

      if (sale.getId() <= 0) {
        throw new InternalServerException("Failed to create sale");
      }

      con.commit();

    } catch (NotFoundException e) {

      rollback(con);
      throw e;

    } catch (Exception e) {

      rollback(con);
      throw new InternalServerException("Internal error while finishing purchase");

    } finally {

      enableAutoCommit(con);
    }
  }

  private void rollback(Connection con) {
    try {
      if (con != null) {
        con.rollback();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void enableAutoCommit(Connection con) {
    try {
      if (con != null) {
        con.setAutoCommit(true);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
