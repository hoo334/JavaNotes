package corejava.sql;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class ExecSQL {
    public static void main(String[] args) throws IOException{
        Scanner in =  new Scanner(System.in);
        System.out.println("输入SQL语句: e.g. select * from publishers");
        if(!in.hasNextLine())return;
        String line = in.nextLine();
        //if(line.equalsIgnoreCase("exit"))return;

        try(Connection conn = getConnection(); Statement stat = conn.createStatement()){
            boolean isResult = stat.execute(line);
            if(isResult){
                try(ResultSet rs = stat.getResultSet()){
                    showResultSet(rs);
                }
            }
            else{
                int updateCount = stat.getUpdateCount();
                System.out.println(updateCount+ " rows updated");
            }
        }
        catch(SQLException e){
            for (Throwable t : e)
                t.printStackTrace();
        }
    }

    /*
    * Gets a connection from the properties specified in the file database.properties
    * @return the database connection
    * */
    public static Connection getConnection() throws SQLException, IOException{
        Properties props = new Properties();
        try(InputStream in = Files.newInputStream(Paths.get("database.properties"))){
            props.load(in);
        }

        String drivers = props.getProperty("jdbc.drivers");
        if(drivers != null)System.setProperty("jdbc.drivers",drivers);
        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");

        return  DriverManager.getConnection(url,username,password);
    }

    /*
    * Prints a result set
    * @para result the result to be printed
    * */
    public static void showResultSet(ResultSet result)throws SQLException{
        //打印列表名
        ResultSetMetaData metaData = result.getMetaData();
        int columnCount = metaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            System.out.printf("%-20s",metaData.getColumnLabel(i));
        }
        System.out.println();

        //打印列表内容
        while(result.next()){
            for (int i = 1; i <= columnCount; i++) {
                System.out.printf("%-20s",result.getString(i));
            }
            System.out.println();
        }
    }
}
