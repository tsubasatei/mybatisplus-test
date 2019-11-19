package com.mp.injector;

import com.baomidou.mybatisplus.entity.TableInfo;
import com.baomidou.mybatisplus.mapper.AutoSqlInjector;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;

/**
 * 自定义全局操作
 */
public class MySqlInjector extends AutoSqlInjector {
    /**
     * 扩展 inject 方法，完成自定义全局操作
     */
    @Override
    public void inject(Configuration configuration, MapperBuilderAssistant builderAssistant, Class<?> mapperClass, Class<?> modelClass, TableInfo table) {

        //将 EmployeeMapper 中定义的 deleteAll， 处理成对应的 MappedStatement 对象，加入到 configuration对象中。

        //注入的 SQL 语句
        String sql = "delete from " + table.getTableName();
        //注入的方法名   一定要与 EmployeeMapper 接口中的方法名一致
        String method = "deleteAll" ;

        //构造 SqlSource 对象
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);

        //构造一个删除的 MappedStatement
        this.addDeleteMappedStatement(mapperClass, method, sqlSource);
    }
}
