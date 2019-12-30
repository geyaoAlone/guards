package com.geyao.guards.commom.controller;

import com.alibaba.fastjson.JSON;
import com.geyao.guards.commom.constant.CommonConst;
import com.geyao.guards.commom.vo.ResultVo;
import com.geyao.guards.utils.IpUtils;
import com.geyao.guards.utils.RsaUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 *
 */


public class BaseController {

    public ResultVo decodeData(HttpServletRequest request,Logger logger){

        String data = request.getParameter("data");
        if(StringUtils.isEmpty(data)){
            return new ResultVo(CommonConst.FAIL_STATUS,"请求参数空了！");
        }

        String privateKey = "";
        try {
            privateKey = (String)request.getSession().getAttribute(IpUtils.getIpAddress(request));
        }catch (Exception e) {
            logger.error("Get privateKey error! " + e.getMessage());
        }
        if(StringUtils.isEmpty(privateKey)){
            return new ResultVo(CommonConst.FAIL_STATUS,"获取密钥失败！");
        }

        String str = "";
        try {
            str = RsaUtils.decryptDataOnJava(data,privateKey);
        }catch (Exception e) {
            logger.error("Decode LoginData error! " + e.getMessage());
        }
        if(StringUtils.isEmpty(str)) {
            return new ResultVo(CommonConst.FAIL_STATUS,"请求参数解密异常！");
        }
        return new ResultVo(CommonConst.SUCCES_STATUS,"", str);

    }

}