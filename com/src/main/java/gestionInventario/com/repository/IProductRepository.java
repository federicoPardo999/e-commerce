package gestionInventario.com.repository;

import gestionInventario.com.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product,Long> {
    @Query("SELECT p FROM Product p ORDER BY p.price DESC limit 1")
    public List<Product>  getMostExpensivesProducts();
}