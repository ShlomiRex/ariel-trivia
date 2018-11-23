package HttpHandlers;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public abstract class Response {
    /**
     * Sends response with response message and status code.
     * @param he Musn't be null.
     * @param response Can be null.
     * @param responseCode
     * @throws IOException
     */
    public static void sendResponse(HttpExchange he, String response, int responseCode) throws IOException {
        int len;
        if(response != null) {
            len = response.length();
        } else {
            len = 0;
            response = "";
        }
        System.out.println("Sending Response: (\n\n" + response + "\n\n)\n\nStatus code: (" + responseCode + ")\nEnd response.");
        he.sendResponseHeaders(responseCode, len);
        OutputStream os = he.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
