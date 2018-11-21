import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

import static org.junit.Assert.*;


public class MainClassTest {

    Multimap<String, String> map;

    @Before
    public void setUp() throws IOException {
        InputStream inputStream = MainClass.class.getResourceAsStream("SystemViewController.json");
        Reader reader = new InputStreamReader(inputStream);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(reader);

        map = ArrayListMultimap.create();
        MainClass.addKeys("", root, map);

    }


    @Test
    public void InputCount() {

        int counter=0;

        for(Map.Entry<String, String> entry: map.entries()) {
            if(entry.getValue().equals("Input")) {
                counter++;
            }
        }
        assertEquals(26, counter);
    }

}