package apirouterjava;

/**
 *
 * @author Kayode
 */

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RequestHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange he) {

        System.out.println("Serving the request");

        try {

            // Serve for POST requests only
            if (he.getRequestMethod().equalsIgnoreCase("POST")) {

                    System.out.println("RequestHandler:handle:POST:IN");

                try {

                    // REQUEST Headers
                    Headers requestHeaders = he.getRequestHeaders();
                    Set<Map.Entry<String, List<String>>> entries = requestHeaders.entrySet();

                    int contentLength = Integer.parseInt(requestHeaders.getFirst("Content-length"));

                    // REQUEST Body
                    InputStream is = he.getRequestBody();

                    byte[] data = new byte[contentLength];
                    int length = is.read(data);

                    System.out.println("RequestHandler:handle:data" + new String(data));

                    // RESPONSE Headers
                    Headers responseHeaders = he.getResponseHeaders();

                    // Send RESPONSE Headers
                    he.sendResponseHeaders(HttpURLConnection.HTTP_OK, contentLength);

                    String input = "SUCCESSFULL";

                    // RESPONSE Body
                    OutputStream os = he.getResponseBody();

                    os.write(input.getBytes());

                    he.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // Serve for POST requests only
            if (he.getRequestMethod().equalsIgnoreCase("GET")) {

                    System.out.println("RequestHandler:handle:GET:IN");

                try {

                    String input = "<p>SUCCESSFULL GET 678</p>";
                    byte[] data = input.getBytes();
                    
                    int contentLength = data.length;

                    // REQUEST Headers                    
                    he.getResponseHeaders().set("Content-Type", "text/html");
                    he.sendResponseHeaders(200, contentLength);

                    // RESPONSE Body
                    OutputStream os = he.getResponseBody();

                    os.write(data);
                    //os.write(data);

                    he.close();

                } catch (Exception e) {
                    
                    e.printStackTrace();
                }
            }

        } catch (Exception ex) {
            
            System.out.println("RequestHandler:handle:" + ex);
        }
    }
    
}
