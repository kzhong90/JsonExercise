import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ValueNode;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.io.*;
import java.util.*;

public class MainClass {
    public static void main(String[] args) throws IOException {
        InputStream inputStream = MainClass.class.getResourceAsStream("SystemViewController.json");
        Reader reader = new InputStreamReader(inputStream);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(reader);

        Multimap<String, String> map = ArrayListMultimap.create();

        addKeys("", root, map);

        String selector = "";
        int counter=0;

        while(!selector.equals("quit")) {
            System.out.println("Please enter selector or enter 'quit' to quit: ");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            selector = bufferedReader.readLine();

            for(Map.Entry<String, String> entry: map.entries()) {
                if(entry.getValue().equals(selector)) {
                    System.out.println(entry.getKey());
                    counter++;
                }
            }
            System.out.println(counter + " total");
            counter=0;
        }
    }

    public static void addKeys(String currentPath, JsonNode jsonNode,
                                Multimap<String, String> map) {

        if(jsonNode.isObject()) {
            ObjectNode objectNode = (ObjectNode) jsonNode;
            Iterator<Map.Entry<String, JsonNode>> iterator = objectNode.fields();
            String pathPrefix = currentPath.isEmpty() ? "" : currentPath + ".";

            while(iterator.hasNext()) {
                Map.Entry<String, JsonNode> entry = iterator.next();
                addKeys(pathPrefix + entry.getKey(), entry.getValue(), map);
            }
        } else if(jsonNode.isArray()) {
            ArrayNode arrayNode = (ArrayNode) jsonNode;

            for(int i=0; i<arrayNode.size(); i++) {
                addKeys(currentPath, arrayNode.get(i), map);
            }
        } else if(jsonNode.isValueNode()) {

            ValueNode valueNode = (ValueNode) jsonNode;
            map.put(currentPath, valueNode.asText());
        }
    }
}
