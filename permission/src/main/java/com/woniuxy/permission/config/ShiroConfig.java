package com.woniuxy.permission.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.woniuxy.permission.component.CustomerRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.swing.plaf.metal.MetalFileChooserUI;
import java.util.LinkedHashMap;

@Configuration
public class ShiroConfig {
    @Bean
    public Realm realm(){
        CustomerRealm customerRealm = new CustomerRealm();
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(2048);
        customerRealm.setCredentialsMatcher(matcher);
        return customerRealm;
    }
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm());
        //配置记住我
        securityManager.setRememberMeManager(cookieRememberMeManager());
        return securityManager;
    }
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(){
        System.out.println("进入过滤器");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager());
        //如果使用了rememberme，过滤器必须使用user
        LinkedHashMap<String,String> filterChainDefinitionMap=new LinkedHashMap<>();
        //设置白名单
        filterChainDefinitionMap.put("/**","anon");
        filterChainDefinitionMap.put("/js/**","anon");
        filterChainDefinitionMap.put("/css/**","anon");
        filterChainDefinitionMap.put("/user/login","anon");
        filterChainDefinitionMap.put("/user/register","anon");
        filterChainDefinitionMap.put("/login.html","anon");
        filterChainDefinitionMap.put("/register.html","anon");
        filterChainDefinitionMap.put("/user/logout","logout");
        //设置黑名单
        filterChainDefinitionMap.put("/**","user");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        shiroFilterFactoryBean.setLoginUrl("/login.html");
        return shiroFilterFactoryBean;
    }
    //配置记住我
    @Bean
   public CookieRememberMeManager cookieRememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        //创建cookie
        SimpleCookie rememberMe = new SimpleCookie("rememberMe");
        rememberMe.setMaxAge(7*24*60*60);
        cookieRememberMeManager.setCookie(rememberMe);
        cookieRememberMeManager.setCipherKey(Base64.decode("a1b2c3d4e5f6h7j8k9l10m11=="));
        return cookieRememberMeManager;
   }
   @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
   }


}
