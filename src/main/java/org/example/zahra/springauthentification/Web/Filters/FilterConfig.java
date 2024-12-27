package org.example.zahra.springauthentification.Web.Filters;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<ResponseFilter> responseFilter() {
        FilterRegistrationBean<ResponseFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ResponseFilter());
        registrationBean.addUrlPatterns("/api-docs/*", "/swagger-ui/*", "/swagger-ui.html");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}