package com.mp.test;

import com.baomidou.mybatisplus.plugins.Page;
import com.mp.bean.Employee;
import com.mp.mapper.EmployeeMapper;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestMP {

    private ApplicationContext ioc = new ClassPathXmlApplicationContext("beans.xml");
    private EmployeeMapper employeeMapper = ioc.getBean("employeeMapper", EmployeeMapper.class);

    /**
     * 通用删除操作 Delete
     */
    @Test
    public void testDelete () {
        // 1. 根据 id 进行删除
//        Integer result = employeeMapper.deleteById(16);
//        System.out.println(result);

        // 2. 根据条件进行删除
//        Map<String, Object> map = new HashMap<>();
//        map.put("last_name", "DD");
//        map.put("gender", "0");
//        Integer result2 = employeeMapper.deleteByMap(map);
//        System.out.println(result2);

        // 3. 批量删除
        Integer result3 = employeeMapper.deleteBatchIds(Arrays.asList(9, 12, 13));
        System.out.println(result3);
    }

    /**
     * 通用查询操作：Select
     */
    @Test
    public void testSelect () {

        // 1. 通过 id 查询
        Employee employee = employeeMapper.selectById(7);
        System.out.println(employee);

        System.out.println("===========");

        /**
         * 2. 通过多个列进行查询 id + lastName
         * selectOne: 当查询到多条记录会报错
         */
        Employee employee1 = new Employee();
        employee1.setId(7);
        employee1.setLastName("sanae");
        employee1.setGender("0");
        Employee employee2 = employeeMapper.selectOne(employee1);
        System.out.println(employee2);

        System.out.println("===========");

        // 3. 通过多个 id 进行查询
        List<Employee> employees = employeeMapper.selectBatchIds(Arrays.asList(1, 2, 3, 4));
        employees.forEach(System.out::println);

        System.out.println("============");

        /**
         * 4. 通过 Map 封装条件查询
         * Map 的 key 写数据库中的列名
         */
        Map<String, Object> map = new HashMap<>();
        map.put("last_name", "sanae");
        map.put("gender", "0");
        List<Employee> employees1 = employeeMapper.selectByMap(map);
        employees1.forEach(System.out::println);

        // 5. 分页查询
        List<Employee> employees2 = employeeMapper.selectPage(new Page<>(3, 2), null);
        employees2.forEach(System.out::println);

    }

    /**
     * 通用更新操作：Update
     * updateById：动态更新改变的字段
     * updateAllColumnById：更新全部字段，可能将原有字段变为空
     */
    @Test
    public void testCommonUpdate () {
        Employee employee = new Employee();
        employee.setId(6);
        employee.setLastName("sanae");
        employee.setEmail("sanae@163.com");
        employee.setAge(18);
//        employee.setGender("0");

//        Integer result = employeeMapper.updateById(employee);
        Integer result = employeeMapper.updateAllColumnById(employee);
        System.out.println(result);
    }

    /**
     * 通用插入操作 Insert
     */
    @Test
    public void testCommonInsert () {

        Employee employee = new Employee("DD", "dd@163.com", "0", null);
        employee.setSalary(20000.0);
        /**
         * insert 方法: 插入时，会根据实体类的每个属性进行非空判断，只有非空的属性对应的字段才会出现到 SQL 语句中
         *
         * insertAllColumn 方法：插入时，不管属性是否非空，属性所对应的字段都会出现到 SQL 语句中
         */
//        int result = employeeMapper.insert(employee);
        Integer result = employeeMapper.insertAllColumn(employee);
        System.out.println(result);

        // 获取当前数据在数据库中的主键值
        Integer key = employee.getId();
        System.out.println("key: " + key);
    }

    @Test
    public void testDataSource () throws SQLException {
        DataSource dataSource = (DataSource) ioc.getBean("dataSource");
        System.out.println(dataSource);

        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }
}
