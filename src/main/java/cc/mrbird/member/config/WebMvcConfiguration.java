package cc.mrbird.member.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * WebMVC 配置.
 * <p>
 * 添加路径和页面的映射关系
 *
 * @author Mengday Zhang
 * @version 1.0
 * @since 2018/6/13
 */
//@Configuration
//public class WebMvcConfiguration extends WebMvcConfigurationSupport {
    public class WebMvcConfiguration  {
    //@Override
    protected void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/gotoWapPage").setViewName("gotoWapPay");
//        registry.addViewController("/gotoPagePage").setViewName("gotoPagePay");
//        registry.addViewController("/gotoH5Page").setViewName("gotoH5Page");
//        registry.addViewController("/h5PaySuccess").setViewName("h5PaySuccess");
//        registry.addViewController("/wxPayPc").setViewName("wxPayPc");
       // super.addViewControllers(registry);
    }
}
