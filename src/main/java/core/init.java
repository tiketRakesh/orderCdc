package core;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.client.RestHighLevelClient;
import util.DbUtil;
import util.ElasticSearchUtil;
import util.SpannerDbUtil;

public class init {



    public static void main(String[] args) throws IOException {
        Instant inst1 = Instant.now();
        RestHighLevelClient esClient =null;
        try {
            //fetch data from the mysql data base
            String startDate ="2022-04-01 00:00:00";
            String endDate ="2022-07-31 00:00:00";
            List<Long> dbOrders = DbUtil.getOrdersFromMysqlDb(startDate,endDate);

            //fetch the data from the spanner DB
            List<Long> spannerOrders= SpannerDbUtil.getOrdersFromSpanner(startDate,endDate);
            List<Long> orderList = new ArrayList<Long>(dbOrders);
            orderList.addAll(spannerOrders);

            // Fetch the data from the ES for the orders we get from both Mysql and Spanner
            esClient = ElasticSearchUtil.getClient();
           // Long totalRecordsES = ElasticSearchUtil.countSearchApi(esClient);
           /* if(totalRecordsES>orderList.size()){
                //Below Logic is to find orderIDs that are not in DB but present in the ES
                List<Long> esOrders=ElasticSearchUtil.searchApiScroll(esClient);
                // Logic to find the extra or miss-matched order ids
                Util.compare(esOrders,orderList);
            }*/



            // Below logic is  for finding missing records present in DB but not in ES
            //need the fix here as the max limit in api is 10,000 ids
            Long found= ElasticSearchUtil.searchQueryApiById(esClient,orderList);
            if(found==orderList.size()){
                System.out.println("All Records matched");
            }else{
                System.out.println("Mismatch of the records are found");
                List<String> notFoundIds= ElasticSearchUtil.mGetQueryApiById(esClient,orderList);
                System.out.println(notFoundIds);
            }
            Instant inst2 = Instant.now();
            System.out.println("Elapsed Time: "+ Duration.between(inst1, inst2).toString());
        } finally {
            if(esClient!=null){
                esClient.close();
            }
        }
    }



}
