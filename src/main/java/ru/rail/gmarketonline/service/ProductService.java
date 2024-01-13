package ru.rail.gmarketonline.service;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import lombok.extern.log4j.Log4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rail.gmarketonline.dto.ProductDto;
import ru.rail.gmarketonline.entity.Product;
import ru.rail.gmarketonline.repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j
@Service
public class ProductService {
    @Autowired
    private final ModelMapper modelMapper;
    @Autowired
    private final ProductRepository productRepository;

    public ProductService(ModelMapper modelMapper, ProductRepository productRepository) {
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
    }

    private ProductDto convertProductToProductDto(Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        productDto.setId(product.getId());
        productDto.setProductName(product.getProductName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        return productDto;
    }

    private Product convertProductDtoToProduct(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        product.setId(productDto.getId());
        product.setProductName(productDto.getProductName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        return product;
    }

    public List<ProductDto> getAllProducts() throws Exception {
        log.info("Product service enters into getAllProducts");
        return productRepository.findAll().stream().map(product ->
                new ProductDto(product.getId(),
                        product.getProductName(),
                        product.getDescription(),
                        product.getQuantity(),
                        product.getPrice())).collect(Collectors.toList());
    }

    public Optional<ProductDto> getProductById(Long productId) {
        log.info("Product service enters into getProductById");
        Optional<Product> productOptional = productRepository.findById(productId);
        return productOptional.map(this::convertProductToProductDto);
    }


    public List<ProductDto> getProductsByUserId(Long userId) throws Exception {
        log.info("Product service enters into getProductsByUserId");
        List<Product> products = productRepository.getProductsByUserId(userId);
        return products.stream()
                .map(product -> new ProductDto(product.getId(), product.getProductName()
                        , product.getDescription(), product.getQuantity(), product.getPrice()))
                .collect(Collectors.toList());
    }

    public void reduceQuantityByOne(Long productId, int quantityToDecrease) {
        log.info("Product service enters into reduceQuantityByOne");
        productRepository.decreaseQuantityByAmount(productId, quantityToDecrease);
    }

    @Transactional
    public void addProduct(ProductDto productDto) {
        log.info("Product service enters into addProduct");
//        var validationFactory = Validation.buildDefaultValidatorFactory();
//        var validator = validationFactory.getValidator();
//        var validationResult = validator.validate(productDto);
//
//        if (!validationResult.isEmpty()) {
//            throw new ConstraintViolationException(validationResult);
//        }

        Product product = convertProductDtoToProduct(productDto);
        productRepository.save(product);
    }
}
