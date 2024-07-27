package org.codexist.codexistcasestudy;

import org.codexist.restcontrollers.MapController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = MapController.class)
public class CodexistCaseStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodexistCaseStudyApplication.class, args);
    }

}
