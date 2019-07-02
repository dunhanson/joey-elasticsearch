package cn.joey.elasticsearch.utils;

import cn.joey.elasticsearch.core.Store;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Elasticsearch工具类
 * @author dunhanson
 * @date 2019-04-23
 */
@Slf4j
public class ElasticsearchUtils {
    private static Properties properties;
    private static Map<String, String> indicesMap = new HashMap<>();

    static {
        init();
    }

    /**
     * 初始化代码块
     */
    public static void init(){
        if(indicesMap.isEmpty()) {
            //获取索引名称和实体对象类
            String[] indicesArr = getProperties().getProperty("indices").split(",");
            Arrays.asList(indicesArr).forEach(indices->{
                String[] arr = indices.split(":");
                indicesMap.put(arr[1], arr[0]);
            });
        }
    }

    /**
     * 初始化indicesMap
     * @param simpleName
     * @return
     */
    public static String getIndices(String simpleName) {
        return indicesMap.get(simpleName);
    }

    /**
     * 获取Properties
     * @return
     */
    public static Properties getProperties() {
        if(properties == null) {
            //获取配置文件
            String configFileName = CommonUtils.getConfigFileName();
            try(InputStream in = Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream(configFileName)) {
                //加载es配置文件
                properties = new Properties();
                properties.load(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return properties;
    }

    /**
     * 查询
     * @param builder
     * @param clazz
     * @return
     */
    public static <T> List<T> search(SearchSourceBuilder builder, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        try {
            //设置indices
            SearchRequest request = new SearchRequest();
            request.indices(getIndices(clazz.getSimpleName()));
            request.source(builder);

            //打印查询内容
            log.info(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(builder.toString()));

            //查询返回结果
            SearchResponse response = Store.getInstance().getClient().search(request, RequestOptions.DEFAULT);

            //数量判断
            if(response.getHits().totalHits <= 0) {
                return result;
            }

            //遍历结果
            Gson gson = new Gson();
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
            Store.getInstance().getClient().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
