package gestionInventario.com.repository;

import gestionInventario.com.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByUsername(String username);

//    @Query("SELECT o FROM OrderEntity o WHERE o.role = 'CUSTOMER'")
//    List<UserEntity> findUsersByRole();


    Boolean existsByUsername(String username);
}