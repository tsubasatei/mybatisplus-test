package com.xt.mp.controller;


import com.xt.mp.bean.Employee;
import com.xt.mp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xt
 * @since 2019-11-18
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/list")
    public String list(Map<String, Object> map) {
        List<Employee> employees = employeeService.selectList(null);
        map.put("emps", employees);
        return "list";
    }
}

