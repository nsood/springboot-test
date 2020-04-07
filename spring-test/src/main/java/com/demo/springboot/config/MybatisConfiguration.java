package com.demo.springboot.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@MapperScan("com.demo.springboot.mapper")
public class MybatisConfiguration {
	
	@Value("${mybatis.type-aliases-package}")
	private String typeAliasesPackage;
	
//	@Value("${mybatis.mapper-locations}")
//	private String mapperLocations;
	
	@Autowired
	private DataSource dataSource;
	
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);
//		sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
		
		return sqlSessionFactoryBean.getObject();
	}
}
