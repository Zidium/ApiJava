package zidium.http;

import java.net.*;
import java.io.*;
import java.util.Map;


public class ZHttpClient {   
    
    private int _connectTimeout = 5000;
    private int _readTimeout = 10 * 1000;
    private String _responseEncoding = "UTF-8";
    private String _requestEncoding = "UTF-8";
    
    public void setConnectionTimeout(int timeout){
        _connectTimeout = timeout;
    }
    
    public void setReadTimeout(int timeout){
        _readTimeout = timeout;
    }
    
    private HttpURLConnection createConnection(String url) throws MalformedURLException, IOException{
        URL urlObj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
        con.setConnectTimeout(_connectTimeout);
        con.setReadTimeout(_readTimeout);
        return con;
    }
    
    private byte[] getResponseBytes(HttpURLConnection connection) throws IOException{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream is = null;
        try {
          is = connection.getInputStream();
          byte[] byteChunk = new byte[4096]; // Or whatever size you want to read in at a time.
          int n;
          while ( (n = is.read(byteChunk)) > 0 ) {
            baos.write(byteChunk, 0, n);
          }
        }
//        catch (IOException e) {
//          System.err.printf ("Failed while reading bytes from %s: %s", url.toExternalForm(), e.getMessage());
//          e.printStackTrace ();
//          // Perform any other exception handling that's appropriate.
//        }
        finally {
          if (is != null) { is.close(); }
        }
        return baos.toByteArray();
    } 
    
    private String getResponseString(HttpURLConnection connection) throws IOException{
        InputStream in = connection.getInputStream();
        
        InputStreamReader reader = (_responseEncoding==null)
                ? new InputStreamReader(in)
                : new InputStreamReader(in, _responseEncoding);
        
        BufferedReader bufferedReader = new BufferedReader(reader);
        String inputLine;
        String text = "";
        while ((inputLine = bufferedReader.readLine()) != null) 
            text += inputLine;
        in.close();
        return text;
    }
    
    public ZHttpResponse ExecuteRequest(ZHttpRequest request) throws Exception{
        
        // отправляем запрос
        HttpURLConnection connection = createConnection(request.Url);
        connection.setConnectTimeout(1000 * 5);
        connection.setRequestMethod(request.Method);
        for (ZHttpRequestProperty property : request.getProperties()) {
            connection.setRequestProperty(property.Name, property.Value);
        }
        if (request.Data!=null){
            connection.setDoOutput(true);
            OutputStream out = connection.getOutputStream();           
            out.write(request.Data);           
            out.flush();
            out.close();
        }
       
        // получаем ответ 
        ZHttpResponse response = new ZHttpResponse();
        response.Code = connection.getResponseCode();
        InputStream in = connection.getInputStream();
        ByteArrayOutputStream responseBytes = new ByteArrayOutputStream();
        byte[] bufer = new byte[4096];
        int readed = -1;
        while ((readed = in.read(bufer))>0) {            
            responseBytes.write(bufer, 0, readed);
        }
        response.Bytes = responseBytes.toByteArray();
        return response;
    }
    
    public byte[] getBytes(String url) throws MalformedURLException, ProtocolException, IOException{
        HttpURLConnection conection = createConnection(url);
        conection.setRequestMethod("GET");
        return getResponseBytes(conection);
    }
    
    public String getString(String url) throws IOException{
        HttpURLConnection conection = createConnection(url);
        conection.setRequestMethod("GET");
        return getResponseString(conection);
    }
    
    public byte[] postBytes(String url, byte[] data) throws MalformedURLException, ProtocolException, IOException{
        HttpURLConnection conection = createConnection(url);
        conection.setRequestMethod("POST");
        conection.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(conection.getOutputStream());
        out.write(data);
        //out.writeBytes(url);
        out.flush();
        out.close();
        return getResponseBytes(conection);
    }
    
    public byte[] postBytes(String url, String text) throws MalformedURLException, ProtocolException, IOException{
       byte[] bytes = text.getBytes(_requestEncoding);
       return postBytes(url, bytes);
    }
    
    public byte[] postBytes(String url, Map<String, String> parameters) throws MalformedURLException, ProtocolException, IOException{
       String query = getUrlParameters(parameters);
       return postBytes(url, query);
    }
    
    public String postString(String url, String text) throws MalformedURLException, ProtocolException, IOException{
       byte[] bytes = text.getBytes(_requestEncoding);
       byte[] result = postBytes(url, bytes);
       return new String(result, _requestEncoding);
    }
    
    public static String getUrlParameters(Map<String, String> params) 
      throws UnsupportedEncodingException{
        StringBuilder result = new StringBuilder();
 
        for (Map.Entry<String, String> entry : params.entrySet()) {
          result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
          result.append("=");
          result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
          result.append("&");
        }
 
        String resultString = result.toString();
        return resultString.length() > 0
          ? resultString.substring(0, resultString.length() - 1)
          : resultString;
    }
}
