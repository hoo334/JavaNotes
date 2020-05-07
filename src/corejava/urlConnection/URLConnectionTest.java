package corejava.urlConnection;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/*
* This program connects to an URL and displays the response header data and the first 10 lines of the request data
* */
public class URLConnectionTest {
    public static void main(String[] args) {
        try {
            String  urlName = "http://baidu.com";
            URL url = new URL(urlName);
            URLConnection conn = url.openConnection();
            conn.connect();

            //print header fields
            Map<String,List<String>> headers = conn.getHeaderFields();
            for(Map.Entry<String,List<String>> entry : headers.entrySet()){
                String key = entry.getKey();
                for(String value : entry.getValue())
                    System.out.println(key + ":"+value);
            }

            //print convenience functions
            System.out.println("--------------------------");
            System.out.println("getContentType: " + conn.getContentType());
            System.out.println("getContentLength: " + conn.getContentLength());
            System.out.println("getContentEncoding: "+conn.getContentEncoding());
            System.out.println("getDate: "+conn.getDate());
            System.out.println("getExpiration: "+conn.getExpiration());
            System.out.println("getLastModified: "+conn.getLastModified());
            System.out.println("--------------------------");

            String encoding = conn.getContentEncoding();
            if(encoding == null)encoding = "UTF-8";
            try(Scanner in = new Scanner(conn.getInputStream(),encoding)){
                //print first ten lines of contents
                for (int i = 1; in.hasNextLine() && i <=10 ; i++)
                    System.out.println(in.nextLine());
                if(in.hasNextLine()) System.out.println("...");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

