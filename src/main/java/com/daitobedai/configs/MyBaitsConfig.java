package com.daitobedai.configs;


import com.alibaba.druid.pool.DruidDataSource;
import com.daitobedai.nocode.util.DruidCipherUtil;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableTransactionManagement
@ImportResource("classpath:/com/daitobedai/configs/aopConfig.xml")
public class MyBaitsConfig implements EnvironmentAware {
    private RelaxedPropertyResolver propertyResolver;

    private DruidDataSource druidDataSource;

    /**
     * mybatis mapper resource 路径
     */
    private static String[] MAPPER_PATH = { "classpath*:/com/daitobedai/nocode/dao/mapper/*.xml" };


    @Override
    public void setEnvironment(Environment environment) {
        this.propertyResolver = new RelaxedPropertyResolver(environment, "spring.datasource.");
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setConfiguration(configuration()); // 设置mybatis配置
        sqlSessionFactoryBean.setDataSource(getDruidDataSourceInstance()); // 设置数据源
        sqlSessionFactoryBean.setMapperLocations(mapperXml()); // 设置mapperxml
//        setTypeAliasesPackage(sqlSessionFactoryBean);
        return sqlSessionFactoryBean.getObject();
    }

    //生产环境不知道为什么无效
//    private void setTypeAliasesPackage(SqlSessionFactoryBean sqlSessionFactoryBean){
//        sqlSessionFactoryBean.setTypeAliasesPackage("com.daitobedai.nocode.domain");
//    }

    @Bean("transactionManager")
    public PlatformTransactionManager transactionManager() throws SQLException {
        return new DataSourceTransactionManager(getDruidDataSourceInstance());
    }

    // 注册dataSource
    @Bean(name = "dataSource", initMethod = "init", destroyMethod = "close")
    public DruidDataSource dataSource() throws SQLException {
        DruidCipherUtil druidCipherUtil = new DruidCipherUtil();
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(propertyResolver.getProperty("driver-class-name"));
        druidDataSource.setUrl(propertyResolver.getProperty("url"));
        druidDataSource.setUsername(propertyResolver.getProperty("username"));
        druidDataSource.setPassword(druidCipherUtil.decrypt(propertyResolver.getProperty("password")));
        druidDataSource.setInitialSize(Integer.parseInt(propertyResolver.getProperty("initialSize")));
        druidDataSource.setMinIdle(Integer.parseInt(propertyResolver.getProperty("minIdle")));
        druidDataSource.setMaxActive(Integer.parseInt(propertyResolver.getProperty("maxActive")));
        druidDataSource.setMaxWait(Integer.parseInt(propertyResolver.getProperty("maxWait")));
        druidDataSource.setTimeBetweenEvictionRunsMillis(Long.parseLong(propertyResolver.getProperty("timeBetweenEvictionRunsMillis")));
        druidDataSource.setMinEvictableIdleTimeMillis(Long.parseLong(propertyResolver.getProperty("minEvictableIdleTimeMillis")));
        druidDataSource.setValidationQuery(propertyResolver.getProperty("validationQuery"));
        druidDataSource.setTestWhileIdle(Boolean.parseBoolean(propertyResolver.getProperty("testWhileIdle")));
        druidDataSource.setTestOnBorrow(Boolean.parseBoolean(propertyResolver.getProperty("testOnBorrow")));
        druidDataSource.setTestOnReturn(Boolean.parseBoolean(propertyResolver.getProperty("testOnReturn")));
        druidDataSource.setPoolPreparedStatements(Boolean.parseBoolean(propertyResolver.getProperty("poolPreparedStatements")));
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(
                Integer.parseInt(propertyResolver.getProperty("maxPoolPreparedStatementPerConnectionSize")));
        return druidDataSource;
    }

    private synchronized DruidDataSource getDruidDataSourceInstance() throws SQLException {
        if (this.druidDataSource == null) {
            this.druidDataSource = dataSource();
        }
        return this.druidDataSource;
    }

    private org.apache.ibatis.session.Configuration configuration() {
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setCacheEnabled(false);
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        //        configuration.getTypeHandlerRegistry().register(Boolean.class, JdbcType.VARCHAR, new BooleanTypeHandler());
        configuration.getTypeHandlerRegistry().register(List.class, JdbcType.VARCHAR, new ListStringTypeHandler());
        // ...
        return configuration;
    }

    /**
     * mapper xml资源
     */
    private Resource[] mapperXml() throws IOException {
        PathMatchingResourcePatternResolver reolver = new PathMatchingResourcePatternResolver();
        List<Resource> list = new ArrayList();
        for (String path : MAPPER_PATH) {
            list.addAll(Arrays.asList(reolver.getResources(path)));
        }
        return list.toArray(new Resource[] {});
    }
}
