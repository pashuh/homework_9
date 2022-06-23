import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


import static org.assertj.core.api.Assertions.assertThat;

public class JsonTest extends FilesTest {

    @Test
    void jsonTest() throws IOException {
        InputStream is = classLoader.getResourceAsStream("jsonFile.json");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(new InputStreamReader(is));
        assertThat(jsonNode.findValue("auto").findValue("AUDI")
                        .findValue("RS6").get("power").asText()).isEqualTo("610 hp");
        assertThat(jsonNode.findValue("auto").findValue("AUDI")
                        .findValue("A4").get("Rear-wheel drive").asBoolean()).isEqualTo(false);
        assertThat(jsonNode.findValue("auto").findValue("BMW")
                        .findValue("M4").get("vin").asInt()).isEqualTo(77338912);
    }
}

