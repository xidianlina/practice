package com.pro.es;

import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.threadpool.ThreadPool;

import java.util.concurrent.TimeUnit;

public class BulkProcessorTest {
    public static void main(String[] args) {
        BulkProcessorTest bulkProcessor = new BulkProcessorTest();
        bulkProcessor.CreateManyIndex();
    }

    public void CreateManyIndex() {
        RestHighLevelClient client = EsBase.GetEsClient("localhost", 9200);

        Settings settings = Settings.EMPTY;
        ThreadPool threadPool = new ThreadPool(settings);
        BulkProcessor.Listener listener = new BulkProcessor.Listener() {
            public void beforeBulk(long executionId, BulkRequest bulkRequest) {
                //重写beforeBulk,在每次bulk request发出前执行,在这个方法里面可以知道在本次批量操作中有多少操作数
                int numberOfActions = bulkRequest.numberOfActions();
                System.out.printf("Executing bulk %d with %d requests", executionId, numberOfActions);
            }

            public void afterBulk(long executionId, BulkRequest bulkRequest, BulkResponse bulkResponse) {
                //重写afterBulk方法，每次批量请求结束后执行，可以在这里知道是否有错误发生。
                if (bulkResponse.hasFailures()) {
                    System.out.printf("Bulk %d executed with failures", executionId);
                } else {
                    System.out.printf("Bulk %d completed in %d milliseconds", executionId, bulkResponse.getTook().getMillis());
                }
            }

            public void afterBulk(long executionId, BulkRequest bulkRequest, Throwable throwable) {
                //重写方法，如果发生错误就会调用。
                System.out.printf("Failed to execute bulk", throwable);
            }
        };

        //使用builder做批量操作的控制
        BulkProcessor.Builder builder = new BulkProcessor.Builder(client::bulkAsync, listener, threadPool);
        //在这里调用build()方法构造bulkProcessor,在底层实际上是用了bulk的异步操作
        BulkProcessor bulkProcessor = builder.build();

        builder.setBulkActions(500); //执行多少次动作后刷新bulk.默认1000，-1禁用
        builder.setBulkSize(new ByteSizeValue(1L, ByteSizeUnit.MB));//执行的动作大小超过多少时，刷新bulk。默认5M，-1禁用
        builder.setConcurrentRequests(0);//最多允许多少请求同时执行。默认是1，0是只允许一个。
        builder.setFlushInterval(TimeValue.timeValueSeconds(10L));//设置刷新bulk的时间间隔。默认是不刷新的。
        builder.setBackoffPolicy(BackoffPolicy.constantBackoff(TimeValue.timeValueSeconds(1L), 3)); //设置补偿机制参数。由于资源限制（比如线程池满），批量操作可能会失败，在这定义批量操作的重试次数。

        //新建三个 index 请求
        IndexRequest one = new IndexRequest("posts", "doc", "1").
                source(XContentType.JSON, "title", "In which order are my Elasticsearch queries executed?");
        IndexRequest two = new IndexRequest("posts", "doc", "2")
                .source(XContentType.JSON, "title", "Current status and upcoming changes in Elasticsearch");
        IndexRequest three = new IndexRequest("posts", "doc", "3")
                .source(XContentType.JSON, "title", "The Future of Federated Search in Elasticsearch");
        //新的三条index请求加入到上面配置好的bulkProcessor里面。
        bulkProcessor.add(one);
        bulkProcessor.add(two);
        bulkProcessor.add(three);
        // add many request here.
        //bulkProcess必须被关闭才能使上面添加的操作生效
        bulkProcessor.close(); //立即关闭
        //关闭bulkProcess的两种方法：
        try {
            //2.调用awaitClose.
            //简单来说，就是在规定的时间内，是否所有批量操作完成。全部完成，返回true,未完成返//回false
            boolean terminated = bulkProcessor.awaitClose(30L, TimeUnit.SECONDS);
            System.out.println(terminated);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
