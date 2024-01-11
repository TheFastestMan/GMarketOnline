package ru.rail.gmarketonline.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import ru.rail.gmarketonline.dto.UserDto;
import ru.rail.gmarketonline.util.JspHelper;


import java.io.IOException;
@Log4j
@Component
@WebServlet("/user")
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Entering UserServlet's doGet method");
        UserDto user = (UserDto) req.getSession().getAttribute("user");

        if (user == null) {
            req.setAttribute("error", "Please login to view user details.");
            req.getRequestDispatcher(JspHelper.getJspFormat("errorPage")).forward(req, resp);
            return;
        }

        req.setAttribute("user", user);
        req.getRequestDispatcher(JspHelper.getJspFormat("user")).forward(req, resp);
        log.info("Exiting UserServlet's doGet method");
    }


}
