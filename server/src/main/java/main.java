import Handlers.RootHandler;
import Handlers.SignupHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class main {
    public static void main(String[] args) {
        int port = 80;
        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
            System.out.println("server started at " + port);
            server.createContext("/", new RootHandler());
            server.createContext("/signup", new SignupHandler());
            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
