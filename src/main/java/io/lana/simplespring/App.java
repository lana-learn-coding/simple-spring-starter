package io.lana.simplespring;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;

public class App {
    private static final String webappDir = "src/main/webapp/";
    private static final int port = 8080;

    public static void main(String[] args) throws LifecycleException {
        final var tomcat = new Tomcat();
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

        tomcat.start();
        System.out.println("Tomcat is listening on: " + port);
        tomcat.getServer().await();
    }
}

