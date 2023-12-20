package ru.rail.gmarketonline.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import ru.rail.gmarketonline.dto.ProductDto;
import ru.rail.gmarketonline.service.ProductService;
import ru.rail.gmarketonline.util.JspHelper;


import java.io.IOException;
import java.util.List;
@Log4j
@WebServlet("/userItems")
public class UserItemsServlet extends HttpServlet {

    private final ProductService productService = ProductService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      log.info("Entering UserItemsServlet's doGet method");
        try {
            Long userId = Long.parseLong(req.getParameter("userId"));
            List<ProductDto> productDTOs = productService.getProductsByUserId(userId);

            req.setAttribute("userItems", productDTOs);
            req.getRequestDispatcher(JspHelper.getJspFormat("userItems")).forward(req, resp);
        } catch (Exception e) {
            log.error("Error in UserItemsServlet's method getProductsByUserId",e);
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to retrieve user items.");
        }
        log.info("Exiting UserItemsServlet's doGet method");
    }
}
