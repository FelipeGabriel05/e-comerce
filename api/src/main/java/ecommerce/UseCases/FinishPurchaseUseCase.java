package ecommerce.UseCases;

import ecommerce.Database.DBConnection;
import ecommerce.Database.Entites.Product;
import ecommerce.Database.Repositories.ProductRepository;
import ecommerce.Database.Repositories.SaleRepository;
import ecommerce.Exceptions.InternalServerException;
import ecommerce.Exceptions.NotFoundException;
import ecommerce.Http.IO.Requests.CheckoutBodyRequest;
import java.sql.Connection;

public class FinishPurchaseUseCase {

  public void execute(int userId, CheckoutBodyRequest checkout)
      throws NotFoundException, InternalServerException {

    Connection con = null;

    try {
      con = DBConnection.getConnection();
      con.setAutoCommit(false);

      ProductRepository productRepository = new ProductRepository(con);

      SaleRepository saleRepository = new SaleRepository(con);

      int saleId = saleRepository.createSale(userId);

      for (CheckoutBodyRequest.CheckoutProductItemRequest item : checkout.products) {

        Product product = productRepository.findById(item.productId);

        if (product == null) {
          throw new NotFoundException("Product not found");
        }

        if (product.getQuantidade() < item.quantity) {
          throw new NotFoundException("Insufficient stock");
        }

        saleRepository.createSaleProduct(
            saleId, product.getId(), product.getPreco(), item.quantity);

        boolean stockUpdated =
            productRepository.decreaseProductStock(product.getId(), item.quantity);

        if (!stockUpdated) {
          throw new NotFoundException("Insufficient stock");
        }
      }

      con.commit();

    } catch (NotFoundException e) {

      rollback(con);
      throw e;

    } catch (Exception e) {

      rollback(con);
      throw new InternalServerException("Internal error while finishing purchase");

    } finally {

      close(con);
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

  private void close(Connection con) {
    try {
      if (con != null) {
        con.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
