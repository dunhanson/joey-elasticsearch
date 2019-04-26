package cn.joey.elasticsearch.utils;

import com.google.gson.Gson;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Elasticsearch工具类
 * @author dunhanson
 * @date 2019-04-23
 */
public class ElasticsearchUtils {
    private static RestHighLevelClient client;
    private static Map<String, String> indicesMap = new HashMap<>();

    /**
     * 初始化资源
     */
    static {
        try(InputStream in = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("joey-elasticsearch.properties")) {

            //加载es配置文件
            Properties properties = new Properties();
            properties.load(in);

            //获取索引名称和实体对象类
            String[] indicesArr = properties.getProperty("indices").split(",");
            Arrays.asList(indicesArr).forEach(indices->{
                String[] arr = indices.split(":");
                indicesMap.put(arr[1], arr[0]);
            });

            //ES配置
            RestClientBuilder restClientBuilder = RestClient.builder(
                    getHttpHosts(properties.getProperty("host").split(","))
            );
            client = new RestHighLevelClient(restClientBuilder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * HttpHost获取
     * @param hostArr
     * @return
     */
    public static HttpHost[] getHttpHosts(String[] hostArr) {
        List<HttpHost> list = new ArrayList<>();
        Arrays.asList(hostArr).forEach(host->{
            String[] str = host.split(":");
            list.add(new HttpHost(str[0], Integer.parseInt(str[1]), "http"));
        });
        return list.toArray(new HttpHost[list.size()]);
    }

    /**
     * 查询
     * @param builder
     * @param clazz
     * @return
     */
    public static <T> List<T> search(SearchSourceBuilder builder, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        Gson gson =  new Gson();
        try {
            //设置indices
            SearchRequest request = new SearchRequest();
            request.indices(indicesMap.get(clazz.getSimpleName()));
            request.source(builder);

            //查询返回结果
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);

            //数量判断
            if(response.getHits().totalHits <= 0) {
                return result;
            }

            //遍历结果
            response.getHits().forEach(hit->{
                result.add(gson.fromJson(hit.getSourceAsString(), clazz));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 关闭资源
     */
    public static void close() {
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
