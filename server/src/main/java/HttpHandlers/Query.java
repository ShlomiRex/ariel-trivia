package HttpHandlers;

import java.io.*;
import java.net.URLDecoder;
import java.util.*;

public abstract class Query {
    /**
     * Parses the query of request BODY. Not parameters.
     * @param reqBody - HttpExchange.getRequestBody()
     * @return
     * @throws IOException
     */
    public static Map<String, List<String>> parseBodyQuery(InputStream reqBody) throws IOException {
        Map<String, Object> parameters = new HashMap<>();
        InputStreamReader isr = new InputStreamReader(reqBody, "utf-8");
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
        Map<String, List<String>> result = new HashMap<>();
        for(String key : parameters.keySet()) {
            Object o = parameters.get(key);
            if(o instanceof List<?>) {
                result.put(key, (List<String>) o);
            } else if(o instanceof String) {
                List<String> lst = new ArrayList<>();
                lst.add((String) o);
                result.put(key, lst);
            }
        }
        return result;
    }


    /**
     * @param query HttpHandlers.Query inside URI (e.g. a=b&c=d)
     * @return
     */
    public static Map<String, List<String>> parseParametersQuery(String query) {
        Map<String, List<String>> result = new HashMap<>();
        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) {
                if (result.containsKey(entry[0])) {
                    List<String> val = result.get(entry[0]);
                    val.add(entry[1]);
                } else {
                    ArrayList<String> arr = new ArrayList<>(); //on heap
                    arr.add(entry[1]);
                    result.put(entry[0], arr);
                }
            }
        }
        return result;
    }
}