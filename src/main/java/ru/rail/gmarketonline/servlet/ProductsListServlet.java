package ru.rail.gmarketonline.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import lombok.extern.log4j.Log4j;
import ru.rail.gmarketonline.dto.ProductDto;
import ru.rail.gmarketonline.service.ProductService;
import ru.rail.gmarketonline.util.JspHelper;


import java.io.IOException;
import java.util.List;

@Log4j
@WebServlet("/products")
public class ProductsListServlet extends HttpServlet {
    private final ProductService productService = ProductService.getInstance();

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


       log.info("Entering ProductsListServlet's doGet method");

        try {
            List<ProductDto> products = productService.getAllProducts();
            System.out.println("Number of products retrieved: " + products.size());
            req.setAttribute("pr", products);
            req.getRequestDispatcher(JspHelper.getJspFormat("products")).forward(req, resp);
        } catch (Exception e) {
          log.error("Error processing the request", e);

            req.getRequestDispatcher(JspHelper.getJspFormat("error")).forward(req, resp);
        }

       log.info("Exiting ProductsListServlet's doGet method");
    }
}

