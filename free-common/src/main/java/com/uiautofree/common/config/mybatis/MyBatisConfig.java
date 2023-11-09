package com.uiautofree.common.config.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@MapperScan(
        value = {
                "com.uiautofree.auth.dao",
                "com.uiautofree.common",
                "com.uiautofree.agent.dao"
        },
        sqlSessionFactoryRef = "freeSqlSessionFactory",
        sqlSessionTemplateRef = "freeSqlSessionTemplate"
)
public class MyBatisConfig {
    @Primary
    @Bean(name = "freeSqlSessionFactory")
    public SqlSessionFactory freeSqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(getMapperLocations());
        return sqlSessionFactoryBean.getObject();
    }


    @Primary
    @Bean(name = "freeSqlSessionTemplate")
    public SqlSessionTemplate freeSqlSessionTemplate(@Qualifier("freeSqlSessionFactory") SqlSessionFactory freeSqlSessionFactory) {
        return new SqlSessionTemplate(freeSqlSessionFactory);
    }

    private Resource[] getMapperLocations() {
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        List<String> mapperLocations = new ArrayList<>();
        mapperLocations.add("classpath*:/mapper/**/*.xml");
        List<Resource> resources = new ArrayList<>();
        if (mapperLocations != null) {
            for (String mapperLocation : mapperLocations) {
                try {
                    Resource[] mapper = resourcePatternResolver.getResources(mapperLocation);
                    resources.addAll(Arrays.asList(mapper));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return resources.toArray(new Resource[resources.size()]);
    }
}