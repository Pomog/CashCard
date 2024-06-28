package com.pomogblin.cashcard;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class CashCardJsonTest {
    
    @Autowired
    private JacksonTester<CashCard> json;
    
    @Test
    void cashCardSerializationTest() throws IOException {
        CashCard cashCard = new CashCard(99L, 123.45);
        // Verify the entire JSON structure
        assertThat(json.write(cashCard)).isStrictlyEqualToJson("expected.json");
        
        // Verify individual JSON properties
        assertThat(json.write(cashCard)).hasJsonPathNumberValue("@.id");
        assertThat(json.write(cashCard)).extractingJsonPathNumberValue("@.id").isEqualTo(99);
        
        assertThat(json.write(cashCard)).hasJsonPathNumberValue("@.amount");
        assertThat(json.write(cashCard)).extractingJsonPathNumberValue("@.amount").isEqualTo(123.45);
    }
    
    @Test
    void cashCardDeserializationTest() throws IOException {
        String expected = """
                {
                    "id":99,
                    "amount":123.45
                }
                """;
        // Deserialize the JSON string
        CashCard cashCard = json.parseObject(expected);
        
        // Check the deserialized object
        assertThat(cashCard).isEqualTo(new CashCard(99L, 123.45));
        assertThat(cashCard.id()).isEqualTo(99L);
        assertThat(cashCard.amount()).isEqualTo(123.45);
    }
}
