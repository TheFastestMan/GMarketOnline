package ru.rail.gmarketonline.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.rail.gmarketonline.dto.UserDto;
import ru.rail.gmarketonline.service.UserService;
import ru.rail.gmarketonline.util.JspHelper;

import java.io.IOException;
import java.util.Optional;

@Log4j
@Component
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Autowired
    private final UserService userService;

    public LoginServlet(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Entering LoginServlet's doGet method");
        req.getRequestDispatcher(JspHelper.getJspFormat("login")).forward(req, resp);
        log.info("Exiting LoginServlet's doGet method");
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Entering LoginServlet's doPost method");

        String email = req.getParameter("email");
        String password = req.getParameter("password");
        Optional<UserDto> userOptional = userService.login(email, password);

        if (email != null && password != null && userOptional.isPresent()) {
            UserDto user = userOptional.get();


            req.getSession().setAttribute("user", user);
            resp.sendRedirect("/user");

        } else {
            resp.sendRedirect("/login?error&email=" + email);

        }

        log.info("Exiting LoginServlet's doPost method");

    }
}