package ru.rail.gmarketonline.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Cleanup;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.rail.gmarketonline.dto.ProductDto;
import ru.rail.gmarketonline.service.ProductService;
import ru.rail.gmarketonline.util.JspHelper;

import java.io.IOException;

@Log4j
@Component
@WebServlet("/admin/addProducts")
public class AddProductsServlet extends HttpServlet {
    @Autowired
    private final ProductService productService;

    public AddProductsServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Entering AddProductsServlet's doGet method");
        req.getRequestDispatcher(JspHelper.getJspFormat("addProducts")).forward(req, resp);
        log.info("Exiting AddProductsServlet's doGet method");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Entering AddProductsServlet's doPost method");
        String name = req.getParameter("productName");
        String description = req.getParameter("description");
        Integer quantity = Integer.valueOf(req.getParameter("quantity"));
        Double price = Double.valueOf(req.getParameter("price"));

        ProductDto productDto = ProductDto.builder()
                .productName(name)
                .description(description)
                .quantity(quantity)
                .price(price)
                .build();

        try {
            productService.addProduct(productDto);
            resp.sendRedirect(req.getContextPath() + "/user");

        } catch (Exception e) {
            log.error("Error adding the product", e);
            throw new RuntimeException(e);

        }
    }


}

