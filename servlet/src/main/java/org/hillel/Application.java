package org.hillel;

import org.hillel.filter.CharsetEncodingFilter;
import org.hillel.servlet.DownloadServlet;
import org.hillel.servlet.WelcomeServlet;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.nio.charset.StandardCharsets;

public class Application implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        ServletRegistration.Dynamic welcomeServlet = servletContext.addServlet("welcomeServlet", WelcomeServlet.class);
        welcomeServlet.addMapping("/welcome");
        ServletRegistration.Dynamic downloadServlet = servletContext.addServlet("downloadServlet", DownloadServlet.class);
        downloadServlet.addMapping("/download/*");
        FilterRegistration.Dynamic charsetFilter = servletContext.addFilter("charsetFilter", new CharacterEncodingFilter(StandardCharsets.UTF_8.displayName()));
        charsetFilter.addMappingForUrlPatterns(null,true,"/*");
    }
}
