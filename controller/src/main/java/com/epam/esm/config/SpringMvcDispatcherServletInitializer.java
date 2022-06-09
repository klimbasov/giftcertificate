package com.epam.esm.config;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class SpringMvcDispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    private static final String PROFILE_KEY = "spring.profiles.active";
    private static final String DEFAULT_PROFILE = "default";

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{ControllerConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected DispatcherServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
        DispatcherServlet ds = new DispatcherServlet(servletAppContext);
        ds.setThrowExceptionIfNoHandlerFound(true);
        return ds;
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        setProfile(servletContext);
        super.onStartup(servletContext);
    }

    private void setProfile(ServletContext servletContext) {
        servletContext.setInitParameter(PROFILE_KEY, loadProfile(servletContext));
    }

    private String loadProfile(ServletContext servletContext) {
        String profileName = DEFAULT_PROFILE;
        String path = servletContext.getRealPath("/WEB-INF/classes/application.properties");
        try (FileReader reader = new FileReader(path)) {
            Properties properties = new Properties();
            properties.load(reader);
            profileName = properties.getProperty(PROFILE_KEY);
        } catch (IOException ignored){}
        return profileName;
    }
}
