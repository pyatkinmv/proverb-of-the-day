package ru.pyatkinmv.parse;

import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JsonProverbParser {
    private static final String TELEGRAM_PROVERB_FORMAT = "**%s**\n\n%s";

    public static String parse(String jsonProverb) {
        val jsonObject = JsonParser.parseString(jsonProverb).getAsJsonObject();

        val text = jsonObject.getAsJsonPrimitive("text").toString();
        val description = jsonObject.getAsJsonPrimitive("description").toString();

        return withoutQuotes(String.format(TELEGRAM_PROVERB_FORMAT, text, description));
    }

    private static String withoutQuotes(String str) {
        return str.replaceAll("\"", "");
    }
}
