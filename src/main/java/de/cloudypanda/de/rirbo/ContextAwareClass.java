package de.cloudypanda.de.rirbo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ContextAwareClass implements ApplicationContextAware {

    private static ApplicationContext ctx;

    public static ApplicationContext getApplicationContext() {
        return ctx;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        ctx = applicationContext;
    }
}
