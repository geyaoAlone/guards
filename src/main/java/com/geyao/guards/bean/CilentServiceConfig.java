package com.geyao.guards.bean;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

//@Entity(name="EntityName") 必须，用来标注一个数据库对应的实体，数据库中创建的表名默认和类名一致。其中，name 为可选，对应数据库中一个表，使用此注解标记 Pojo 是一个 JPA 实体。
//@Table(name=""，catalog=""，schema="") 可选，用来标注一个数据库对应的实体，数据库中创建的表名默认和类名一致。通常和 @Entity 配合使用，只能标注在实体的 class 定义处，表示实体对应的数据库表的信息。
//@Id 必须，@Id 定义了映射到数据库表的主键的属性，一个实体只能有一个属性被映射为主键。
//@GeneratedValue(strategy=GenerationType，generator="") 可选，strategy: 表示主键生成策略，有 AUTO、INDENTITY、SEQUENCE 和 TABLE 4 种，分别表示让 ORM 框架自动选择，generator: 表示主键生成器的名称。
//@Column(name = "user_code"， nullable = false， length=32) 可选，@Column 描述了数据库表中该字段的详细定义，这对于根据 JPA 注解生成数据库表结构的工具。name: 表示数据库表中该字段的名称，默认情形属性名称一致；nullable: 表示该字段是否允许为 null，默认为 true；unique: 表示该字段是否是唯一标识，默认为 false；length: 表示该字段的大小，仅对 String 类型的字段有效。
//@Transient可选，@Transient 表示该属性并非一个到数据库表的字段的映射，ORM 框架将忽略该属性。
//@Enumerated 可选，使用枚举的时候，我们希望数据库中存储的是枚举对应的 String 类型，而不是枚举的索引值，需要在属性上面添加 @Enumerated(EnumType.STRING) 注解。


@Data
@Entity
@Table(name="client_service_config")
public class CilentServiceConfig implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "client_No",nullable = false,length=30)
    private String clientNo;

    @Column(name = "client_Name",length=100)
    private String clientName;

    @Column(name = "service_Url",length=50)
    private String serviceUrl;

    @Column(name = "service_Switch")
    private boolean serviceSwitch;

    @Column(name = "interface_Url")
    private String interfaceUrl;

    @Column(name = "use_Function",length=1000)
    private String useFunction;

    @Column(name = "secret_Key",length=100)
    private String secretKey;

    @Column(name = "remark",length=200)
    private String remark;

    @Column(name = "do_Time")
    private Date doTime;

    @Column(name = "client_url",nullable = false,length=100)
    private String clientUrl;





}
