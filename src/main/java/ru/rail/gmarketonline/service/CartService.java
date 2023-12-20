package ru.rail.gmarketonline.service;

import org.modelmapper.ModelMapper;
import ru.rail.gmarketonline.dto.ProductDto;
import ru.rail.gmarketonline.dto.UserDto;
import ru.rail.gmarketonline.entity.Product;
import ru.rail.gmarketonline.entity.User;
import ru.rail.gmarketonline.repository.CartRepository;


public class CartService {
    private final static CartService INSTANCE = new CartService();
    private final ModelMapper modelMapper = new ModelMapper();

    private CartRepository cartRepository = CartRepository.getInstance();

    private CartService() {
    }

    public static CartService getInstance() {
        return INSTANCE;
    }

    private Product convertProductDtoToProduct(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        product.setId(productDto.getId());
        product.setProductName(productDto.getProductName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        return product;
    }

    private User convertUserDtoToUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        return user;
    }

    public void addProductToCart(UserDto userDto, ProductDto productDto, int quantity) throws Exception {
        User user = convertUserDtoToUser(userDto);
        Product product = convertProductDtoToProduct(productDto);

        cartRepository.addProductToCart(user, product, quantity);
    }
}
