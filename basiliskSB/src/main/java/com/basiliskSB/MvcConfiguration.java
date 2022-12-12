package com.basiliskSB;
import java.util.Locale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {
	@Bean
	public LocaleResolver localeResolver() {
	    SessionLocaleResolver session = new SessionLocaleResolver();
	    Locale indonesia = new Locale("id", "ID");
	    session.setDefaultLocale(indonesia);
	    return session;
	}
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/category/index");
        registry.addViewController("/category").setViewName("forward:/category/index");
        registry.addViewController("/supplier").setViewName("forward:/supplier/index");
        registry.addViewController("/product").setViewName("forward:/product/index");
        registry.addViewController("/region").setViewName("forward:/region/index");
        registry.addViewController("/customer").setViewName("forward:/customer/index");
        registry.addViewController("/delivery").setViewName("forward:/delivery/index");
        registry.addViewController("/salesman").setViewName("forward:/salesman/index");
        registry.addViewController("/order").setViewName("forward:/order/index");
    }
}
