package ru.rail.gmarketonline.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.rail.gmarketonline.dto.ProductDto;
import ru.rail.gmarketonline.dto.UserDto;
import ru.rail.gmarketonline.entity.*;
import ru.rail.gmarketonline.repository.CartRepository;
import ru.rail.gmarketonline.repository.ProductRepository;
import ru.rail.gmarketonline.repository.UserRepository;
import ru.rail.gmarketonline.service.CartService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CartServiceIT {
    @Autowired
    private CartService cartService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    private User savedUser;
    private Product savedProduct;

    @BeforeEach
    public void setUp() {
        //Set
        User user = new User();
        user.setUsername("ast");
        user.setEmail("ast");
        user.setPassword("ast");
        user.setRole(Role.USER);
        user.setGender(Gender.MALE);
        savedUser = userRepository.save(user);

        //Set
        Product product = new Product();
        product.setProductName("ast");
        product.setDescription("ast");
        product.setPrice(19.0);
        product.setQuantity(100);
        savedProduct = productRepository.save(product);

        //Set
        Cart cart = new Cart();
        cart.setUser(savedUser);
        cart.setCreatedAt(new Date(System.currentTimeMillis()));

        // If Cart doesn't initialize its cartItems list, do it here
        cart.setCartItems(new ArrayList<>());
        Cart savedCart = cartRepository.save(cart);

        // Create and add an existing CartItem to the Cart
        CartItem existingCartItem = new CartItem();
        existingCartItem.setCart(savedCart);
        existingCartItem.setProduct(savedProduct);
        existingCartItem.setQuantity(2);

        // Add the CartItem directly to the cartItems list
        savedCart.getCartItems().add(existingCartItem);

        // Save the cart again with the new item added
        cartRepository.save(savedCart);
    }

    @Test
    public void testAddProductToCart() throws Exception {
        //Set
        UserDto userDto = UserDto.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .role(savedUser.getRole())
                .gender(savedUser.getGender())
                .password(savedUser.getPassword())
                .build();

        //Set
        ProductDto productDto = ProductDto.builder()
                .id(savedProduct.getId())
                .productName(savedProduct.getProductName())
                .description(savedProduct.getDescription())
                .price(savedProduct.getPrice())
                .quantity(savedProduct.getQuantity())
                .build();

        //Add product to cart
        cartService.addProductToCart(userDto, productDto, 1);

        // Assert
        Optional<Cart> cartOptional = cartRepository.findCartForUser(savedUser);
        assertTrue(cartOptional.isPresent(), "Cart should be present");
        Cart cart = cartOptional.get();
        assertEquals(2, cart.getCartItems().size(), "Cart should have 2 items after adding a new one");
        CartItem addedCartItem = cart.getCartItems().get(1);
        assertEquals(savedProduct.getId(), addedCartItem.getProduct().getId(), "Product in cart should match");
        assertEquals(1, addedCartItem.getQuantity(), "Quantity should match");
    }
}