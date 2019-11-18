package com.mp.test;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
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
     * 条件构造器 删除操作
     */
    @Test
    public void testEntityWrapperDelete () {
        employeeMapper.delete(new EntityWrapper<Employee>()
                .eq("last_name", "Tom")
                .eq("age", 40));
    }

    /**
     * 条件构造器 修改操作
     */
    @Test
    public void testEntityWrapperUpdate () {
        Employee employee = new Employee();
        employee.setLastName("Jerry");
        employee.setEmail("jerry@163.com");
        employee.setGender("0");
        employeeMapper.update(employee,
                new EntityWrapper<Employee>()
                        .eq("last_name", "Tom")
                        .eq("age", 30));
    }

    /**
     * 条件构造器 查询操作
     * EntityWrapper ：使用的是数据库字段，不是 Java属性 !
     */
    @Test
    public void testEntityWrapperSelect () {
        // 分页查询 tbl_employee 表中，年龄在18~50之间且性别为男且姓名为 Tom 的所有用户
        List<Employee> employees = employeeMapper.selectPage(new Page<Employee>(1, 2),
                new EntityWrapper<Employee>()
                        .between("age", 18, 50)
                        .eq("gender", "1")
                        .eq("last_name", "Tom"));
        employees.forEach(System.out::println);

        List<Employee> emps = employeeMapper.selectPage(new Page<Employee>(1, 2),
                Condition.create()
                        .between("age", 18, 50)
                        .eq("gender", "1")
                        .eq("last_name", "Tom"));
        emps.forEach(System.out::println);

        System.out.println("================");
        // 查询 tbl_employee 表中，性别为女并且名字中带有“老师” 或者邮箱中带有“a”
        List<Employee> employees1 = employeeMapper.selectList(new EntityWrapper<Employee>()
                .eq("gender", "0")
                .like("last_name", "老师")
//                .or()       // gender = ? AND last_name LIKE ? OR email LIKE ?
                .orNew()      // (gender = ? AND last_name LIKE ?) OR (email LIKE ?)
                .like("email", "a"));
        employees1.forEach(System.out::println);

        System.out.println("===========");

        // 查询性别为女的，根据 age 进行排序（asc/desc），简单分页
        List<Employee> employees2 = employeeMapper.selectList(new EntityWrapper<Employee>()
                .eq("gender", "0")
                .orderBy("age")
//                .orderDesc(Arrays.asList("age"))
                .last("desc limit 0, 3") // limit 初始偏移量为 0
        );
        employees2.forEach(System.out::println);
    }

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
