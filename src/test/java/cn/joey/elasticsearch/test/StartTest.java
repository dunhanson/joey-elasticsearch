package cn.joey.elasticsearch.test;

import cn.joey.elasticsearch.entity.Organization;
import cn.joey.elasticsearch.utils.ElasticsearchUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

public class StartTest {
    public static void main(String[] args) {
        try {
            //Condition
            SearchSourceBuilder builder = new SearchSourceBuilder();
            builder.query(QueryBuilders.matchQuery("name", "南方电网"));

            //Search
            ElasticsearchUtils.search(builder, Organization.class).forEach(org->{
                System.out.println(org);
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ElasticsearchUtils.close();
        }
    }
}
