package com.porcelani;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.File;
import java.net.InetSocketAddress;

public class Main {

    private static Server server;

    public static void main(String[] args) throws Exception {
        startServer();
        server.join();
    }

    public static void stoptServer() throws Exception {
        server.stop();
    }

    public static void startServer() throws Exception {
        String webappDirLocation = "src/main/webapp/";

        String path = new File(".").getCanonicalPath();
        System.setProperty("log4j.configuration", "file:///" + path + "/" + webappDirLocation + "WEB-INF/log4j.properties");

        InetSocketAddress localhost = new InetSocketAddress("localhost", 8080);
        server = new Server(localhost);
        WebAppContext root = new WebAppContext();

        root.setContextPath("/");
        root.setDescriptor(webappDirLocation + "/WEB-INF/web.xml");
        root.setResourceBase(webappDirLocation);

        root.setParentLoaderPriority(true);

        server.setHandler(root);
        server.start();

        System.out.println("########################################################");
        System.out.println(server.getURI());
        System.out.println("########################################################");
    }


}
