package gestionInventario.com.repository;

import gestionInventario.com.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer,Long> {
//    @Query(value = "SELECT * FROM customer c WHERE c.id IN (" +
//            "SELECT s.customer_id FROM sale s " +
//            "JOIN product_sale ps ON ps.sale_id = s.sale_id " +
//            "ORDER BY ps.total_price DESC LIMIT 1)",
//            nativeQuery = true)
//    public Customer getMostExpensive();

    @Query(value = "SELECT * FROM customer WHERE id IN (SELECT c.id FROM order_entity o JOIN" +
            " customer c ON o.customer_id = c.id GROUP BY c.id ORDER BY sum(price_total) DESC limit 1)",nativeQuery = true)
    Optional <Customer> getCustomerWhitMostBuys();
}