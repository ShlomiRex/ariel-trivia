package Handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyMongoHandler {
    /**
     * Parses the query of request BODY. Not parameters.
     * @param he
     * @return
     * @throws IOException
     */
    public static Map<String, Object> parseBodyQuery(HttpExchange he) throws IOException {
        Map<String, Object> parameters = new HashMap<String, Object>();
        InputStreamReader isr = new InputStreamReader(he.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String query = br.readLine();

        if (query != null) {
            String pairs[] = query.split("[&]");
            for (String pair : pairs) {
                String param[] = pair.split("[=]");
                String key = null;
                String value = null;
                if (param.length > 0) {
                    key = URLDecoder.decode(param[0],
                            System.getProperty("file.encoding"));
                }

                if (param.length > 1) {
                    value = URLDecoder.decode(param[1],
                            System.getProperty("file.encoding"));
                }

                if (parameters.containsKey(key)) {
                    Object obj = parameters.get(key);
                    if (obj instanceof List<?>) {
                        List<String> values = (List<String>) obj;
                        values.add(value);

                    } else if (obj instanceof String) {
                        List<String> values = new ArrayList<String>();
                        values.add((String) obj);
                        values.add(value);
                        parameters.put(key, values);
                    }
                } else {
                    parameters.put(key, value);
                }
            }
        }
        return parameters;
    }

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
        he.sendResponseHeaders(responseCode, len);
        OutputStream os = he.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    /**
     *
     * @param query Query inside URI (e.g. a=b&c=d)
     * @return
     */
    public static Map<String, String> queryToMap(String query) {
        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) {
                result.put(entry[0], entry[1]);
            }else{
                result.put(entry[0], "");
            }
        }
        return result;
    }
}
