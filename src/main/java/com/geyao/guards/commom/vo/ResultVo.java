package com.geyao.guards.commom.vo;

import lombok.Data;

@Data
public class ResultVo {

    private int status;//返回状态：-1 -错误；0 -失败；1 -成功；2 -特殊判断

    private String massage;

    private Object object;//多加一个参数的情况

    public ResultVo(int status,String massage){
        this.status = status;
        this.massage = massage;
    }


    public ResultVo(int status,String massage,Object object){
        this.status = status;
        this.massage = massage;
        this.object = object;
    }



}
