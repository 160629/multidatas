package com.example.demo.config;

import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.github.pagehelper.PageHelper;
import com.mysql.cj.jdbc.MysqlXADataSource;

import tk.mybatis.spring.mapper.MapperScannerConfigurer;
@Configuration
//basePackages 最好分开配置 如果放在同一个文件夹可能会报错
//跟之前都一样。扫包然后指定工厂
//没有配置事务管理器，相当于将本地事务注册到Atomikos全局事务
@MapperScan(basePackages = "com.example.demo.db01.mapper", sqlSessionTemplateRef = "sqlSessionTemplate01")
public class MyBatisConfig01 implements EnvironmentAware{
    private String xmlLocation;
    private String typeAliasesPackage;
    
    
    
/*    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer01(Environment environment) {

        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory01");
        mapperScannerConfigurer.setBasePackage(typeAliasesPackage);
        return mapperScannerConfigurer;
    }*/
    
    // 配置数据源
    @Bean(name = "dataSource01")
    public DataSource dataSource01(DBConfig01 testConfig) throws SQLException {
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setUrl(testConfig.getUrl());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXaDataSource.setPassword(testConfig.getPassword());
        mysqlXaDataSource.setUser(testConfig.getUsername());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
 
        
        //创建Atomikos全局事务，所有的事务注册进来
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXaDataSource);
        xaDataSource.setUniqueResourceName("dataSource01"); //dataSource01这个数据源的注册

        xaDataSource.setMinPoolSize(testConfig.getMinPoolSize());
        xaDataSource.setMaxPoolSize(testConfig.getMaxPoolSize());
        xaDataSource.setMaxLifetime(testConfig.getMaxLifetime());
        xaDataSource.setBorrowConnectionTimeout(testConfig.getBorrowConnectionTimeout());
        xaDataSource.setLoginTimeout(testConfig.getLoginTimeout());
        xaDataSource.setMaintenanceInterval(testConfig.getMaintenanceInterval());
        xaDataSource.setMaxIdleTime(testConfig.getMaxIdleTime());
        xaDataSource.setTestQuery(testConfig.getTestQuery());
        return xaDataSource;
    }

    
    @Bean(name = "sqlSessionFactory01")
    public SqlSessionFactory sqlSessionFactory01(@Qualifier("dataSource01") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //分页插件
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");
        pageHelper.setProperties(properties);
        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Interceptor[] plugins = new Interceptor[]{pageHelper};
        bean.setPlugins(plugins);
        bean.setMapperLocations(resolver.getResources(xmlLocation));
        if (!org.springframework.util.StringUtils.isEmpty(typeAliasesPackage)) {
            bean.setTypeAliasesPackage(typeAliasesPackage);
        }
        return bean.getObject();
    }

    
    @Bean(name = "sqlSessionTemplate01")
    public SqlSessionTemplate sqlSessionTemplate01(
            @Qualifier("sqlSessionFactory01") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
    

	@Override
	public void setEnvironment(Environment environment) {
		this.typeAliasesPackage = environment.getProperty("mybatis.typeAliasesPackage");
        this.xmlLocation =environment.getProperty("mybatis.xmlLocation"); 
        System.out.println(typeAliasesPackage  +"?????" + xmlLocation);
		
	}
}
