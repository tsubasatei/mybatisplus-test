package com.mp.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mp.bean.Employee;

/**
 * Mapper 接口
 *
 *  基于 Mybatis: 在 Mapper 接口中编写 CRUD 相关的方法，提供 Mapper 接口所对应的 SQL 映射文件 以及 方法对应的 sql 语句
 *
 *  基于 MP：让 XxxMapper 接口继承 BaseMapper 接口即可
 *          BaseMapper<T>: 泛型指定当前 Mapper 接口所操作的实体类类型
 */
public interface EmployeeMapper extends BaseMapper<Employee> {

    int deleteAll();
}
