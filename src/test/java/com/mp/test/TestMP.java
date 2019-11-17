package com.mp.test;

import com.mp.bean.Employee;
import com.mp.mapper.EmployeeMapper;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class TestMP {

    private ApplicationContext ioc = new ClassPathXmlApplicationContext("beans.xml");

    /**
     * 通用插入操作 Insert
     */
    @Test
    public void testCommonInsert () {
        EmployeeMapper employeeMapper = ioc.getBean("employeeMapper", EmployeeMapper.class);
        Employee employee = new Employee("DD", "dd@163.com", "0", 26);
        int result = employeeMapper.insert(employee);

        System.out.println(result);
    }

    @Test
    public void testDataSource () throws SQLException {
        DataSource dataSource = (DataSource) ioc.getBean("dataSource");
        System.out.println(dataSource);

        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }
}
