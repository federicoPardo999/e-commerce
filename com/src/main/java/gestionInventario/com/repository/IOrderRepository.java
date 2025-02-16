package gestionInventario.com.repository;

import gestionInventario.com.model.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderRepository extends JpaRepository<OrderEntity,Long> {
    @Query("SELECT o FROM OrderEntity o WHERE o.user.id = :idCustomer")
    List<OrderEntity> findOrdersByCustomers(Long idCustomer);
}
