package com.xk.dbtool.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@TableName("")
public  class DbInfoEntity {

    @TableField(value="id")
    private String id;
    @TableField(value="driver_class_name")
    private String driverClassName;
    @TableField(value="user_name")
    private String userName;
    @TableField(value="pwd")
    private String pwd;
    @TableField(value="url")
    private String url;
    @TableField(value="initial_size")
    private int initialSize;
    @TableField(value="max_active")
    private int maxActive;
    @TableField(value="min_idle")
    private int minIdle;
    @TableField(value="max_wait")
    private int maxWait;

}
