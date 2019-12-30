package com.geyao.guards.manage.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.geyao.guards.bean.CilentServiceConfig;
import com.geyao.guards.commom.constant.CommonConst;
import com.geyao.guards.commom.controller.BaseController;
import com.geyao.guards.commom.vo.ResultVo;
import com.geyao.guards.dao.ConfigRepository;
import com.geyao.guards.dao.FunctionConfigRepository;
import com.geyao.guards.manage.constant.ManageConstant;
import com.geyao.guards.utils.IpUtils;
import com.geyao.guards.utils.LoginUtils;
import com.geyao.guards.utils.RsaUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 管理端
 */
@Controller
@RequestMapping("/manage")
public class ManageController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(ManageController.class);

    @Resource
    private ConfigRepository clientDao;
    @Resource
    private FunctionConfigRepository functionDao;


    /**
     * 打开登陆页
     * @param request
     * @return
     */
    @RequestMapping("/login")
    public String loginConfig(HttpServletRequest request){
        try{
            Map<String, Object> keyMap = RsaUtils.genKeyPair();

            //私钥存储在session里；key是访问IP
            request.getSession().setAttribute(IpUtils.getIpAddress(request), RsaUtils.getPrivateKey(keyMap));

            String publicKey = RsaUtils.getPublicKey(keyMap);
            request.setAttribute("publicKey",publicKey);

            return "login";
        }catch (Exception e){
            e.printStackTrace();
            return "result";

        }

    }

    /**
     *登陆操作
     * @param request
     * @return
     */
    @RequestMapping("/dologin")
    @ResponseBody
    public ResultVo dologin(HttpServletRequest request){

        ResultVo resultVo = decodeData(request,logger);
        if(CommonConst.SUCCES_STATUS != resultVo.getStatus()){
            return new ResultVo(CommonConst.FAIL_STATUS,"登陆失败!" + resultVo.getMassage());
        }

        JSONObject userJson = JSON.parseObject((String)resultVo.getObject());
        userJson.put("ip",IpUtils.getIpAddress(request));

        if(ArrayUtils.contains(ManageConstant.MANAGE_USER_NAME,userJson.getString("userName"))
            && ArrayUtils.contains(ManageConstant.MANAGE_PASSWORD,userJson.getString("password"))){

            if(LoginUtils.saveLoginStatus(request,userJson)) {

                return new ResultVo(CommonConst.SUCCES_STATUS,"登陆成功",ManageConstant.MANAGE_LOGIN_SUCC_ADDRE);
            }else {
                logger.error("Login SaveData Fail!");
                return new ResultVo(CommonConst.FAIL_STATUS,"登陆保存异常!请重新登陆！");
            }
        }else{
            return new ResultVo(CommonConst.FAIL_STATUS,"用户名或密码错误");
        }
    }

    /**
     *退出登陆操作
     * @param request
     * @return
     */
    @RequestMapping("/logout")
    @ResponseBody
    public ResultVo logout(HttpServletRequest request){

        ResultVo resultVo = decodeData(request,logger);
        if(CommonConst.SUCCES_STATUS != resultVo.getStatus()){
            return new ResultVo(CommonConst.FAIL_STATUS,"退出登陆失败!" + resultVo.getMassage());
        }

        String msg = (String)resultVo.getObject();
        logger.info("Logout:  " + msg);
        LoginUtils.logout(request);
        return new ResultVo(CommonConst.SUCCES_STATUS,"退出成功",ManageConstant.MANAGE_FIRST_PAGE);

    }


    @RequestMapping("/query/client")
    public String clientConfig(HttpServletRequest request){
        List<CilentServiceConfig> list = clientDao.queryByList();
        request.setAttribute("data",list);
        return "client-config";
    }

    @RequestMapping("/open/client")
    public String openDoClient(HttpServletRequest request){
        String clientNo = request.getParameter("clientNo");
        if(StringUtils.isNotBlank(clientNo)){
            CilentServiceConfig info = clientDao.queryByObj(clientNo);
            request.setAttribute("data",info);
        }
        request.setAttribute("functionList", functionDao.queryByList());
        return "do-client-info";
    }

    /**
     * 验证存储参数
     */
    private ResultVo checkclient(CilentServiceConfig cilentServiceConfig){
        if(cilentServiceConfig == null){
            return new ResultVo(CommonConst.FAIL_STATUS,"保存数据失败!数据有问题");
        }
        String clientName = cilentServiceConfig.getClientName();
        if(StringUtils.isNotBlank(clientName) && clientDao.queryByAloneParam("clientName",clientName) != null){
            return new ResultVo(CommonConst.FAIL_STATUS,"客户端名称已存在");
        }

        String clientUrl = cilentServiceConfig.getClientUrl();
        if(StringUtils.isNotBlank(clientUrl) && clientDao.queryByAloneParam("clientUrl",clientUrl) != null){
            return new ResultVo(CommonConst.FAIL_STATUS,"客户端URL已存在");
        }
        return new ResultVo(CommonConst.SUCCES_STATUS,"");
    }

    @RequestMapping("/save/client")
    @ResponseBody
    public ResultVo saveClient(HttpServletRequest request){

        ResultVo resultVo = decodeData(request,logger);
        if(CommonConst.SUCCES_STATUS != resultVo.getStatus()){
            return new ResultVo(CommonConst.FAIL_STATUS,"保存数据失败!" + resultVo.getMassage());
        }

        CilentServiceConfig cilentServiceConfig = null;
        try{
            cilentServiceConfig = JSON.parseObject((String)resultVo.getObject(),CilentServiceConfig.class);
            cilentServiceConfig.setClientNo(RandomStringUtils.randomAlphanumeric(30));
        }catch(Exception e){
            logger.error("客户端数据转对象异常！" + e.getMessage());
        }


        resultVo = checkclient(cilentServiceConfig);
        if(CommonConst.SUCCES_STATUS != resultVo.getStatus()){
            return resultVo;
        }
        try {
            clientDao.save(cilentServiceConfig);
        }catch (Exception e){
            logger.error("客户端数据保存异常！" + e.getMessage());
            return new ResultVo(CommonConst.FAIL_STATUS,"数据保存异常" + e.getMessage());
        }

        return new ResultVo(CommonConst.SUCCES_STATUS,"保存成功",ManageConstant.MANAGE_LOGIN_SUCC_ADDRE);

    }

    @RequestMapping("/query/function")
    public String systemFunction(HttpServletRequest request){
        List<CilentServiceConfig> list = clientDao.queryByList();
        request.setAttribute("data",list);
        return "system-function";
    }

    @RequestMapping("/query/record")
    public String clientInvokeRecord(HttpServletRequest request){
        List<CilentServiceConfig> list = clientDao.queryByList();
        request.setAttribute("data",list);
        return "client-invoke-record";
    }


    public static void main(String[] args){

    }

}
