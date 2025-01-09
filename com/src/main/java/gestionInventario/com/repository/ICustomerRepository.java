package gestionInventario.com.repository;

import gestionInventario.com.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer,Long> {

    @Query(value = "SELECT * FROM customer WHERE id IN (SELECT c.id FROM order_entity o JOIN" +
            " customer c ON o.customer_id = c.id GROUP BY c.id ORDER BY sum(price_total) DESC limit 1)",nativeQuery = true)
    Optional <Customer> getCustomerWhitMostBuys();
}