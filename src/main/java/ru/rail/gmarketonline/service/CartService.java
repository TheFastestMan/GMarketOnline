package ru.rail.gmarketonline.service;

import lombok.extern.log4j.Log4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rail.gmarketonline.dto.ProductDto;
import ru.rail.gmarketonline.dto.UserDto;
import ru.rail.gmarketonline.entity.Product;
import ru.rail.gmarketonline.entity.User;
import ru.rail.gmarketonline.repository.CartRepository;

@Log4j
@Service
public class CartService {
    @Autowired
    private final ModelMapper modelMapper;
    @Autowired
    private final CartRepository cartRepository;

    public CartService(ModelMapper modelMapper, CartRepository cartRepository) {
        this.modelMapper = modelMapper;
        this.cartRepository = cartRepository;
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

    @Transactional
    public void addProductToCart(UserDto userDto, ProductDto productDto, int quantity) throws Exception {
        log.info("Cart service enters into addProductToCart");
        User user = convertUserDtoToUser(userDto);
        Product product = convertProductDtoToProduct(productDto);

        cartRepository.addProductToCart(user, product, quantity);
    }
}
