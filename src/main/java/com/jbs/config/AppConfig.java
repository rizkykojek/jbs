package com.jbs.config;

import com.google.common.collect.Sets;
import com.jbs.mapper.EventSkipDestinationMap;
import com.jbs.mapper.PerformanceSkipDestinationMap;
import com.jbs.util.ODataUtil;
import com.jbs.util.OpenCmisUtil;
import com.sap.ecm.api.EcmService;
import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.web.servlet.support.csrf.CsrfRequestDataValueProcessor;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.support.RequestDataValueProcessor;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import javax.naming.NamingException;
import java.io.IOException;
import java.util.Locale;

/**
 * Created by rizkykojek on 3/19/17.
 */
@Configuration
@EnableWebMvc
@EnableScheduling
@ComponentScan(basePackages = "com.jbs")
public class AppConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public Edm edm() throws IOException, ODataException {
        return ODataUtil.initEdm();
    }

    @Bean
    public EcmService ecmService() throws NamingException {
        return OpenCmisUtil.initEcmService();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new EventSkipDestinationMap());
        modelMapper.addMappings(new PerformanceSkipDestinationMap());
        return modelMapper;
    }

    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        return validator;
    }

    @Bean
    public RequestDataValueProcessor requestDataValueProcessor() {
        return new CsrfRequestDataValueProcessor();
    }

    @Bean
    public MultipartResolver multipartResolver(){
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        //multipartResolver.setMaxUploadSizePerFile(ApplicationUtil.MAX_UPLOAD_SIZE_PER_FILE);
        return  multipartResolver;
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("/resources/default_theme/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("/resources/default_theme/js/");
        registry.addResourceHandler("/images/**").addResourceLocations("/resources/default_theme/images/");
    }

    //start internationalization
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("/resources/i18/language");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver(){
        SessionLocaleResolver resolver = new SessionLocaleResolver ();
        resolver.setDefaultLocale(new Locale("en"));
        return resolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        registry.addInterceptor(interceptor);
    }
    //end internationalization

    //start Thymeleaf specific configuration
    @Bean
    public ViewResolver viewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        resolver.setCharacterEncoding("UTF-8");
        return resolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setEnableSpringELCompiler(true);
        engine.setTemplateResolver(templateResolver());
        engine.setAdditionalDialects(Sets.newHashSet(new SpringSecurityDialect()));
        return engine;
    }

    private ITemplateResolver templateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setApplicationContext(applicationContext);
        resolver.setPrefix("/WEB-INF/templates/");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setSuffix(".html");
        resolver.setCacheable(false); //uncomment this if you want to use SpringDEvTool reload feature
        return resolver;
    }
    //end Thymeleaf specific configuration

}
