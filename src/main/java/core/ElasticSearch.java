package core;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import util.ElasticSearchUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ElasticSearch {


    public static void main(String[] args) throws IOException {
        //countSearchApi();
        //searchQueryApiById();
        mGetQueryApiById();
    }


    public static void searchQueryApiById() throws IOException {
        // Fetch the data from the ES for the orders we get from both Mysql and Spanner
        List<String> idList = Arrays.asList("700114930","1100063183","1100129743","9871289612","1100037583");
        String [] ids= (String[]) idList.toArray();
        RestHighLevelClient esClient = ElasticSearchUtil.getClient();
        SearchRequest searchRequest = new SearchRequest("orders");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.idsQuery().addIds(ids));
        searchSourceBuilder.fetchSource(false);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        System.out.println(hits.getTotalHits().value);
        esClient.close();
    }



    //Need to work on this
    public static void mGetQueryApiById() throws IOException {
        // Fetch the data from the ES for the orders we get from both Mysql and Spanner
        List<String> idList = Arrays.asList("700114930","9871289612");
        String [] ids= (String[]) idList.toArray();
        RestHighLevelClient esClient = ElasticSearchUtil.getClient();

        MultiGetRequest request = new MultiGetRequest();
        request.add(new MultiGetRequest.Item("orders", "700114930").fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE));
        request.add(new MultiGetRequest.Item("orders", "9871289612").fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE));

        MultiGetResponse response = esClient.mget(request, RequestOptions.DEFAULT);

        MultiGetItemResponse firstItem = response.getResponses()[0];
        GetResponse firstGet = firstItem.getResponse();
        if (firstGet.isExists()) {
            //long version = firstGet.getVersion();
            System.out.println(" found "+ firstGet.getId());
        } else {
            System.out.println("Not found "+ firstGet.getId());

        }

        firstItem = response.getResponses()[1];
        firstGet = firstItem.getResponse();
        if (firstGet.isExists()) {
            //long version = firstGet.getVersion();
            System.out.println(" found "+ firstGet.getId());
        } else {
            System.out.println("Not found "+ firstGet.getId());

        }




        esClient.close();
    }


    public static void countSearchApi() throws IOException {
        // Fetch the data from the ES for the orders we get from both Mysql and Spanner
        RestHighLevelClient esClient = ElasticSearchUtil.getClient();
        CountRequest countRequest = new CountRequest("orders");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        countRequest.source(searchSourceBuilder);
        CountResponse countResponse  = esClient.count(countRequest, RequestOptions.DEFAULT);
        System.out.println(countResponse.getCount());
        esClient.close();
    }
}
