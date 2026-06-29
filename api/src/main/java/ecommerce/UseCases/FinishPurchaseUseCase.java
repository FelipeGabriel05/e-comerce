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
import ecommerce.Exceptions.ValidationException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class FinishPurchaseUseCase {

  public void execute(int userId, Cart cart)
      throws NotFoundException, InternalServerException, ValidationException {

    Connection con = null;

    try {
      con = DBConnection.getConnection();
      con.setAutoCommit(false);

      ProductRepository productRepository = new ProductRepository(con);
      SaleRepository saleRepository = new SaleRepository(con);

      List<SaleItem> saleItems = buildSaleItems(cart, productRepository);

      double calculatedTotal = calculateTotal(saleItems);

      validateCartTotal(calculatedTotal, cart.getTotal());

      decreaseStock(saleItems, productRepository);

      Sale sale = new Sale();
      sale.setUserId(userId);
      sale.setItems(saleItems);
      sale.setTotal(calculatedTotal);

      saleRepository.createSale(sale);

      if (sale.getId() <= 0) {
        throw new InternalServerException("Failed to create sale");
      }

      con.commit();

    } catch (ValidationException e) {

      rollback(con);
      throw e;

    } catch (NotFoundException e) {

      rollback(con);
      throw e;

    } catch (Exception e) {

      rollback(con);
      throw e;

    } finally {

      enableAutoCommit(con);
    }
  }

  private List<SaleItem> buildSaleItems(Cart cart, ProductRepository productRepository)
      throws Exception {

    List<SaleItem> saleItems = new ArrayList<>();

    for (CartItem item : cart.getItems()) {
      Product cartProduct = item.getProduct();

      Product product = productRepository.findById(cartProduct.getId());

      if (product == null) {
        throw new NotFoundException("Product not found");
      }

      saleItems.add(new SaleItem(product.getId(), product.getPreco(), item.getQuantity()));
    }

    return saleItems;
  }

  private double calculateTotal(List<SaleItem> saleItems) {

    double total = 0.0;

    for (SaleItem item : saleItems) {
      total += item.getPrice() * item.getQuantity();
    }

    return total;
  }

  private void validateCartTotal(double calculatedTotal, double cartTotal)
      throws ValidationException {

    if (Math.abs(calculatedTotal - cartTotal) > 0.01) {
      throw new ValidationException("Cart total does not match product prices");
    }
  }

  private void decreaseStock(List<SaleItem> saleItems, ProductRepository productRepository)
      throws Exception {

    for (SaleItem item : saleItems) {
      boolean stockUpdated =
          productRepository.decreaseProductStock(item.getProductId(), item.getQuantity());

      if (!stockUpdated) {
        throw new NotFoundException("Insufficient stock");
      }
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
