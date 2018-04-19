package com.spring_security.config;

import net.sf.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.domain.*;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.AclCache;
import org.springframework.security.acls.model.PermissionGrantingStrategy;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.sql.DataSource;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration{

    @Autowired
    DataSource dataSource;


    @Bean
    public JdbcMutableAclService aclService(){
        JdbcMutableAclService jdbcMutableAclService=
                new JdbcMutableAclService(dataSource,lookUpStrategy(),aclCache());
        jdbcMutableAclService.setClassIdentityQuery("SELECT @@IDENTITY");
        jdbcMutableAclService.setSidIdentityQuery("SELECT @@IDENTITY");
        return jdbcMutableAclService;
    }

    @Bean
    public LookupStrategy lookUpStrategy() {
        return new BasicLookupStrategy(
                dataSource,
                aclCache(),
                aclAuthorizationStrategy(),
                permissionGrantingStrategy());
    }

    @Bean
    public AclCache aclCache() {
        EhCacheFactoryBean factoryBean = new EhCacheFactoryBean();
        EhCacheManagerFactoryBean cacheManager = new EhCacheManagerFactoryBean();
        cacheManager.setAcceptExisting(true);
        cacheManager.setCacheManagerName(CacheManager.getInstance().getName());
        cacheManager.afterPropertiesSet();
        factoryBean.setName("aclCache");
        factoryBean.setCacheManager(cacheManager.getObject());
        factoryBean.setMaxBytesLocalHeap("16M");
        factoryBean.setMaxEntriesLocalHeap(0L);
        factoryBean.afterPropertiesSet();
        return new EhCacheBasedAclCache(
                factoryBean.getObject(),
                permissionGrantingStrategy(),
                aclAuthorizationStrategy());
    }

    @Bean
    AclAuthorizationStrategy aclAuthorizationStrategy(){
        return new AclAuthorizationStrategyImpl(new SimpleGrantedAuthority("Admin"));
    }

    PermissionGrantingStrategy permissionGrantingStrategy(){
        return new DefaultPermissionGrantingStrategy(new ConsoleAuditLogger());
    }

    @Bean
    public AclPermissionEvaluator aclPermissionEvaluator() {
        AclPermissionEvaluator aclPermissionEvaluator =
                new AclPermissionEvaluator(aclService());
        return aclPermissionEvaluator;
    }

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        DefaultMethodSecurityExpressionHandler expressionHandler =
                new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(aclPermissionEvaluator());
        return expressionHandler;
    }

}
