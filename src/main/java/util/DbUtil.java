package util;

import Model.OrderCart;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class DbUtil {
    private static String dbhost = "jdbc:mysql://192.168.70.41:3306/tiket_production?zeroDateTimeBehavior=convertToNull";
    private static String username = "user_select";
    private static String password = "select@123";
    private static Connection conn;
    private static Statement stmt;
    private static ResultSet results;

    @SuppressWarnings("finally")
    public static Connection createNewDBconnection() {
        try  {
            conn = DriverManager.getConnection(
                    dbhost, username, password);
        } catch (SQLException e) {
            System.out.println("Cannot create database connection");
            e.printStackTrace();
        } finally {
            return conn;
        }
    }

    public  static List<Long> getOrdersFromMysqlDb(String startDate, String endDate){
        System.out.println("###########################################################################################");
        System.out.println("Fetching data from the Monolith DB");
        List<Long> orderId = new ArrayList<>();

        String sql_select = "SELECT * FROM order__cart WHERE order_timestamp >= '"+startDate+"' AND order_timestamp <= '"+endDate+"';";
        List<OrderCart> pojoList=null;

        ResultSetMapper<OrderCart> resultSetMapper = new ResultSetMapper<OrderCart>();
        try(Connection conn = DbUtil.createNewDBconnection()){

            stmt = conn.createStatement();
            results = stmt.executeQuery(sql_select);

            // simple JDBC code to run SQL query and populate resultSet - END
            pojoList = resultSetMapper.mapRersultSetToObject(results, OrderCart.class);
            // print out the list retrieved from database

            if(pojoList != null){
                //System.out.println("size of the entries are "+ pojoList.size());
                for(OrderCart pojo : pojoList){
                    orderId.add(pojo.getOrderId());
                }
            }else{
                System.out.println("ResultSet is empty. Please check if database table is empty");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("No of orders found from the DB is "+orderId.size());
        System.out.println("Fetching data from the Monolith DB is successful");
        System.out.println("###########################################################################################");
        return orderId;
    }
}
