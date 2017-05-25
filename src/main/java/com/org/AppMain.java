package com.org;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StopWatch;

import com.org.service.AppService;

public class AppMain {

    final Logger logger = LoggerFactory.getLogger(AppMain.class);

    public static void main(String args[]) {

        AppMain main = new AppMain();
        main.process();

    }

    public void process() {

        logger.info("Starting EasyEmailApplication");

        ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("config/spring/application-config.xml");
        try {
            AppService service = ctx.getBean(AppService.class);

            StopWatch watch = new StopWatch();
            watch.setKeepTaskList(true);
            watch.start("EasyEmailService");
            logger.info("Starting EasyEmailService");
            service.startService();

            logger.info("EasyEmailService ended");

            watch.stop();

            logger.info(watch.prettyPrint());

        } finally {
            ctx.stop();
            ctx.close();
        }
    }
}