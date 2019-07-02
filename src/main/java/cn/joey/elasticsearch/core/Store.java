package cn.joey.elasticsearch.core;

import cn.joey.elasticsearch.utils.CommonUtils;
import cn.joey.elasticsearch.utils.ElasticsearchUtils;
import lombok.Data;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.InputStream;
import java.util.*;


/**
 * @author dunhanson
 * @version 1.0
 * @date 2019/7/2
 * @description
 */
@Data
public class Store {
    private static volatile Store instance = null;
    private RestHighLevelClient client = null;

    /**
     * 获取实例
     * @return
     */
    public static Store getInstance() {
        if(instance == null) {
            synchronized (Store.class) {
                instance = new Store();
            }
        }
        return instance;
    }

    /**
     * 获取Client
     * @return
     */
    public RestHighLevelClient getClient() {
        if(client == null || !isValid()) {
            Properties properties = ElasticsearchUtils.getProperties();
            //ES配置
            RestClientBuilder restClientBuilder = RestClient.builder(
                    getHttpHosts(properties.getProperty("host").split(","))
            );
            //生成Client
            client = new RestHighLevelClient(restClientBuilder);
        }
        return client;
    }

    /**
     * HttpHost获取
     * @param hostArr
     * @return
     */
    public HttpHost[] getHttpHosts(String[] hostArr) {
        List<HttpHost> list = new ArrayList<>();
        Arrays.asList(hostArr).forEach(host->{
            String[] str = host.split(":");
            list.add(new HttpHost(str[0], Integer.parseInt(str[1]), "http"));
        });
        return list.toArray(new HttpHost[list.size()]);
    }

    /**
     * 判断是否有效
     * @return
     */
    public boolean isValid() {
        try {
            if(client != null) {
                client.ping(RequestOptions.DEFAULT);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
