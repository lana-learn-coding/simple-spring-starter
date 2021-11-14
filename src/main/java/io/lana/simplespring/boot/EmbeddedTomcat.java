package io.lana.simplespring.boot;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;

public class EmbeddedTomcat {
    private final Tomcat tomcat = new Tomcat();
    private final int port;

    public EmbeddedTomcat(int port) {
        this(port, "src/main/webapp/");
    }

    public EmbeddedTomcat(int port, String webappDir) {
        this.port = port;
        final var connector = new Connector("HTTP/1.1");
        connector.setPort(port);
        tomcat.setConnector(connector);

        // Declare an alternative location for your "WEB-INF/classes" dir
        // Servlet 3.0 annotation will work
        final var ctx = (StandardContext) tomcat.addWebapp("/", new File(webappDir).getAbsolutePath());

        // Resources => WEB-INF/classes in the war
        final var additionWebInfClasses = new File("target/classes");
        final var resources = new StandardRoot(ctx);
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes", additionWebInfClasses.getAbsolutePath(), "/"));
        ctx.setResources(resources);
    }

    public int getPort() {
        return port;
    }

    public void start() throws LifecycleException {
        tomcat.start();
    }

    public void await() {
        tomcat.getServer().await();
    }

    public void stop() throws LifecycleException {
        try {
            tomcat.stop();
        } finally {
            tomcat.destroy();
        }
    }
}
