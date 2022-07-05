package util;


import com.google.common.base.Functions;
import com.google.common.collect.Lists;
import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ElasticSearchUtil {


    public static RestHighLevelClient getClient() {

        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("192.168.60.233", 9200, "http")));

        return client;
    }

    public static Long searchQueryApiById(RestHighLevelClient esClient,List<Long> orders) throws IOException {
        // Fetch the data from the ES for the orders we get from both Mysql and Spanner
        System.out.println("###########################################################################################");
        System.out.println("Searching elasticsearch for number of orders present  ");
        List<String> idList = Lists.transform(orders, Functions.toStringFunction());
        String [] ids=  idList.toArray(new String[0]);
        SearchRequest searchRequest = new SearchRequest("orders");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.idsQuery().addIds(ids));
        searchSourceBuilder.fetchSource(false);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        System.out.println("Total orders found is "+ hits.getTotalHits().value);
        System.out.println("Searching elasticsearch for number of orders present is done ");
        System.out.println("###########################################################################################");
        return hits.getTotalHits().value;
    }



    //Need to work on this
    public static  List<String> mGetQueryApiById(RestHighLevelClient esClient,List<Long> orders ) throws IOException {
        // Fetch the data from the ES for the orders we get from both Mysql and Spanner
        System.out.println("###########################################################################################");
        System.out.println("Fetching data from the ES for finding missing order ids ");
        List<String> foundIds = new ArrayList<>();
        List<String> notFoundIds = new ArrayList<>();
        MultiGetRequest request = new MultiGetRequest();
        for (int i=0 ;i<orders.size();i++){
            request.add(new MultiGetRequest.Item("orders", orders.get(i).toString()).fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE));
        }

        MultiGetResponse response = esClient.mget(request, RequestOptions.DEFAULT);

        for(int i=0 ;i<orders.size();i++){
            MultiGetItemResponse item = response.getResponses()[i];
            GetResponse itemGet = item.getResponse();
            if (itemGet.isExists()) {
                //long version = firstGet.getVersion();
               // System.out.println(" found "+ itemGet.getId());
                foundIds.add(itemGet.getId());
            } else {
                //System.out.println("Not found "+ itemGet.getId());
                notFoundIds.add(itemGet.getId());
            }
        }
        System.out.println("Size of Found ids is  "+foundIds.size());
        System.out.println("Size of non Found ids is  "+notFoundIds.size());
        System.out.println("Fetching data from the ES for finding missing order ids is done ");
        System.out.println("###########################################################################################");
        return notFoundIds;
    }


    public static Long countSearchApi(RestHighLevelClient esClient) throws IOException {
        // Fetch the data from the ES for the orders we get from both Mysql and Spanner
        System.out.println("###########################################################################################");
        System.out.println("Fetching the total number of documents available in ES ");
        CountRequest countRequest = new CountRequest("orders");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        countRequest.source(searchSourceBuilder);
        CountResponse countResponse  = esClient.count(countRequest, RequestOptions.DEFAULT);
        System.out.println("Total document present in the given Index is "+countResponse.getCount());
        System.out.println("Fetching the total number of documents available in ES is done ");
        System.out.println("###########################################################################################");
        return countResponse.getCount();

    }

    public static List<Long> searchApiScroll(RestHighLevelClient esClient) throws IOException {
        // Fetch the data from the ES for the orders we get from both Mysql and Spanner
        System.out.println("###########################################################################################");
        System.out.println("Fetching the all the order ids  available in ES ");
        List<Long> orderIds = new ArrayList<>();
        int i =0;
        final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
        SearchRequest searchRequest = new SearchRequest("orders");
        searchRequest.scroll(scroll);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);
        String scrollId = searchResponse.getScrollId();
        SearchHit[] searchHits = searchResponse.getHits().getHits();
        for (SearchHit hit : searchHits) {
            // do something with the SearchHit
            orderIds.add(Long.valueOf(hit.getId()));
            i=i+1;
            System.out.println("round "+i);
        }

        while (searchHits != null && searchHits.length > 0) {

            SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
            scrollRequest.scroll(scroll);
            searchResponse = esClient.scroll(scrollRequest, RequestOptions.DEFAULT);
            scrollId = searchResponse.getScrollId();
            searchHits = searchResponse.getHits().getHits();
            for (SearchHit hit : searchHits) {
                // do something with the SearchHit
                orderIds.add(Long.valueOf(hit.getId()));
                i=i+1;
                System.out.println("round "+i);
            }
        }

        ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
        clearScrollRequest.addScrollId(scrollId);
        ClearScrollResponse clearScrollResponse = esClient.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
        boolean succeeded = clearScrollResponse.isSucceeded();


        System.out.println("Total order ids found are "+orderIds.size());
        System.out.println("Fetching the all the order ids  available in ES is done ");
        System.out.println("###########################################################################################");
        return orderIds;

    }


    public static void main(String[] args) throws IOException {
        RestHighLevelClient esClient = ElasticSearchUtil.getClient();
        List<Long> esOrders=searchApiScroll(esClient);
        System.out.println(esOrders.size());
        esClient.close();
    }

}
