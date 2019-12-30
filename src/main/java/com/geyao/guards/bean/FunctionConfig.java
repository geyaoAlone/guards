package com.geyao.guards.bean;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name="function_config")
public class FunctionConfig implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "function_No",nullable = false,length=30)
    private String functionNo;

    @Column(name = "function_Name",length=200)
    private String functionName;

    @Column(name = "function_Switch")
    private boolean functionSwitch;


    @Column(name = "description",length=500)
    private String description;

    @Column(name = "invoke_Url",length=100)
    private String invokeUrl;


    @Column(name = "param_Rule",length=300)
    private String paramRule;

    @Column(name = "do_Time")
    private Date doTime;



}
