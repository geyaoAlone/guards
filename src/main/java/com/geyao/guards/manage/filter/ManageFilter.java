package com.geyao.guards.manage.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.geyao.guards.manage.constant.ManageConstant;
import com.geyao.guards.utils.IpUtils;
import com.geyao.guards.utils.LoginUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;


public class ManageFilter implements Filter {



    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String uri = request.getRequestURI();
        String ip = IpUtils.getIpAddress(request);
        //登陆状态：true-已登陆；false-未登录；
        boolean loginStatus = LoginUtils.judgeLoginStatus(request);


        //转向状态：true-需要转向；false-不需要
        boolean redirectStatus = false;
        try {
            //不需要过滤的Url
            if (ArrayUtils.contains(ManageConstant.NO_FILTER_URL,uri)) {

                if(loginStatus) {
                    redirectStatus = true;
                }

            }else {

                if(!loginStatus) {
                    //Ajax请求处理
                    if (!StringUtils.isBlank(request.getHeader("x-requested-with"))
                            && request.getHeader("x-requested-with").equals("XMLHttpRequest")) {
                        System.err.println("This is a Ajax request!");
                        JSONObject result = new JSONObject();
                        result.put("status","0");
                        result.put("message",ManageConstant.NO_LOGIN);
                        response.setContentType("application/json;charset=UTF-8");
                        PrintWriter writer = response.getWriter();
                        writer.write(JSON.toJSONString(result));
                        writer.close();
                        response.flushBuffer();
                    }else {
                        redirectStatus = true;
                    }
                }

            }

        }catch (Exception e){
            e.printStackTrace();
            redirectStatus = true;
        }

        if(redirectStatus){
            String redirectUrl = ManageConstant.MANAGE_FIRST_PAGE;
            if(loginStatus){
                redirectUrl = ManageConstant.MANAGE_LOGIN_SUCC_ADDRE;
            }
            response.sendRedirect(request.getContextPath() + redirectUrl);
         }else{
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {
    }


}
