package com.app.demo.config;

import java.io.IOException;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;

@Configuration
@MapperScan("com.app.demo.dao")
public class DataConfig {

    /**
     * SqlSessionFactoryBean格納クラス
     * <PRE>
     * mybatisの設定情報をSqlSessionFactoryBeanにセット
     * DataSourceをSqlSessionFactoryBeanにセット
     * </PRE>
     *
     * @param dataSource
     * @return SqlSessionFactoryBean
     * @throws IOException
     * @author y_ha
     */
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) throws IOException {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        ResourcePatternResolver resolver = ResourcePatternUtils.getResourcePatternResolver(new DefaultResourceLoader());
        factory.setConfigLocation(resolver.getResource("classpath:mybatis-config.xml"));
        factory.setMapperLocations(resolver.getResources("classpath:sql/**/*.xml"));
        return factory;
    }
}