package com.mp.bean;


import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * javaBean
 *
 * 定义 JavaBean 中的成员变量时最好使用包装类：
 *      因为每个基本类型都有一个默认值：
 *          int ==> 0
 *          boolean ==> false
 *
 * @TableName : MybatisPlus 会默认使用实体类的类名到数据中查找对应的表
 *
 * 使用 AR 模式：仅仅需要让实体类继承 Model类且实现主键指定方法，即可开启
 */
//@TableName(value = "tbl_employee")
@Data
@NoArgsConstructor
public class Employee extends Model<Employee> {

    /**
     * @TableId :
     *      value: 指定表中的主键列的列名，如果实体属性名与列名一致，可以省略不指定
     *      type：指定主键策略
     */
//    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
//    @TableField(value = "last_name")
    private String lastName;
    private String email;
    private String gender;
    private Integer age;

    /**
     * @TableField(exist = false) : 表示当前属性不是数据库的字段，但在项目中必须使用，
     * 这样在新增等使用 bean 的时候，mybatis-plus就会忽略这个，不会报错
     */
    @TableField(exist = false)
    private Double salary;

    @Version
    private Integer version;

    /**
     * 指定主键
     * @return
     */
    @Override
    protected Serializable pkVal() {
        return id;
    }

    public Employee(String lastName, String email, String gender, Integer age) {
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.age = age;
    }

}
