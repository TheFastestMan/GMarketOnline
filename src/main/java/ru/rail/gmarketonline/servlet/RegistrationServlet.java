package ru.rail.gmarketonline.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import ru.rail.gmarketonline.dto.UserDto;
import ru.rail.gmarketonline.entity.Gender;
import ru.rail.gmarketonline.entity.Role;
import ru.rail.gmarketonline.service.UserService;
import ru.rail.gmarketonline.util.JspHelper;


import java.io.IOException;
@Log4j
@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Entering RegistrationServlet's doGet method");
        req.setAttribute("roles", Role.values());
        req.setAttribute("genders", Gender.values());
        req.getRequestDispatcher(JspHelper.getJspFormat("registration")).forward(req, resp);
        log.info("Exiting RegistrationServlet's doGet method");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Entering RegistrationServlet's doPost method");
        String name = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        Role role = Role.valueOf(req.getParameter("role"));
        Gender gender = Gender.valueOf(req.getParameter("gender"));

        UserDto userDto = UserDto.builder()
                .username(name)
                .email(email)
                .password(password)
                .role(Role.valueOf(String.valueOf(role)))
                .gender(Gender.valueOf(String.valueOf(gender)))
                .build();

        try {
            userService.create(userDto);
            resp.sendRedirect("login");
        } catch (Exception e) {
            log.error("Error in RegistrationServlet create method", e);
            doGet(req, resp);
        }
        log.info("Exiting RegistrationServlet's doGet method");
    }
}
