package org.n52.project.enforce.cs1.api.impl;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * <p>
 * Application class.
 * </p>
 *
 * @author Benjamin Pross (b.pross@52north.org)
 * @since 1.0.0
 */
@SpringBootApplication
@EntityScan(basePackages = {
"org.n52.project.enforce.cs1.api.impl*"})
public class Application {

    /**
     * <p>
     * main.
     * </p>
     *
     * @param args
     *            an array of {@link java.lang.String} objects
     */
    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).run(args);
    }

}