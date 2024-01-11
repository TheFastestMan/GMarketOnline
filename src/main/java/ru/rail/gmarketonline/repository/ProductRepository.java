package ru.rail.gmarketonline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.rail.gmarketonline.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p join p.userProducts up join up.user u where u.id = :userId")
    List<Product> getProductsByUserId(@Param("userId") Long userId);

    @Modifying
    @Query("update Product p set p.quantity = p.quantity - :quantityToDecrease where p.id = :productId")
    void decreaseQuantityByAmount(@Param("productId") Long productId, @Param("quantityToDecrease") int quantityToDecrease);
}
