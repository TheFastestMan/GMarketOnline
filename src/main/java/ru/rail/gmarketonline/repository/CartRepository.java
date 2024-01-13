package ru.rail.gmarketonline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.rail.gmarketonline.entity.Cart;
import ru.rail.gmarketonline.entity.Product;
import ru.rail.gmarketonline.entity.User;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("select c from Cart c join fetch c.cartItems where c.user = :user")
    Optional<Cart> findCartForUser(@Param("user") User user);

    @Transactional
    @Modifying
    @Query("insert into CartItem (cart, product, quantity) select c, :product, :quantity from Cart c where c.user = :user")
    void addProductToCart(@Param("user") User user, @Param("product") Product product, @Param("quantity") int quantity);
}
