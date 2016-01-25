package com.sung.es;

import com.sung.es.java.example01.Medicine;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;

/**
 * Created by sungang on 2016/1/21.11:36
 */
public class JsonUtil {


    public static String obj2JsonData(Medicine medicine) {
        String jsonData = null;
        try {
            //使用XContentBuilder创建json数据
            XContentBuilder jsonBuild = XContentFactory.jsonBuilder();
            jsonBuild.startObject()
                    .field("id", medicine.getId())
                    .field("name", medicine.getName())
                    .field("funciton", medicine.getFunction())
                    .endObject();
            jsonData = jsonBuild.string();
            System.out.println(jsonData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonData;
    }
}
