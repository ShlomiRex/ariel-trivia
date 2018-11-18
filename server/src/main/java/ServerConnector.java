import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Connects to Java server that handle ariel-trivia mongodb requests
 */
public class ServerConnector {
    private HttpURLConnection connection;

    private enum HttpMethod {
        GET("GET"), POST("POST");
        public final String name;
        HttpMethod(String name) {
            this.name = name;
        }
    };

    public static void main(String[] args) {
        try {
            new ServerConnector("localhost", 80);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param url URL to connect to
     * @param method Method to use (GET, POST...)
     * @return
     * @throws IOException
     */
    private HttpURLConnection getDefaultConnection(URL url,  HttpMethod method) throws IOException {
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
     * @param @NotNull form_data
     * @param writer The body of Http POST packet
     * @throws IOException
     */
    private void writeBody(HashMap<String, List<String>> form_data, OutputStreamWriter writer) throws IOException {
        String buff = "";
        for(String key : form_data.keySet()) {
            List<String> vals = form_data.get(key);
            for(String val : vals) {
                buff += key+"="+val+"&";
            }
        }

        if(buff.length() >= 2) {
            String without_uppercent = buff.substring(0, buff.length()-1);
            writer.write("" +without_uppercent);
        }
    }


    public ServerConnector(String hostname, int port) throws IOException, InterruptedException {
        URL url = new URL("http://"+hostname+":"+port+"/?a=b&c=d");
        connection = getDefaultConnection(url, HttpMethod.POST);

        HashMap<String, List<String>> map = new HashMap<>();

        List<String> vals1 = new ArrayList<>();
        vals1.add("Value 1");
        vals1.add("Value 2");
        map.put("Key1", vals1);


        List<String> vals2 = new ArrayList<>();
        vals2.add("ABC");
        map.put("Key2", vals2);

        //Write to body (POST only)
        OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
        writeBody(map, osw);

        osw.flush();


        InputStreamReader isr = new InputStreamReader(connection.getInputStream(), "utf-8");
        BufferedReader reader = new BufferedReader(isr);

        String line;

        //TimeUnit.MILLISECONDS.sleep(1);

        while((line=reader.readLine()) != null) {
            System.out.println(line);
        }


    }


}
