package org.example.spring_aop;

import org.example.spring_aop.service.*;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AopConfig {
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        return new DefaultAdvisorAutoProxyCreator();
    }

    @Bean
    public AspectJExpressionPointcut aspectJExpressionPointcut() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* com.example.spring_aop.service..*.*(..))");

        return pointcut;
    }

    @Bean
    public PerformanceMonitorAdvice performanceMonitorAdvice() {
        return new PerformanceMonitorAdvice();
    }

    @Bean
    public Advisor performanceMonitorAdvisor() {
        return new DefaultPointcutAdvisor(aspectJExpressionPointcut(), performanceMonitorAdvice());
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl();
    }

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl();
    }

    @Bean
    public ProductService productService() {
        return new ProductServiceImpl();
    }

}
