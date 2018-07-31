package com.zm.springsrcurity.config;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

/**
 * @Describle This Class Is SpringSecurity配置类
 * @Author ZengMin
 * @Date 2018/7/30 20:47
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
        //定制授权规则
        http.authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/level1/**").hasRole("L1")
                .antMatchers("/level2/**").hasRole("L2")
                .antMatchers("/level3/**").hasRole("L3");
        //开启登录  自动跳转到登录页面/login 他帮我们生成的！
        http.formLogin().loginPage("/userlogin")
        .usernameParameter("name").passwordParameter("pwd");
        //登录失败会跳转/login?error
        http.logout().logoutSuccessUrl("/");  //开启自动配置注销的功能

        //开启记住我  保存cookie
        http.rememberMe().rememberMeParameter("rember");

    }

/*
   SPring5.0新特性 加密密码  这里配置一个NoOpPasswordEncoder为了让不加密的密码可以被识别
 */
    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

    /**
     * @param auth
     * @throws Exception
     * 生成
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        super.configure(auth);
        //放虚拟用户到内存中  实际应该是从数据库查询
        auth.inMemoryAuthentication()
                .withUser("zm").password("123").roles("L1")
                .and()
                .withUser("zm1").password("123").roles("L3","L2","L1");
    }

}
