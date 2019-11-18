package com.xt.mp.service.impl;

import com.xt.mp.bean.Employee;
import com.xt.mp.mapper.EmployeeMapper;
import com.xt.mp.service.EmployeeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xt
 * @since 2019-11-18
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    //不用再进行mapper的注入.

    /**
     * EmployeeServiceImpl  继承了 ServiceImpl
     * 1. 在 ServiceImpl中已经完成 Mapper 对象的注入,直接在 EmployeeServiceImpl 中进行使用
     * 2. 在 ServiceImpl中也帮我们提供了常用的 CRUD 方法， 基本的一些 CRUD方法在 Service 中不需要我们自己定义.
     *
     */
}
