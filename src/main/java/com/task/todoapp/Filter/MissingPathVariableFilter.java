package com.task.todoapp.Filter;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MissingPathVariableFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String uri = httpServletRequest.getRequestURI();

//        if(uri.matches(".*/delete/$")) {
//            httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,"Path variable cant be null: id required");
//            return;
//        }
//        if(uri.matches(".*/update/$")) {
//            httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,"Path variable cant be null: id required");
//            return;
//        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
