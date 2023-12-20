package ru.rail.gmarketonline.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.rail.gmarketonline.dto.UserDto;
import ru.rail.gmarketonline.entity.Role;


import java.io.IOException;

@WebFilter("/admin/*")
public class AdminFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        UserDto user = (UserDto) req.getSession().getAttribute("user");

        if (user == null || !user.getRole().equals(Role.ADMIN)) {
            HttpServletResponse resp = (HttpServletResponse) response;
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
            return;
        }

      //  log.info("User with role {} and name {} has access to the admin page", user.getRole(), user.getUsername());

        chain.doFilter(request, response);

        //5

    }
}
