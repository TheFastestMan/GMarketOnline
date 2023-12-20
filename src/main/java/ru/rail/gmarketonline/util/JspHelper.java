package ru.rail.gmarketonline.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JspHelper {
    private static final String JSP_FORMAT = "/WEB-INF/jsp/%s.jsp";

    public static String getJspFormat(String jsp) {
        return JSP_FORMAT.formatted(jsp);
    }
}
