package com.esprit.gdp.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SuppressWarnings("deprecation")
@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
      registry.addViewController("/{spring:\\w+}")
            .setViewName("forward:/");
      registry.addViewController("/**/{spring:\\w+}")
            .setViewName("forward:/");
      registry.addViewController("/{spring:\\w+}/**{spring:?!(\\.js|\\.css)$}")
            .setViewName("forward:/");
  }
  
//  @Bean
//  public ConfigurableServletWebServerFactory webServerFactory() {
//      TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
//      factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
//          @Override
//          public void customize(Connector connector) {
//              connector.setProperty("relaxedQueryChars", "?");
//          }
//      });
//      return factory;
//  }
}