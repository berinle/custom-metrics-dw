package com.example;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

/**
 * @author berinle
 */
public class AppService extends Service<AppConfiguration> {
    @Override
    public void initialize(Bootstrap<AppConfiguration> bootstrap) {
    }

    @Override
    public void run(AppConfiguration configuration, Environment environment) throws Exception {
        final AppResource appResource = new AppResource(new BarService());
        environment.addResource(appResource);
    }

    /**
     * public static void main(String[] args) throws Exception {
     new HelloWorldApplication().run(args);
     }
     */

    public static void main(String[] args) throws Exception {
        new AppService().run(args);
    }
}
