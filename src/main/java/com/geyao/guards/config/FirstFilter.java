package com.geyao.guards.config;

import com.geyao.guards.constant.ManageConstant;
import com.geyao.guards.utils.IpUtils;
import com.geyao.guards.utils.LoginUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FirstFilter implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(FirstFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String url = request.getRequestURI();
        String ip = IpUtils.getIpAddress(request);
        if(!url.startsWith("/static/")) {
            LOG.info("访问IP -> " + ip + " 访问地址 -> " + url);
        }
        if("/".equals(url) || "".equals(url)){
            if(LoginUtils.judgeLoginStatus(request)){
                response.sendRedirect(request.getContextPath() + ManageConstant.MANAGE_LOGIN_SUCC_ADDRE);
            }else{
                response.sendRedirect(request.getContextPath() + ManageConstant.MANAGE_FIRST_PAGE);
            }

        }else{

            filterChain.doFilter(servletRequest, servletResponse);
        }

    }

    @Override
    public void destroy() {

    }
}
