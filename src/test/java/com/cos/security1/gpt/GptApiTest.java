package com.cos.security1.gpt;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
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
import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class GptApiTest {
    private static String API_KEY;
    private static String MODEL = "text-davinci-003";
    private static String END_POINT = "https://api.openai.com/v1/completions";
    private static String SYSTEM_TASK_MESSAGE = "Please classify the previous text into one of the emotions, which are happiness, fear, anger, sadness, depression, excited, impressed.";

    private static String FILE_PROTOCOL = "file:/";
    private static String ROOT_PATH_PROPERTY = "user.dir";
    private static String PROPERTIES_DIR = "/src/config/apikey.properties";


    private static int MAX_TOKENS = 1000;
    private static int N = 1;
    private static String STOP = null;
    private static double TEMPERATURE = 0.1;

    private static String SAMPLE_USER_PROMPT = "취업 준비 쉽지 않아. 할 일이 정말 많거든";

    @BeforeEach
    void getApiKey() throws IOException {
        StringBuilder filePath = new StringBuilder()
                .append(FILE_PROTOCOL)
                .append(System.getProperty(ROOT_PATH_PROPERTY))
                .append(PROPERTIES_DIR);
        URL propURL = new URL(filePath.toString());

        Properties properties = new Properties();
        properties.load(propURL.openStream());

        API_KEY = properties.getProperty("gptKey");
    }

    
    @Test
    @DisplayName("전체 Response Text 불러오기")
    public void getFeelingFromText() {
        String endPoint = END_POINT;
        String model = MODEL;
        String prompt = SAMPLE_USER_PROMPT + SYSTEM_TASK_MESSAGE;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(API_KEY);

        String requestBody = createRequestBody(MODEL, prompt, MAX_TOKENS, N, STOP, TEMPERATURE);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);


        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.postForObject(endPoint, requestEntity, String.class);

        System.out.println("response = " + response);
    }
    private static String createRequestBody(String model, String prompt, int maxTokens, int n, String stop, double temperature) {
        return String.format("{\"model\":\"%s\",\"prompt\":\"%s\",\"max_tokens\":%d,\"n\":%d,\"stop\":%s,\"temperature\":%f}",
                model, prompt, maxTokens, n, stop, temperature);
    }

    @Test
    @DisplayName("Choices의 text만 결과값 가져오기")
    public void getFeeling() throws IOException, JSONException {
        HttpURLConnection con = (HttpURLConnection) new URL(END_POINT).openConnection();
        String bearerToken = "Bearer " + API_KEY;

        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", bearerToken);

        String prompt = SAMPLE_USER_PROMPT + SYSTEM_TASK_MESSAGE;

        JSONObject data = new JSONObject();
        data.put("model", MODEL);
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
    @DisplayName("OpenAiService 사용하여 chat에 사용되는 turbo 모델 결과 응답 가져오기")
    public void getFeelingFromDiary() {
        OpenAiService openAiService = new OpenAiService(API_KEY, Duration.ofMinutes(1L));

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model("gpt-3.5-turbo")
                .temperature(0.1)
                .messages(
                        List.of(
                                new ChatMessage("user", SAMPLE_USER_PROMPT),
                                new ChatMessage("system", SYSTEM_TASK_MESSAGE)

                        )
                ).build();

        StringBuilder builder = new StringBuilder();
        openAiService.createChatCompletion(chatCompletionRequest)
                .getChoices().forEach(choice -> {
                    builder.append(choice.getMessage().getContent());
                });

        String jsonResponse = builder.toString();
        System.out.println("jsonResponse = " + jsonResponse);

    }
}
