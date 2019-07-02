package cn.joey.elasticsearch.test;

import cn.joey.elasticsearch.core.Store;
import cn.joey.elasticsearch.utils.ElasticsearchUtils;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * @author dunhanson
 * @version 1.0
 * @date 2019/7/2
 * @description
 */
public class DemoTest {
    public static void main(String[] args) {

    }

    public void test2() {

    }

    public void test() {
        RestHighLevelClient client = Store.getInstance().getClient();
        try {
            System.out.println(client.ping(RequestOptions.DEFAULT));
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
