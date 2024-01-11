package ru.rail.gmarketonline.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import ru.rail.gmarketonline.dto.UserDto;
import ru.rail.gmarketonline.entity.Role;


import java.io.IOException;

@Log4j
@WebFilter("/admin/*")
public class AdminFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        log.info("Entering Admin filter");
        HttpServletRequest req = (HttpServletRequest) request;
        UserDto user = (UserDto) req.getSession().getAttribute("user");

        if (user == null || !user.getRole().equals(Role.ADMIN)) {
            HttpServletResponse resp = (HttpServletResponse) response;
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
            return;
        }

        chain.doFilter(request, response);
        log.info("Exiting Admin filter");


    }
}
