package ru.rail.gmarketonline.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.rail.gmarketonline.dto.ProductDto;
import ru.rail.gmarketonline.dto.UserDto;
import ru.rail.gmarketonline.entity.Gender;
import ru.rail.gmarketonline.entity.Product;
import ru.rail.gmarketonline.entity.Role;
import ru.rail.gmarketonline.entity.User;
import ru.rail.gmarketonline.repository.CartRepository;
import ru.rail.gmarketonline.service.CartService;

import static org.hamcrest.Matchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CartServiceIT {
    @Autowired
    private CartService cartService;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private CartRepository cartRepository;

    @BeforeEach
    void setUp() {
        when(modelMapper.map(any(ProductDto.class), eq(Product.class)))
                .thenAnswer(invocation -> {
                    ProductDto dto = invocation.getArgument(0);
                    Product product = new Product();
                    product.setId(dto.getId());
                    product.setProductName(dto.getProductName());
                    product.setDescription(dto.getDescription());
                    product.setPrice(dto.getPrice());
                    return product;
                });
        when(modelMapper.map(any(UserDto.class), eq(User.class)))
                .thenAnswer(invocation -> {
                    UserDto dto = invocation.getArgument(0);
                    User user = new User();
                    user.setUsername(dto.getUsername());
                    user.setPassword(dto.getPassword());
                    return user;
                });
    }

    @Test
    void addProductToCartTest() throws Exception {
        // Declare and initialize necessary variables for UserDto
        Long userId = 1L; // Example ID
        String username = "testUser";
        String password = "password123";
        Role role = Role.USER; // Replace with actual Role enum value
        Gender gender = Gender.MALE; // Replace with actual Gender enum value
        String email = "test@example.com"; // Declare the email variable

        // Assuming UserDto has a builder pattern
        UserDto userDto = UserDto.builder()
                .id(userId)
                .username(username)
                .password(password)
                .role(role)
                .gender(gender)
                .email(email)
                .build();

        // Create ProductDto instance (if required)
        // Assuming ProductDto has similar fields or a builder pattern
        ProductDto productDto = new ProductDto(); // Initialize appropriately

        int quantity = 1;

        cartService.addProductToCart(userDto, productDto, quantity);

        // Using Option 1 or Option 2
        cartService.addProductToCart(userDto, productDto, quantity);

        // Corrected verify call
        verify(cartRepository).addProductToCart(ArgumentMatchers
                .any(User.class), ArgumentMatchers.any(Product.class), eq(quantity));
    }


}
