package com.ps.qa.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ps.qa.domain.QasysAnswerT2;
import com.ps.qa.domain.QasysQuestionT2;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.MapSolrParams;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 搜索引擎
 * @author:QL
 * @create：2019/07/23
 */
@RestController
public class Sou {

    /**
     * 模糊查询
     *
     * @throws IOException
     * @throws SolrServerException
     */
    @RequestMapping("/querys")
    public List query(@RequestParam("type") String type, @RequestParam("message") String message) throws SolrServerException, IOException {
        // 连接solr服务器
        HttpSolrClient client = new HttpSolrClient.Builder("http://127.0.0.1:8983/solr").build();
        // 准备参数
        Map<String, String> params = new HashMap<>();
        params.put("q", type + ":" + message);
        // 执行查询
        QueryResponse query = client.query("qa", new MapSolrParams(params));
        // 取查询结果
        SolrDocumentList documentList = query.getResults();
        Gson gson = new Gson();
        String solrString = gson.toJson(documentList);
        if (type.equals("question")) {
            List<QasysQuestionT2> solrQuestions = gson.fromJson(solrString, new TypeToken<List<QasysQuestionT2>>() {
            }.getType());
            return solrQuestions;
        } else if (type.equals("answer")) {
            List<QasysAnswerT2> solrAnswers = gson.fromJson(solrString, new TypeToken<List<QasysAnswerT2>>() {
            }.getType());
            return solrAnswers;
        }
        return null;
    }
}
