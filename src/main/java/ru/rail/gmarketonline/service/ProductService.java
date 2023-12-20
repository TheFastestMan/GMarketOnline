package ru.rail.gmarketonline.service;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import org.modelmapper.ModelMapper;
import ru.rail.gmarketonline.dto.ProductDto;
import ru.rail.gmarketonline.entity.Product;
import ru.rail.gmarketonline.repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProductService {
    private final static ProductService INSTANCE = new ProductService();
    private ModelMapper modelMapper = new ModelMapper();
    private ProductRepository productRepository = ProductRepository.getInstance();

    public static ProductService getInstance() {
        return INSTANCE;
    }

    private ProductService() {
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
        return productRepository.findAll().stream().map(product ->
                new ProductDto(product.getId(),
                        product.getProductName(),
                        product.getDescription(),
                        product.getQuantity(),
                        product.getPrice())).collect(Collectors.toList());
    }

    public Optional<ProductDto> getProductById(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        return productOptional.map(this::convertProductToProductDto);
    }


    public List<ProductDto> getProductsByUserId(Long userId) throws Exception {
        List<Product> products = productRepository.getProductsByUserId(userId);
        return products.stream()
                .map(product -> new ProductDto(product.getId(), product.getProductName()
                        , product.getDescription(), product.getQuantity(), product.getPrice()))
                .collect(Collectors.toList());
    }

    public void reduceQuantityByOne(Long productId, int quantityToDecrease) {
        productRepository.decreaseQuantityByAmount(productId, quantityToDecrease);
    }

    public void addProduct(ProductDto productDto) {
        var validationFactory = Validation.buildDefaultValidatorFactory();
        var validator = validationFactory.getValidator();
        var validationResult = validator.validate(productDto);

        if (!validationResult.isEmpty()) {
            throw new ConstraintViolationException(validationResult);
        }

        Product product = convertProductDtoToProduct(productDto);
        productRepository.save(product);
    }
}
