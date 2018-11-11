import Handlers.RootHandler;
import Handlers.SigninHandler;
import Handlers.SignupHandler;
import Handlers.TriviaHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class main {
    private final static String uri = "mongodb://127.0.0.1:27017";
    public static void main(String[] args) {
        int port = 80;
        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
            System.out.println("server started at " + server.getAddress());
            server.createContext("/trivias",new TriviaHandler(uri));
            server.createContext("/signin", new SigninHandler(uri));
            server.createContext("/signup", new SignupHandler(uri));
            server.createContext("/", new RootHandler(uri));
            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
