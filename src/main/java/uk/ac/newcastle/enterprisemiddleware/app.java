package uk.ac.newcastle.enterprisemiddleware;

import org.h2.tools.Server;

import java.sql.SQLException;

public class app {
    private static Server server;
    public static void main(String[] args) {
            // Start H2 in server mode to allow remote connections (DBeaver)
        try {
            server = Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers", "-ifNotExists").start();
        } catch (SQLException e) {
            throw new RuntimeException("Could not start H2 server", e);
        }
    }
}
