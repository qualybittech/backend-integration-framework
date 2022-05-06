package org.backendtestframework.util;

import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.Map;

public class CompareTwoJsons {

    public static void main(String[] args) {
        JsonElement mongoDBResponse =getJson("src\\test\\resources\\dbresponse.json");
        JsonElement apiResponse =getJson("src\\test\\resources\\apiresponse.json");

        //Convert the required nodes for processing 
        JsonElement name = mongoDBResponse.getAsJsonObject().getAsJsonArray("tfGroups").get(0).getAsJsonObject().get("_id");       
        mongoDBResponse.getAsJsonObject().getAsJsonArray("tfGroups").get(0).getAsJsonObject().add("name", name);
        
        Gson g = new Gson();
        Type mapType = new TypeToken<Map<String, Object>>() {
        }.getType();
        Map<String, Object> leftMap = FlatMapUtil.flatten(g.fromJson(mongoDBResponse, mapType));
        Map<String, Object> rightMap = FlatMapUtil.flatten(g.fromJson(apiResponse, mapType));											
        MapDifference<String, Object> difference = Maps.difference(leftMap, rightMap);

        
        System.out.println("\n\nEntries are missing in Json2\n--------------------------\n");
        difference.entriesOnlyOnLeft().forEach((key, value) -> System.out.println("\n--> " + key + ": " + value));

        System.out.println("\n\nEntries are missing in Json1\n--------------------------\n");
        difference.entriesOnlyOnRight().forEach((key, value) -> System.out.println("\n--> " + key + ": " + value));
       
        System.out.println("\n\nEntries Mismatching:\n--------------------------\n");
        difference.entriesDiffering().forEach((key, value) -> System.out.println("\n--> " + key + ": " + value));
        System.out.println(difference.entriesDiffering().values().size());
        
        System.out.println("\n\nEntries Common:\n--------------------------\n");
        difference.entriesInCommon().forEach((key, value) -> System.out.println("\n--> " + key + ": " + value));
    }
    public static JsonElement getJson(String filePath) {
        JsonElement jsonElement=null;
        try {
            FileReader fileReader = new FileReader(filePath);
            jsonElement = JsonParser.parseReader(fileReader);
            
        } catch (Exception e) {
            System.out.println("File not found :"+filePath);
            e.printStackTrace();
        }
        return jsonElement;
    }
}
