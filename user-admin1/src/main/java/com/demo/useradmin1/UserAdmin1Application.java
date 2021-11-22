package com.demo.useradmin1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import jdk.javadoc.doclet.Doclet;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication
public class UserAdmin1Application {

  public static void main(String[] args) {
    SpringApplication.run(UserAdmin1Application.class, args);
  }
}
