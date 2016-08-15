package com.epam.yoda.control.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author Sergey Mikhluk.
 */
public class EncodingFilter implements Filter {
    private static final Logger logger = Logger.getLogger(EncodingFilter.class.getName());
    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String encoding = request.getCharacterEncoding();
        logger.debug("doFilter.encoding: " + encoding);
        if (!"UTF-8".equalsIgnoreCase(encoding)) {
            response.setContentType("text/html; charset=UTF-8");
            request.setCharacterEncoding("UTF-8");
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
