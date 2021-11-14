package io.lana.simplespring;

import io.lana.simplespring.boot.EmbeddedTomcat;
import org.apache.catalina.LifecycleException;

public class App {
    public static void main(String[] args) throws LifecycleException {
        var tomcat = new EmbeddedTomcat(8080);

        tomcat.start();
        System.out.println("Tomcat is listening on: " + tomcat.getPort());
        tomcat.await();
    }
}

