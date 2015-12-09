package cz.muni.fi.pa165.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Vaculik on 09/12/2015.
 */

@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
@Configuration
@ComponentScan(basePackages = {"cz.muni.fi.pa165.controllers", "cz.muni.fi.pa165.hateoas"})
public class RestApiSpringConfig extends WebMvcConfigurerAdapter {
}
