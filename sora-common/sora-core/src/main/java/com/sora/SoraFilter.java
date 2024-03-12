package com.sora;

import jakarta.servlet.*;

import java.io.IOException;

/**
 * @Classname SoraFIlter
 * @Description
 * @Date 2024/02/23 16:30
 * @Author by Sora33
 */
public class SoraFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        filterChain.doFilter();
    }
}
