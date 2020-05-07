package corejava.urlConnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

/*
* This program demonstrates how to use the URLConnection class foe a POST request
* */
public class PostTest {

    public static void main(String[] args) throws IOException{
        //读取文件
        String propsname = args.length>0 ? args[0] : "post.properties";
        Properties props = new Properties();
        try(InputStream in = Files.newInputStream(Paths.get(propsname))){
             props.load(in);
        }
        //获取元素
        String urlString = props.remove("url").toString();
        Object userAgent = props.remove("User-Agent");
        Object redirects = props.remove("redirects");
        //配置Cookie处理器
        CookieHandler.setDefault(new CookieManager(null,CookiePolicy.ACCEPT_ALL));
        //
        String result = doPost(new URL(urlString),props,userAgent == null ? null : userAgent.toString(),
                redirects == null ? -1 : Integer.parseInt(redirects.toString()));
        System.out.println(result);
    }

    /*
    * Do an HTTP POST
    * @param url the URL to post to
    * @param nameValuePairs the query parameters
    * @param userAgent the user Agent to use,or null for the default user agent
    * @param redirects the number of redirects to follow manually,or -1 for automatic redirects
    * @return the data returned from the server
    * */
    public static String doPost(URL url,Map<Object,Object> nameValuePairs,String userAgent,int redirects) throws IOException{
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        if(userAgent != null)
            conn.setRequestProperty("User-Agent",userAgent);
        //关闭自动重定向
        if(redirects > 0)
            conn.setInstanceFollowRedirects(false);

        conn.setDoOutput(true);

        //生成输出流
        try(PrintWriter out = new PrintWriter(conn.getOutputStream())){
            boolean first = true;
            for(Map.Entry<Object,Object> pair : nameValuePairs.entrySet()){
                if(first) first=false;
                else out.print('&');
                String name = pair.getKey().toString();
                String value = pair.getValue().toString();
                out.print(name);
                out.print("=");
                out.print(URLEncoder.encode(value,"UTF-8"));
            }
        }

        //实现人工重定向
        String encoding = conn.getContentEncoding();
        if(encoding == null)encoding = "UTF-8";
        if(redirects > 0){
            int responseCode = conn.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_MOVED_PERM ||
            responseCode == HttpURLConnection.HTTP_MOVED_TEMP ||
            responseCode == HttpURLConnection.HTTP_SEE_OTHER){
                String location = conn.getHeaderField("Location");
                if(location != null){
                    URL base = conn.getURL();
                    conn.disconnect();
                    return doPost(new URL(base,location),nameValuePairs,userAgent,redirects - 1);
                }
            }
        }
        else if(redirects == 0) {
            throw new IOException("Too many redirects");
        }

        StringBuilder response = new StringBuilder();
        try(Scanner in = new Scanner(conn.getInputStream(),encoding)){
            while(in.hasNextLine()){
                response.append(in.nextLine());
                response.append("\n");
            }
        }
        catch (IOException e){
            InputStream err = conn.getErrorStream();
            if(err == null)throw e;
            try(Scanner in = new Scanner(err)){
                response.append(in.nextLine());
                response.append("\n");
            }
        }
        return response.toString();
    }
}
