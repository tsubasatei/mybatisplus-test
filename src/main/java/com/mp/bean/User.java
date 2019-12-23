package com.mp.bean;


import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.FieldFill;
import lombok.Data;

@Data
public class User {
    private Integer id;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String username;

    // 逻辑删除属性
    @TableLogic
    private Integer logicFlag;

}
