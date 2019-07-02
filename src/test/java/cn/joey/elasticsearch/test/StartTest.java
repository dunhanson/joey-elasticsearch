package cn.joey.elasticsearch.test;

import cn.joey.elasticsearch.entity.Organization;
import cn.joey.elasticsearch.utils.ElasticsearchUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;

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
        }
    }

    @Test
    public void loop() {
        for(int i = 0 ; i < 5; i++) {
            LocalDateTime start = LocalDateTime.now();
            search("南方电网");
            LocalDateTime end = LocalDateTime.now();
            Duration duration = Duration.between(start, end);
            System.out.println(duration.getSeconds());
            System.out.println(duration.getNano());
            System.out.println();
        }

        for(int i = 0 ; i < 5; i++) {
            LocalDateTime start = LocalDateTime.now();
            search("东方信腾");
            LocalDateTime end = LocalDateTime.now();
            Duration duration = Duration.between(start, end);
            System.out.println(duration.getSeconds());
            System.out.println(duration.getNano());
            System.out.println();
        }

        for(int i = 0 ; i < 5; i++) {
            LocalDateTime start = LocalDateTime.now();
            search("比地科技");
            LocalDateTime end = LocalDateTime.now();
            Duration duration = Duration.between(start, end);
            System.out.println(duration.getSeconds());
            System.out.println(duration.getNano());
            System.out.println();
        }
    }

    public void search(String name) {
        try {
            //Condition
            SearchSourceBuilder builder = new SearchSourceBuilder();
            builder.query(QueryBuilders.matchQuery("name", name));

            //Search
            List<Organization> list = ElasticsearchUtils.search(builder, Organization.class);
            System.out.println(list.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
