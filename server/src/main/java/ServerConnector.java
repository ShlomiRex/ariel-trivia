import HttpHandlers.APICode;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Connects to Java server that handle ariel-trivia mongodb requests
 */
public class ServerConnector {
    private enum HttpMethod {
        GET("GET"), POST("POST");
        public final String name;
        HttpMethod(String name) {
            this.name = name;
        }
    };

    /**
     *
     * @param url URL to connect to
     * @param method Method to use (GET, POST...)
     * @return
     * @throws IOException
     */
    private static HttpURLConnection getDefaultConnection(URL url,  HttpMethod method) throws IOException {
        HttpURLConnection connection;
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method.name);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setUseCaches(false);
        if(method == HttpMethod.GET) {
            connection.setDoInput(true);
            connection.setDoOutput(false);
        } else if(method == HttpMethod.POST) {
            connection.setDoInput(true);
            connection.setDoOutput(true);
        } else {
            throw new IllegalArgumentException("Must be GET or POST.");
        }
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);


        return connection;
    }


    /**
     * Writes form data to outputstream. Does <b>NOT</b> flush.
     * @param form_data
     * @param writer The body of Http POST packet
     * @throws IOException
     */
    private static void writeBody(Map<String, List<String>> form_data, OutputStreamWriter writer) throws IOException {
        if(form_data == null || writer == null)
                return;
        String buff = "";
        for(String key : form_data.keySet()) {
            List<String> vals = form_data.get(key);
            for(String val : vals) {
                buff += key+"="+val+"&";
            }
        }

        if(buff.length() >= 2) {
            //Mote: Substring is EXLUCISVE at endIndex.
            String without_uppercent = buff.substring(0, buff.length()-1);
            writer.write("" +without_uppercent);
        }
    }


    /**
     *
     * @param hostname The hostname (Usually localhost)
     * @param port The port (Usually 80)
     * @param param_data The param data (basically the URI after route)
     * @param form_data The form data (basically body of POST)
     * @param out Writes the response to this
     * @return Status code that depends on the request
     * @throws IOException Error io in networking
     */
    public static int POST(String hostname, int port, String route, Map<String, List<String>> param_data, Map<String, List<String>> form_data, OutputStream out, APICode api_code) throws IOException {
        String urlbuf = "http://"+hostname+":"+port+"/"+route+"?api_code="+api_code.getApicode();
        if(param_data != null) {
            for (String param_key : param_data.keySet()) {
                List<String> param_key_values = param_data.get(param_key);
                for (String v : param_key_values) {
                    urlbuf += "&" + param_key + "=" + v;
                }
            }
        }

        URL url = new URL(urlbuf);
        HttpURLConnection connection = getDefaultConnection(url, HttpMethod.POST);

        //Write to body (POST only)
        OutputStream os = connection.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os);
        writeBody(form_data, osw);

        osw.flush();


        //READ
        InputStreamReader isr = new InputStreamReader(connection.getInputStream(), "utf-8");
        BufferedReader reader = new BufferedReader(isr);
        OutputStreamWriter a = new OutputStreamWriter(out);
        String line;
        List<String> lines = new ArrayList<>();
        while((line=reader.readLine()) != null) {
            lines.add(line);
        }

        for(int i = 0; i < lines.size() - 1; i++) {
            a.write(lines.get(i));
            a.write('\n');
        }

        a.write(lines.get(lines.size()-1));



        a.flush();
        int code = connection.getResponseCode();

        connection.disconnect();

        return code;
    }

    /**
     *
     * @param hostname The hostname (Usually localhost)
     * @param port The port (Usually 80)
     * @param param_data The param data (basically the URI after route)
     * @param out Writes the response to this
     * @return Status code that depends on the request
     * @throws IOException Error io in networking
     */
    public static int GET(String hostname, int port, String route, Map<String, List<String>> param_data, OutputStream out, APICode api_code) throws IOException {
        String urlbuf = "http://"+hostname+":"+port+"/"+route+"?api_code="+api_code.getApicode();
        for(String param_key : param_data.keySet()) {
            List<String> param_key_values = param_data.get(param_key);
            for (String v : param_key_values) {
                urlbuf += "&" + param_key + "=" + v;
            }
        }

        URL url = new URL(urlbuf);
        HttpURLConnection connection = getDefaultConnection(url, HttpMethod.GET);

        //READ
        InputStreamReader isr = new InputStreamReader(connection.getInputStream(), "utf-8");
        BufferedReader reader = new BufferedReader(isr);
        OutputStreamWriter a = new OutputStreamWriter(out);
        String line;
        List<String> lines = new ArrayList<>();
        while((line=reader.readLine()) != null) {
            lines.add(line);
        }

        for(int i = 0; i < lines.size() - 1; i++) {
            a.write(lines.get(i));
            a.write('\n');
        }

        a.write(lines.get(lines.size()-1));



        a.flush();
        int code = connection.getResponseCode();
        return code;
    }

}
