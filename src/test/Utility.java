/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kaumahat
 */
public class Utility {

    public static List<RoadInfo> getRoads(String filePath){
        List<RoadInfo> roads = new ArrayList<>();
         JsonParser jsonParser = new JsonParser();
         
        try {
            FileReader reader = new FileReader(filePath);

            //Read JSON file
            JsonObject spec = jsonParser.parse(reader).getAsJsonObject();
            String title = spec.get("title").getAsString();
            String version = spec.get("version").getAsString();
            
            JsonArray jsonRoads = spec.get("roads").getAsJsonArray();
            for (JsonElement jsonRoad : jsonRoads) {
                JsonObject jsonRoadObj = jsonRoad.getAsJsonObject();
                RoadInfo road = new RoadInfo();
                road.setName(jsonRoadObj.get("uniqueName").getAsString());
                road.setRequestClassName(jsonRoadObj.get("sampleClassName").getAsJsonObject().get("java").getAsString());
                road.setResponseClassName(jsonRoadObj.get("responseClassName").getAsJsonObject().get("java").getAsString());
                road.setHttpStatus(jsonRoadObj.get("assertions").getAsJsonObject().get("httpStatus").getAsString());
                
                JsonArray jsonExpectedValues = jsonRoadObj.get("assertions").getAsJsonObject().getAsJsonArray("expectedValues");
                Map<String,String> expectedValues = new HashMap<>();
                for (JsonElement jsonExpectedValue : jsonExpectedValues) {
                    expectedValues.put(jsonExpectedValue.getAsJsonObject().get("field").getAsString(), 
                            jsonExpectedValue.getAsJsonObject().get("value").getAsString());
                }
                road.setExpectedValues(expectedValues);
                
                JsonArray jsonRequiredFields = jsonRoadObj.get("assertions").getAsJsonObject().getAsJsonArray("requiredFields");
                List<String> requiredFields = new ArrayList<>();
                for (JsonElement jsonRequiredField : jsonRequiredFields) {
                    requiredFields.add(jsonRequiredField.getAsString());
                }
                road.setRequiredFields(requiredFields);
                
                roads.add(road);
            }
            return roads;
            
//            System.out.println(spec);
//            System.out.println("title: " + title);
//            System.out.println(jsonRoads);

            //Iterate over employee array
            //employeeList.forEach( emp -> parseEmployeeObject( (JsonObject) emp ) );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
