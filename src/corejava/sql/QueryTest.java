package corejava.sql;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class QueryTest {

    private static final String allQuery = "select books.price,books.title from books";

    //根据作者名和出版社名找书
    // authors.author_name = "Alexander" and publishers.publisher_name = "John wiley & Sons";
    private static final String authorPublisherQuery = "select books.price,books.title from books,booksauthors,authors,publishers " +
            "where authors.author_id = booksauthors.author_id and booksauthors.ISBN = books.ISBN " +
            "and books.publisher_id = publishers.publisher_id and authors.author_name = ? " +
            "and publishers.publisher_name = ?";
    //根据作者名找书
    //authors.author_name = "Alexander"
    private static final String authorQuery = "select books.price,books.title from books,booksauthors,authors " +
            "where authors.author_id = booksauthors.author_id and booksauthors.ISBN = books.ISBN " +
            "and authors.author_name = ?";

    //根据出版商找书
    //publishers.publisher_name = "John wiley & Sons"
    private static final String publisherQuery = "select books.price,books.title from books,publishers " +
            "where publishers.publisher_id = books.publisher_id and publishers.publisher_name = ?";

    //把一个出版商的书全部涨价
    private static final String priceUpdate = "update books " +
            "set price= price+? " +
            "where books.publisher_id = (select publishers.publisher_id from publishers where publishers.publisher_name = ?)";

    private static Scanner in;
    private static ArrayList<String> authors = new ArrayList<>();
    private static ArrayList<String> publishers = new ArrayList<>();

    public static void main(String[] args) throws IOException{
        try(Connection conn = getConnection()){
            in = new Scanner(System.in);
            authors.add("Any");
            publishers.add("Any");

            //查询出所有出版社和作者的名字
            try(Statement stat = conn.createStatement()){
                //Fill the authors array list
                String query = "select author_name from authors";
                try(ResultSet rs = stat.executeQuery(query)){
                    while(rs.next())
                        authors.add(rs.getString(1));
                }

                //Fill the publishers array list
                query = "select publisher_name from publishers";
                stat.execute(query);
                try(ResultSet rs = stat.executeQuery(query)){
                    while(rs.next())
                        publishers.add(rs.getString(1));
                }
            }
            //打印菜单
            boolean done = false;
            while(!done){
                System.out.print("(Q)uery (C)hange prices (E)xit: ");
                String input = in.next().toUpperCase();
                if(input.equals("Q"))
                    executeQuery(conn);
                else if(input.equals("C"))
                    changePrices(conn);
                else
                    done = true;
            }
        }
        catch (SQLException e){
            for (Throwable t : e) {
                t.printStackTrace();
            }
        }

    }

    private static void executeQuery(Connection conn)throws SQLException{

        //得到选择的出版商名和作者名
        String author = select("Authors:",authors);
        String publisher = select("Publisher:",publishers);

        //执行相应的查询
        PreparedStatement pstat;
        if(!author.equals("Any") && !publisher.equals("Any")){
            pstat = conn.prepareStatement(authorPublisherQuery);
            pstat.setString(1,author);
            pstat.setString(2,publisher);
        }
        else if(!author.equals("Any") && publisher.equals("Any")){
            pstat = conn.prepareStatement(authorQuery);
            pstat.setString(1,author);
        }
        else if(author.equals("Any") && !publisher.equals("Any")){
            pstat = conn.prepareStatement(publisherQuery);
            pstat.setString(1,publisher);
        }
        else{
            pstat = conn.prepareStatement(allQuery);
        }
        //显示结果集
        try(ResultSet rs = pstat.executeQuery()){

            while(rs.next())
                System.out.println(rs.getString(1)+","+rs.getString(2));
        }
    }

    public static void changePrices(Connection conn) throws SQLException{
        String publisher = select("publishers:",publishers.subList(1,publishers.size()));
        System.out.print("Change prices by: ");
        double priceChange = in.nextDouble();
        PreparedStatement pstat = conn.prepareStatement(priceUpdate);
        pstat.setDouble(1,priceChange);
        pstat.setString(2,publisher);
        int r = pstat.executeUpdate();
        System.out.println(r+"records updated.");
    }

    public static String select(String prompt,List<String> options){
        //打印选项并返回选择的值
        while(true){
            System.out.println(prompt);
            for (int i = 0; i < options.size(); i++)
                System.out.printf("%2d) %s%n",i+1,options.get(i));

            int sel = in.nextInt();
            if(sel >0 && sel <= options.size())
                return options.get(sel-1);
            else
                System.out.println("选项输入错误！请重输。");
        }
    }

    /*
     * Gets a connection from the properties specified in the file database.properties
     * @return the database connection
     * */
    public static Connection getConnection() throws SQLException, IOException {
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
}
