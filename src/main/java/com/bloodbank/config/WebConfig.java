package com.bloodbank.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Serves the static HTML frontend that lives in src/main/resources/static/.
 *
 * Spring Boot auto-configures a default resource handler for classpath:/static/
 * already, but we keep this class to explicitly forward "/" → index.html
 * without conflicting with /api/** routes.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Serve static assets (JS, CSS, images, etc.) only from specific paths
     * so that /api/** requests are NOT intercepted by the resource handler.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Static files served explicitly — does NOT shadow /api/** routes
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        // Serve root-level assets like favicon.ico, manifest.json etc.
        registry.addResourceHandler("/*.ico", "/*.png", "/*.svg", "/*.webmanifest")
                .addResourceLocations("classpath:/static/");
    }

    /**
     * Forward the root path to index.html so the browser loads the SPA.
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index.html");
    }
}
