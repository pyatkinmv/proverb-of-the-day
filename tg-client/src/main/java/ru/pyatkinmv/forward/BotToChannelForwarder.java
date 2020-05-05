package ru.pyatkinmv.forward;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.pyatkinmv.parse.JsonProverbParser;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
@Slf4j
public class BotToChannelForwarder {
    private static final String URI_FORMAT = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s&parse_mode=html";

    private static final RestTemplate restTemplate = new RestTemplate();
    private static final String SUPPLY_PATH = "/supply";

    @Value("${telegram.bot.token}")
    private String token;

    @Value("${telegram.channel.name}")
    private String channelName;

    @Value("${services.endpoints.proverb-service}")
    private String proverbLibUri;

    @SneakyThrows
    private static String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
    }

    private String getProverbMessage() {
        val response = restTemplate.getForEntity(URI.create(proverbLibUri + SUPPLY_PATH), String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            val errorMessage = String.format("Proverb request failed with status [%s]", response.getStatusCode());
            log.error(errorMessage);

            throw new IllegalStateException(errorMessage);
        }

        val jsonProverb = response.getBody();

        return JsonProverbParser.parse(jsonProverb);
    }

    @Scheduled(cron = "${cron.expression}")
    public void forward() {
        val message = getProverbMessage();
        val encoded = encode(message);

        val strUri = String.format(URI_FORMAT, token, channelName, encoded);
        val uri = URI.create(strUri);

        log.info("Sending the message [{}]", message);

        restTemplate.getForEntity(uri, String.class);
    }
}
