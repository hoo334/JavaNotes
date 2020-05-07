package corejava.socket;

import java.io.IOException;
import java.net.InetAddress;

//打印出本机的因特网地址，如果在命令行中指定了主机名，它将打印出该主机的所有因特网地址。
public class InetAddressTest {
    public static void main(String[] args) throws IOException{
        if(args.length > 0){
            String host = args[0];
            InetAddress[] addresses = InetAddress.getAllByName(host);
            for (InetAddress address : addresses) {
                System.out.println(address);
            }
        }
        else{
            InetAddress localHostAddress = InetAddress.getLocalHost();
            System.out.println(localHostAddress);
        }
    }
}
