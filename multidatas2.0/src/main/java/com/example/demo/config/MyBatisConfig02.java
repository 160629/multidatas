package com.example.demo.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.mysql.cj.jdbc.MysqlXADataSource;
@Configuration
//basePackages 最好分开配置 如果放在同一个文件夹可能会报错
//跟之前都一样。扫包然后指定工厂
//没有配置事务管理器，相当于将本地事务注册到Atomikos全局事务
@MapperScan(basePackages = "com.example.demo.db02.mapper", sqlSessionTemplateRef = "sqlSessionTemplate02")
public class MyBatisConfig02 implements EnvironmentAware{
    private String xmlLocation;
    private String typeAliasesPackage;
    // 配置数据源
    
    @Bean(name = "dataSource02")
    public DataSource dataSource02(DBConfig02 testConfig) throws SQLException {
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setUrl(testConfig.getUrl());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXaDataSource.setPassword(testConfig.getPassword());
        mysqlXaDataSource.setUser(testConfig.getUsername());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
 
        
        //创建Atomikos全局事务，所有的事务注册进来
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXaDataSource);
        xaDataSource.setUniqueResourceName("dataSource02"); //dataSource02这个数据源的注册

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

    
    @Bean(name = "sqlSessionFactory02")
    public SqlSessionFactory sqlSessionFactory02(@Qualifier("dataSource02") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        bean.setMapperLocations(resolver.getResources(xmlLocation));
        if (!org.springframework.util.StringUtils.isEmpty(typeAliasesPackage)) {
            bean.setTypeAliasesPackage(typeAliasesPackage);
        }
        return bean.getObject();
    }

    
    @Bean(name = "sqlSessionTemplate02")
    public SqlSessionTemplate sqlSessionTemplate02(
            @Qualifier("sqlSessionFactory02") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

	@Override
	public void setEnvironment(Environment environment) {
		this.typeAliasesPackage = environment.getProperty("mybatis.typeAliasesPackage");
        this.xmlLocation =environment.getProperty("mybatis.xmlLocation"); 
        System.out.println(typeAliasesPackage  +"?????" + xmlLocation);
		
	}
}
