package com.sora.utils;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sora.jackson.InitObjectMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.xcontent.XContentBuilder;
import org.elasticsearch.xcontent.XContentFactory;
import org.elasticsearch.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Classname ElasticSearchUtils
 * @Description ElasticSearch常用方法
 * @Date 2023/10/25 16:48
 * @Author by Sora33
 */
public class ElasticSearchUtils {

    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchUtils.class);

    /**
     * 批量插入数据的最大数
     */
    private static int MAX_COUNT = 3;

    private static RestHighLevelClient restHighLevelClient;

    private static ObjectMapper objectMapper = InitObjectMapper.initObjectMapper();

    public ElasticSearchUtils(RestHighLevelClient restHighLevelClient){
        ElasticSearchUtils.restHighLevelClient = restHighLevelClient;
    }


    /**
     * 获取es连接客户端，可以传递多个es地址
     * @param addressList es地址 格式为 esIP:端口号
     * @return
     */
    public static RestHighLevelClient getConnect(List<String> addressList) {
        RestClientBuilder builder = RestClient.builder(
                addressList.stream()
                        .map(address -> {
                            String[] split = address.split(":");
                            return new HttpHost(split[0], Integer.parseInt(split[1]));
                        })
                        .toArray(HttpHost[]::new)
        );
        return new RestHighLevelClient(builder);
    }


    /**
     * 设置高亮字段
     * @param searchSourceBuilder 查询元
     * @param color 高亮颜色
     * @param fieldName 高亮字段名
     * @return
     */
    public void setHighLightField(SearchSourceBuilder searchSourceBuilder, String color, String... fieldName) {
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<span color=\"" + color + "\">");
        highlightBuilder.postTags("/span");
        for (String name : fieldName) {
            highlightBuilder.field(name);
        }
        searchSourceBuilder.highlighter(highlightBuilder);
    }


    /**
     * 获取查询元
     * @param paramMap 条件map key为es字段 value为字段值
     * @return
     */
    public SearchSourceBuilder getSearchSourceBuilder(HashMap<String, Object> paramMap) {
        // 创建查询请求
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 创建多条件查询元
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        // 加入条件
        paramMap.forEach((k,v) -> {
            boolQueryBuilder.must(new TermQueryBuilder(k,v));
        });
        // 装载多条件查询
        searchSourceBuilder.query(boolQueryBuilder);
        // 分页
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(10);
        return searchSourceBuilder;
    }


    /**
     * 获取查询元
     * @param paramMap 条件map key为es字段 value为字段值
     * @param from 页数
     * @param size 分页大小
     * @return
     */
    public SearchSourceBuilder getSearchSourceBuilder(HashMap<String, Object> paramMap, int from, int size) {
        // 创建查询请求
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 创建多条件查询元
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        // 加入条件
        paramMap.forEach((k, v) -> {
            boolQueryBuilder.must(new TermQueryBuilder(k, v));
        });
        // 装载多条件查询
        searchSourceBuilder.query(boolQueryBuilder);
        // 分页
        searchSourceBuilder.from((from - 1) * size);
        searchSourceBuilder.size(size);
        return searchSourceBuilder;
    }


    /**
     * 根据响应结果获取对应集合
     * @param searchResponse 响应结果
     * @param cls 类
     * @param fieldName 高亮字段名
     * @return
     * @param <T>
     */
    public <T> List<T> getSearchResponseList(SearchResponse searchResponse, Class<T> cls, String... fieldName) {
        return Arrays.stream(searchResponse.getHits().getHits()).map(data -> {
            String dataSourceAsString = data.getSourceAsString();
            try {
                T obj = objectMapper.readValue(dataSourceAsString, cls);
//                T obj = JSON.parseObject(dataSourceAsString, cls);
                // 针对高亮字段进行处理
                Map<String, HighlightField> highlightFields = data.getHighlightFields();
                if (!highlightFields.isEmpty()) {
                    StringBuffer sb = new StringBuffer();
                    Arrays.stream(fieldName).forEach(field -> {
                        HighlightField highlightField = highlightFields.get(field);
                        for (Text fragment : highlightField.fragments()) {
                            sb.append(fragment);
                        }
                        ReflectUtil.setFieldValue(obj, field, sb.toString());
                    });
                }
                return obj;
            } catch (Exception e) {
                logger.warn("es解析对象字符串出错！对应字符串：[{}]", dataSourceAsString, e);
                return null;
            }
        }).collect(Collectors.toList());
    }


    /**
     * 判断索引在es中是否存在
     * @param indexName
     * @return
     */
    public boolean hasIndex(String indexName) {
        if (StrUtil.isBlank(indexName)) {
            return false;
        }
        GetIndexRequest request = new GetIndexRequest(indexName);
        try {
            boolean exists = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
            return exists;
        } catch (IOException e) {
            logger.warn("判断es索引[{}]是否存在出现错误！", indexName, e);
        }
        return false;
    }


    /**
     * 删除索引
     * @param indexName
     * @return
     */
    public void delIndex(String indexName) {
        if (StrUtil.isBlank(indexName)) {
            return;
        }
        DeleteIndexRequest request = new DeleteIndexRequest(indexName);
        try {
            restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            logger.warn("删除es索引[{}]出现错误！", indexName, e);
        }
    }


    /**
     * 新增索引
     * @param indexName
     * @return
     */
    public void insertIndex(String indexName,Class<?> cls) {
        if (StrUtil.isBlank(indexName)) {
            return;
        }
        boolean hasIndex = this.hasIndex(indexName);
        if (hasIndex) {
            logger.warn("索引[{}]已存在！", indexName);
            return;
        }
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        XContentBuilder mapping = null;
        try {
            mapping = XContentFactory.jsonBuilder();
            mapping.startObject()
                    .startObject("properties");

            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                String fieldType = this.getElasticsearchType(field.getType());

                if (fieldType != null) {
                    // 创建字段映射
                    mapping.startObject(fieldName)
                            .field("type", fieldType);

                    // 如果字段类型是 text，添加一个子字段 "keyword" 用于精确匹配
                    if ("text".equals(fieldType)) {
                        mapping.startObject("fields")
                                .startObject("keyword")
                                .field("type", "keyword")
                                .endObject()
                                .endObject();
                    }

                    mapping.endObject();
                }
            }
            mapping.endObject()
                    .endObject();

            request.mapping(mapping);
            restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 新增索引并同步list数据到索引内
     * @param indexName
     * @return
     */
    public <T> boolean insertIndexAndSaveData(String indexName, List<T> list) {
        if (list.isEmpty()) {
            logger.error("创建索引并新增数据失败！集合内必须包含至少1条数据！");
            return false;
        }
        // 创建索引
        this.insertIndex(indexName,list.get(0).getClass());

        // 加入数据
        for (int i = 0; i < list.size(); i += MAX_COUNT) {
            int fromIndex = i;
            int toIndex = Math.min(i + MAX_COUNT, list.size());

            List<T> batch = list.subList(fromIndex, toIndex);
            BulkRequest bulkRequest = new BulkRequest();
            for (T t : batch) {
                try {
                    String jsonDocument = objectMapper.writeValueAsString(t);
                    // 创建 IndexRequest，设置索引名称和 JSON 文档
                    IndexRequest indexRequest = new IndexRequest(indexName)
                            .source(jsonDocument, XContentType.JSON);
                    bulkRequest.add(indexRequest);
                } catch (JsonProcessingException e) {
                    logger.error("对象json序列化发生错误！", e);
                    return false;
                }
            }
            try {
                // 使用 Elasticsearch 客户端执行批量插入请求
                restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            } catch (IOException e) {
                logger.error("批量插入数据到es发生错误！", e);
                return false;
            }
        }
        return true;
    }


    /**
     * 字段类型映射
     * @param fieldType
     * @return
     */
    private String getElasticsearchType(Class<?> fieldType) {
        HashMap<Class<?>, String> typeMappings = new HashMap<Class<?>, String>(){{
            put(String.class, "text");
            put(int.class, "integer");
            put(Integer.class, "integer");
            put(long.class, "long");
            put(Long.class, "long");
            put(double.class, "double");
            put(Double.class, "double");
            put(float.class, "float");
            put(Float.class, "float");
            put(BigDecimal.class, "double");
            put(boolean.class, "boolean");
            put(Boolean.class, "boolean");
            put(Date.class, "date");
        }};
        return typeMappings.get(fieldType) == null ? "text" : typeMappings.get(fieldType);
    }
}
