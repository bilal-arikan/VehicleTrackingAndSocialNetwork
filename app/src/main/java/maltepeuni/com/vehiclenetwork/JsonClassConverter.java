package maltepeuni.com.vehiclenetwork;

import org.json.JSONException;
import org.json.JSONObject;
import maltepeuni.com.vehiclenetwork.database.DBObject;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LaptopPc on 3.5.2017.
 */

public class JsonClassConverter {
    static ObjectMapper mapper = new ObjectMapper();

    public static JSONObject toJSON(DBObject obj){
        try{
            // Convert object to JSON string
            String jsonInString = mapper.writeValueAsString(obj);
            System.out.println(jsonInString);

            // Convert object to JSON string and pretty print
            //jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(staff);

            JSONObject jObj = null;
            try {
                jObj = new JSONObject(jsonInString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jObj;

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static DBObject toObject(String str,Class cls){
        try {
            //JSON from String to Object
            Object obj = mapper.readValue(str, cls);

            return ((DBObject) obj);
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public static List<DBObject> toArray(String str){
        List<DBObject> arr = new ArrayList<DBObject>();
        try {
            JsonNode arrNode = mapper.readTree(str);
            for (final JsonNode objNode : arrNode) {
                arr.add( toObject(objNode.toString(),DBObject.class) );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arr;
    }
}
