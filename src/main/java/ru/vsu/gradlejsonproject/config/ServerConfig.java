package ru.vsu.gradlejsonproject.config;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.vsu.gradlejsonproject.controller.PlayerController;

public class ServerConfig {
    private final static PlayerController playerController = new PlayerController();

    private static Server build(){
        final Server server = new Server();
        final int port = 8080;
        final HttpConfiguration httpConfig = new HttpConfiguration();
        final HttpConnectionFactory httpConnectionFactory = new HttpConnectionFactory(httpConfig);
        final ServerConnector serverConnector = new ServerConnector(server, httpConnectionFactory);
        serverConnector.setHost("localhost");
        serverConnector.setPort(port);
        server.setConnectors(new Connector[]{serverConnector});
        return server;
    }

    public static void initServer() throws Exception {
        final Server server = build();
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        context.setContextPath("/");
        context.addServlet(new ServletHolder("players", playerController), "/players/*");
        server.setHandler(context);
        server.start();
    }
}
