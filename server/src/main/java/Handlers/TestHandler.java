package Handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class TestHandler implements HttpHandler {
    /**
     * Handle the given request and generate an appropriate response.
     * See {@link HttpExchange} for a description of the steps
     * involved in handling an exchange.
     *
     * @param exchange the exchange containing the request from the
     *                 client and used to send the response
     * @throws NullPointerException if exchange is <code>null</code>
     */
    @Override
    public void handle(HttpExchange he) throws IOException {
// parse request
        Map<String, Object> parameters = new HashMap<String, Object>();

        InputStreamReader isr =  new InputStreamReader(he.getRequestBody(),"utf-8");
        BufferedReader br = new BufferedReader(isr);
        String line;
        while((line=br.readLine()) != null)
            System.out.print(br.readLine());
        System.out.println();
        System.out.println(he.getRequestURI().getQuery());
        // send response
        String response = "";
        for (String key : parameters.keySet())
            response += key + " = " + parameters.get(key) + "\n";
        he.sendResponseHeaders(200, response.length());
        OutputStream os = he.getResponseBody();
        os.write(response.toString().getBytes());

        os.close();
    }
}

//Print headers
/*
        Headers headers = he.getRequestHeaders();
        Set<Map.Entry<String, List<String>>> entries = headers.entrySet();
        String response = "";
        for (Map.Entry<String, java.util.List<String>> entry : entries)
            response += entry.toString() + "\n";
        he.sendResponseHeaders(200, response.length());
        OutputStream os = he.getResponseBody();
        os.write(response.toString().getBytes());
        os.close();
 */
