package com.mp.test;

import com.baomidou.mybatisplus.entity.GlobalConfiguration;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mp.bean.Employee;
import com.mp.bean.User;
import com.mp.mapper.EmployeeMapper;
import com.mp.mapper.UserMapper;
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
    private UserMapper userMapper = ioc.getBean("userMapper", UserMapper.class);

    /**
     * 测试逻辑删除
     */
    @Test
    public void testLogicDelete() {

		Integer result = userMapper.deleteById(2);
		System.out.println("result:" +result );

        User user = userMapper.selectById(2);
        System.out.println(user);
    }

    /**
     * 测试自定义全局操作
     */
    @Test
    public void testMySqlInjector() {
        int result = employeeMapper.deleteAll();
        System.out.println("result: " + result);
    }

    /**
     * 测试 乐观锁插件
     */
    @Test
    public void testOptimisticLocker() {
        Employee employee = new Employee();
        employee.setId(6);
        employee.setLastName("高桥老师");
        employee.setEmail("hashi@sina.com");
        employee.setGender("0");
        employee.setAge(30);
        employee.setVersion(2);

        Integer result = employeeMapper.updateById(employee);
        System.out.println("result: " + result);
        System.out.println(employee);
    }

    /**
     * 测试 性能分析插件
     */
    @Test
    public void testPerformance() {
        Employee employee = new Employee();
        employee.setLastName("玛利亚老师");
        employee.setEmail("mly@sina.com");
        employee.setGender("0");
        employee.setAge(22);

        employeeMapper.insert(employee);

    }

    /**
     * 测试SQL执行分析插件
     */
    @Test
    public void testSQLExplain() {
        Integer result = employeeMapper.delete(null);
        System.out.println("result: " + result);
    }

    /**
     * 测试分页插件
     */
    @Test
    public void testPage() {
        Page<Employee> page = new Page<>(1, 1);
        List<Employee> employees = employeeMapper.selectPage(page, null);

        employees.forEach(System.out::println);

        System.out.println("===============获取分页相关的一些信息======================");

        System.out.println("总条数:" + page.getTotal());
        System.out.println("当前页码: "+ page.getCurrent());
        System.out.println("总页码:" + page.getPages());
        System.out.println("每页显示的条数:" + page.getSize());
        System.out.println("是否有上一页: " + page.hasPrevious());
        System.out.println("是否有下一页: " + page.hasNext());

        //将查询的结果封装到page对象中
        page.setRecords(employees);
    }

    /**
     * 代码生成 示例代码
     */
    @Test
    public void testGenerator () {
        // 1. 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setActiveRecord(true) // 是否支持 AR 模式
                    .setAuthor("xt") // 作者
                    .setOutputDir("D:\\ideaProjects\\mybatisplus-test\\src\\main\\java") // 生成路径
                    .setFileOverride(true)   // 文件覆盖
                    .setIdType(IdType.AUTO)  // 主键策略
                    .setServiceName("%sService")  // 设置生成的 service 接口的名字的首字母是否为 I, 如 IEmployeeService
                    .setBaseResultMap(true)
                    .setBaseColumnList(true);

        // 2. 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)   // 设置数据库类型
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setUrl("jdbc:mysql:///mp")
                .setUsername("root")
                .setPassword("root");

        // 3. 策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setCapitalMode(true)  // 全局大写命名
                      .setDbColumnUnderline(true)  // 指定表名 字段名是否使用下划线
                      .setNaming(NamingStrategy.underline_to_camel) // 数据库表映射到实体的命名策略
                      .setTablePrefix("tbl")  // 表前缀
                      .setInclude("tbl_employee");  // 生成的表

        // 4. 包名策略配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com.xt.mp")
                .setMapper("mapper")
                .setService("service")
                .setController("controller")
                .setEntity("bean")
                .setXml("mapper");

        // 5. 整合配置
        AutoGenerator autoGenerator = new AutoGenerator();
        autoGenerator.setGlobalConfig(globalConfig)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(packageConfig);

        // 6. 执行
        autoGenerator.execute();
    }

    /**
     * AR  分页复杂操作
     */
    @Test
    public void  testARPage() {
        Employee employee = new Employee();
        Page<Employee> employeePage = employee.selectPage(new Page<>(1, 2),
                new EntityWrapper<Employee>().like("last_name", "e"));
        employeePage.getRecords().forEach(System.out::println);
    }

    /**
     * AR 删除操作
     *
     * 注意: 删除不存在的数据 逻辑上也是属于成功的.
     */
    @Test
    public void testARDelete() {
        Employee employee = new Employee();
//        boolean result = employee.deleteById(20);

//        employee.setId(17);
//        boolean result = employee.deleteById();
//        System.out.println("result: " + result);

        // like 不区分大小写
        boolean result = employee.delete(new EntityWrapper().like("last_name", "tom"));
        System.out.println("result: " + result);

    }

    /**
     * AR 查询操作
     */
    @Test
    public void testARSelect () {
        Employee employee = new Employee();
//        Employee emp = employee.selectById(20);

        employee.setId(20);
        Employee emp = employee.selectById();
        System.out.println(emp);

        System.out.println("===========");

        List<Employee> employees = employee.selectAll();
        employees.forEach(System.out::println);

        System.out.println("===========");
        List<Employee> list = employee.selectList(new EntityWrapper().like("last_name", "e"));
        list.forEach(System.out::println);

        System.out.println("==========");

        int count = employee.selectCount(new EntityWrapper().eq("gender", 0));
        System.out.println("count: " + count);
    }

    /**
     * AR  修改操作
     */
    @Test
    public void testARUpdate () {
        Employee employee = new Employee();
        employee.setId(20);
        employee.setLastName("Sanae2");
        employee.setEmail("sanae2@163.com");
        employee.setGender("1");
        employee.setAge(30);
        boolean result = employee.updateById();
        System.out.println("result: " + result);
    }


    /**
     * AR  插入操作
     */
    @Test
    public void testARInsert () {
        Employee employee = new Employee();
        employee.setLastName("Sanae");
        employee.setEmail("sanae@163.com");
        employee.setGender("0");
        employee.setAge(30);
        boolean result = employee.insert();
        System.out.println("result: " + result);
    }

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
    public void testCommonDelete () {
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
    public void testCommonSelect () {

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
