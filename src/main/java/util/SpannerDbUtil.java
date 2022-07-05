package util;



import com.google.cloud.spanner.*;

import java.util.ArrayList;
import java.util.List;

public class SpannerDbUtil {

    protected static Spanner spanner;
    protected static DatabaseAdminClient databaseAdminClient;
    protected static InstanceAdminClient instanceAdminClient;
    protected static String projectId="tk-dev-micro";
    protected static String instanceId="order-spanner-dev";
    protected static String database="order_core_gk";

    private static DatabaseId databaseId;

    public static DatabaseClient getDatabaseClient() {

        final SpannerOptions options = SpannerOptions
                .newBuilder()
                .setAutoThrottleAdministrativeRequests()
                .build();
        spanner = options.getService();
        databaseAdminClient = spanner.getDatabaseAdminClient();
        instanceAdminClient = spanner.getInstanceAdminClient();
        databaseId = DatabaseId.of(projectId, instanceId, database);
        DatabaseClient client = spanner.getDatabaseClient(databaseId);
        return client;
    }


    public static List<Long> getOrdersFromSpanner(String startDate, String endDate){
        System.out.println("###########################################################################################");
        System.out.println("Fetching data from the Spanner DB");
        List<Long> orderIds = new ArrayList<>();
        DatabaseClient client=getDatabaseClient();
        String query = "SELECT order_id, reversed_order_id FROM orders@{FORCE_INDEX=orders_by_order_timestamp} WHERE order_timestamp >= '"+startDate+"' AND order_timestamp <= '"+endDate+"'";
        try (ResultSet resultSet = client
                .singleUse()
                .executeQuery(
                        Statement.of(query))) {
            while (resultSet.next()) {
                /*System.out.printf(
                        "order_id: %d, reversed_order_id: %d\n",
                        resultSet.getLong(0),
                        resultSet.getLong(1));*/
                orderIds.add(resultSet.getLong(0));
            }
        }
        System.out.println("size of order ids for spanner found are "+ orderIds.size());
        System.out.println("Fetching data from the Spanner DB is successful");
        System.out.println("###########################################################################################");

        return orderIds;
    }


    public static void main(String[] args) {
        List<Long> orderIds = new ArrayList<>();
        DatabaseClient client=getDatabaseClient();
        String query = "SELECT order_id, reversed_order_id FROM orders@{FORCE_INDEX=orders_by_order_timestamp} WHERE order_timestamp >= '2022-01-01 00:00:00' AND order_timestamp <= '2022-01-05 00:00:00'";
        try (ResultSet resultSet = client
                .singleUse()
                .executeQuery(
                        Statement.of(query))) {
            while (resultSet.next()) {
                System.out.printf(
                        "order_id: %d, reversed_order_id: %d\n",
                        resultSet.getLong(0),
                        resultSet.getLong(1));
                orderIds.add(resultSet.getLong(0));
            }
        }
        System.out.println("size of order ids found are "+ orderIds.size());
    }
}
