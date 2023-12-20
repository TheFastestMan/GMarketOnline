package ru.rail.gmarketonline.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import ru.rail.gmarketonline.dto.UserDto;
import ru.rail.gmarketonline.service.UserService;
import ru.rail.gmarketonline.util.JspHelper;


import java.util.List;
@Log4j
@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    private final UserService userService = UserService.getInstance();

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        log.info("Entering AdminServlet's doGet method");
        List<UserDto> allUsers = userService.findAllUser();
        req.setAttribute("users", allUsers);
        req.getRequestDispatcher(JspHelper.getJspFormat("adminPanel")).forward(req, resp);
        log.info("Exiting AdminServlet's doGet method");
    }
}
