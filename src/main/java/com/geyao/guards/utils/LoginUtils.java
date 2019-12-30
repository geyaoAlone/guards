package com.geyao.guards.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.geyao.guards.commom.constant.CommonConst;
import com.geyao.guards.commom.vo.ResultVo;
import com.geyao.guards.constant.ManageConstant;
import com.geyao.guards.dao.RedisDao;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class LoginUtils {

    private static final String login_key_last = "_validate";
    private static final String login_session_key = "manage_info";


    /**
     * 判断登陆状态
     * @param request
     * @return
     */
    public static boolean judgeLoginStatus(HttpServletRequest request){
        return judgeBySession(request);
        //return judgeByRedis(request);
    }


    /**
     * 保存登陆状态
     * @param request
     * @return
     */
    public static boolean saveLoginStatus(HttpServletRequest request,JSONObject userInfo){
        try{
            saveBySession(request,userInfo);
            //saveByRedis(request,userInfo);
            return true;
        }catch (Exception e){
            return false;
        }

    }

    /**
     * 退出登陆状态
     * @param request
     * @return
     */
    public static void logout(HttpServletRequest request){
        logoutBySession(request);
        //logoutByRedis(request)

    }



    private static boolean judgeBySession(HttpServletRequest request){
        try {
            JSONObject userInfo = (JSONObject)request.getSession().getAttribute(login_session_key);
            if(userInfo == null){
                return false;
            }else{
                /*
                if(!IpUtils.getIpAddress(request).equals(userInfo.getString("ip"))){
                    request.getSession().removeAttribute(login_session_key);
                    return false;
                }
                */
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }



    private static void saveBySession(HttpServletRequest request,JSONObject userInfo) throws Exception{
        request.getSession().setAttribute(login_session_key ,userInfo);
    }

    private static void logoutBySession(HttpServletRequest request){
        request.getSession().removeAttribute(login_session_key);
        request.getSession().removeAttribute(IpUtils.getIpAddress(request));
    }



    private static void saveByRedis(HttpServletRequest request,JSONObject userInfo) throws Exception{
        try {
            RedisDao dao = (RedisDao) SpringUtils.getBean("redisDao");
            dao.set(IpUtils.getIpAddress(request) + login_key_last ,JSON.toJSONString(userInfo));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private static boolean judgeByRedis(HttpServletRequest request){
        try {
            String ip = IpUtils.getIpAddress(request);
            RedisDao dao = (RedisDao) SpringUtils.getBean("redisDao");
            boolean loginStatus = dao != null && dao.hasKey(ip + login_key_last);
            if (loginStatus) {
                String userName = JSON.parseObject((String) dao.get(ip + login_key_last)).getString("userName");
                if (StringUtils.isEmpty(userName) || !ArrayUtils.contains(ManageConstant.MANAGE_USER_NAME, userName)) {
                    loginStatus = false;
                }
            }
            return loginStatus;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    private static void logoutByRedis(HttpServletRequest request){
        try {
            RedisDao dao = (RedisDao) SpringUtils.getBean("redisDao");
            dao.del(IpUtils.getIpAddress(request) + login_key_last);
            request.getSession().removeAttribute(IpUtils.getIpAddress(request));
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
