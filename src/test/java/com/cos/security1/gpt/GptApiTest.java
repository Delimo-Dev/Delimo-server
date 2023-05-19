package com.cos.security1.gpt;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class GptApiTest {
    private static String API_KEY;
    private final static String MODEL_DAVINCI = "text-davinci-003";
    private final static String CHAT_MODEL_TURBO = "gpt-3.5-turbo";
    private final static String END_POINT = "https://api.openai.com/v1/completions";
    private final static String SYSTEM_TASK_MESSAGE = "Which of the following words best describes the previous sentence? Give the result only as the integer number."
            + "unknown(0), happiness(1), embarrassment(2), anger(3), fear(4)"
            + "sadness(5), hurt(6), achievement(7)";

    private final static String FILE_PROTOCOL = "file:/";
    private final static String ROOT_PATH_PROPERTY = "user.dir";
    private final static String PROPERTIES_DIR = "/src/config/apikey.properties";


    private final static int MAX_TOKENS = 1000;
    private final static int N = 1;
    private final static String STOP = null;
    private final static double TEMPERATURE = 0.1;

    private final static String SAMPLE_USER_PROMPT = "I was able to achieve my dream with my previous 10 years of work.";

    @BeforeEach
    void getApiKey() throws IOException {
        String filePath = FILE_PROTOCOL +
                System.getProperty(ROOT_PATH_PROPERTY) +
                PROPERTIES_DIR;
        URL propURL = new URL(filePath);

        Properties properties = new Properties();
        properties.load(propURL.openStream());

        API_KEY = properties.getProperty("gptKey");
    }

    
    @Test
    @DisplayName("전체 Response Text 불러오기")
    public void getEmotionFromText() {
        String prompt = SAMPLE_USER_PROMPT + SYSTEM_TASK_MESSAGE;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(API_KEY);

        String requestBody = createRequestBody(prompt);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);


        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.postForObject(END_POINT, requestEntity, String.class);

        System.out.println("response = " + response);
    }
    private static String createRequestBody(String prompt) {
        return String.format("{\"model\":\"%s\",\"prompt\":\"%s\",\"max_tokens\":%d,\"n\":%d,\"stop\":%s,\"temperature\":%f}",
                GptApiTest.MODEL_DAVINCI, prompt, GptApiTest.MAX_TOKENS, GptApiTest.N, GptApiTest.STOP, GptApiTest.TEMPERATURE);
    }

    @Test
    @DisplayName("Choices의 text만 결과값 가져오기")
    public void getEmotion() throws IOException, JSONException {
        HttpURLConnection con = (HttpURLConnection) new URL(END_POINT).openConnection();
        String bearerToken = "Bearer " + API_KEY;

        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", bearerToken);

        String prompt = SAMPLE_USER_PROMPT + SYSTEM_TASK_MESSAGE;

        JSONObject data = new JSONObject();
        data.put("model", MODEL_DAVINCI);
        data.put("prompt", prompt);
        data.put("max_tokens", MAX_TOKENS);
        data.put("temperature", TEMPERATURE);

        con.setDoOutput(true);
        con.getOutputStream().write(data.toString().getBytes());

        String output = new BufferedReader(new InputStreamReader(con.getInputStream())).lines()
                .reduce((a, b) -> a + b).get();

        System.out.println(new JSONObject(output).getJSONArray("choices").getJSONObject(0).getString("text"));
    }

    @Test
    @DisplayName("OpenAiService 사용하여 completion response에 사용되는 davinci-model의 요청 결과 응답 가져오기")
    public void getEmotionFromDiary() {
        OpenAiService openAiService = new OpenAiService(API_KEY);

        CompletionRequest completionRequest = CompletionRequest
                .builder()
                .prompt(SAMPLE_USER_PROMPT + SYSTEM_TASK_MESSAGE)
                .model(MODEL_DAVINCI)
                .temperature(0.1)
                .build();

        StringBuilder builder = new StringBuilder();
        openAiService.createCompletion(completionRequest)
                .getChoices().forEach(choice -> builder.append(choice.getText()));

        String jsonResponse = builder.toString();
        System.out.println("jsonResponse = " + jsonResponse.substring(2));

    }
}
