package corejava.sql;

import javax.sql.RowSet;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class ViewDB {
    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            JFrame frame = new ViewDBFrame();
            frame.setTitle("ViewDB");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}


class ViewDBFrame extends JFrame{
    private JButton previousButton;
    private JButton nextButton;
    private JButton deleteButton;
    private JButton saveButton;
    private DataPanel dataPanel;
    private Component scrollPane;
    private JComboBox<String> tableNames;
    private Properties props;
    private CachedRowSet crs;
    private Connection conn;

    public ViewDBFrame() {
        tableNames = new JComboBox<String>();

        try {
            //找到数据库中所有表名并绑定tableNames
            readDataBaseProperties();
            conn = getConnection();
            DatabaseMetaData metaData = conn.getMetaData();
            try(ResultSet mrs = metaData.getTables(null,null,null,new String[] {"TABLE"})){
                while(mrs.next())
                    tableNames.addItem(mrs.getString(3));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            for (Throwable t : e)
                t.printStackTrace();
        }

        tableNames.addActionListener(event -> showTable((String) tableNames.getSelectedItem(),conn));

        add(tableNames,BorderLayout.NORTH);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try{
                    if(conn != null)conn.close();
                }
                catch (SQLException ex){
                    for (Throwable t : ex)
                        t.printStackTrace();
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        add(buttonPanel,BorderLayout.SOUTH);

        previousButton = new JButton("Previous");
        previousButton.addActionListener(event-> showPreviousRow());
        buttonPanel.add(previousButton);

        nextButton = new JButton("Next");
        nextButton.addActionListener(event -> showNextRow());
        buttonPanel.add(nextButton);

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(event -> deleteRow());
        buttonPanel.add(deleteButton);

        saveButton = new JButton("Save");
        saveButton.addActionListener(event->saveChanges());
        buttonPanel.add(saveButton);

        if(tableNames.getItemCount() > 0)
            showTable(tableNames.getItemAt(0),conn);
    }

    public void showTable(String tableName,Connection conn){
        //显示选择的表信息
        //查询表内所有数据
        try(Statement stat = conn.createStatement();
        ResultSet result = stat.executeQuery("select * from "+tableName)){
            //新建cachedRowSet(被缓存的行集)对象
            RowSetFactory factory = RowSetProvider.newFactory();
            crs = factory.createCachedRowSet();
            crs.setTableName(tableName);
            //将查询结果放入行集
            crs.populate(result);
            //新建一个可滚动的画板
            if(scrollPane != null)remove(scrollPane);
            dataPanel = new DataPanel(crs);
            scrollPane = new JScrollPane(dataPanel);
            //将画板放入界面中心
            add(scrollPane,BorderLayout.CENTER);
            pack();
            //显示第一条数据
            showNextRow();
        }
        catch(SQLException e){
            for (Throwable throwable : e) {
                throwable.printStackTrace();
            }
        }
    }

    public void showPreviousRow(){
        try {
            //第一条没有最前面一条
            if(crs == null || crs.isFirst()) return;
            crs.previous();
            dataPanel.showRow(crs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showNextRow(){
        try {
            //最后面一条没有后面一条
            if(crs == null || crs.isLast()) return;
            crs.next();
            dataPanel.showRow(crs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteRow(){
        if(crs == null)return;
        new SwingWorker<Void,Void>(){
            public Void doInBackground() throws SQLException {
                crs.deleteRow();
                //关闭自动提交
                conn.setAutoCommit(false);
                //手动提交
                crs.acceptChanges(conn);
                if(crs.isAfterLast())
                    if(!crs.last())crs = null;

                return null;
            }

            public void done(){
                //删除数据后重新显示
                dataPanel.showRow(crs);
            }
        }.execute();
    }
    /*
    * save all changes
    * */
    public void saveChanges(){
        if(crs == null)return;
        new SwingWorker<Void,Void>(){
            public Void doInBackground() throws SQLException {
                dataPanel.setRow(crs);
                conn.setAutoCommit(false);
                crs.acceptChanges(conn);
                return null;
            }
        }.execute();
    }

    private void readDataBaseProperties() throws IOException{
        props = new Properties();
        try(InputStream in = Files.newInputStream(Paths.get("database.properties"))){
            props.load(in);
        }

        String drivers = props.getProperty("jdbc.drivers");
        if(drivers != null)System.setProperty("jdbc.drivers",drivers);
    }

    private Connection getConnection()throws SQLException{
        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");

        return  DriverManager.getConnection(url,username,password);
    }
}

/*
* This panel displays the contents of a result set
* */
class DataPanel extends JPanel{
    private java.util.List<JTextField> fields;

    public DataPanel(RowSet rs) throws SQLException{
        fields = new ArrayList<>();
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.gridheight = 1;

        ResultSetMetaData rsmd = rs.getMetaData();
        for (int i = 1; i <= rsmd.getColumnCount() ; i++) {
            gbc.gridy = i-1;

            String columnName = rsmd.getColumnLabel(i);
            gbc.gridx = 0;
            gbc.anchor = GridBagConstraints.EAST;
            add(new JLabel(columnName),gbc);

            int columnWidth = rsmd.getColumnDisplaySize(i);
            JTextField tb = new JTextField(columnWidth);
            if(!rsmd.getColumnClassName(i).equals("java.lang.String"))
                tb.setEditable(false);

            fields.add(tb);

            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.WEST;
            add(tb,gbc);
        }
    }

    /*
    * Shows a database row by populating all text fields with the column values
    * */
    public void showRow(ResultSet rs){
        try {
            if(rs == null)return;
            for (int i = 1; i <= fields.size(); i++) {
                String field = rs == null ? "" : rs.getString(i);
                JTextField tb = fields.get(i-1);
                tb.setText(field);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    * Updates changed data into the current row of the row set
    * */
    public void setRow(RowSet rs) throws SQLException{
        for (int i = 1; i <= fields.size(); i++) {
            String field = rs.getString(i);
            JTextField tb = fields.get(i-1);
            //修改结果集
            if(!field.equals(tb.getText()))
                rs.updateString(i,tb.getText());
        }
        //更新数据库
        rs.updateRow();
    }
}