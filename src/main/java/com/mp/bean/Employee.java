package com.mp.bean;


import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

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

    @TableField(exist = false)
    private Double salary;


    public Employee() {
    }

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

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
}
