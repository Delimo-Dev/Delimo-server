package com.cos.security1.gpt;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
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
    private static String apiKey;

    @BeforeEach
    void get_api_key() throws IOException {
        String protocol = "file:/";
        String rootPath = System.getProperty("user.dir");
        String propertiesPath = "/src/config/apikey.properties";

        String filePath = protocol + rootPath + propertiesPath;
        URL propURL = new URL(filePath);

        Properties properties = new Properties();
        properties.load(propURL.openStream());

        apiKey = properties.getProperty("gptKey");
    }

    
    @Test
    public void getFeelingFromText() {
        String endPoint = "https://api.openai.com/v1/completions";
        String model = "text-davinci-003";
        String prompt = "요즘 축제 기간이라 신나 Please classify the previous text into one of the emotions, which are happiness, fear, anger, sadness, depression, excited, impressed.";

        int maxTokens = 1000;
        int n = 1;
        String stop = null;
        double temperature = 0.1;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        String requestBody = createRequestBody(model, prompt, maxTokens, n, stop, temperature);
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
    public void getFeeling() throws IOException, JSONException {
        String url = "https://api.openai.com/v1/completions";
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        String bearerToken = "Bearer " + apiKey;

        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", bearerToken);

        String text = "요즘 축제 기간이라 신나 Please classify the previous text into one of the emotions, which are happiness, fear, anger, sadness, depression, excited, impressed.";

        JSONObject data = new JSONObject();
        data.put("model", "text-davinci-003");
        data.put("prompt", text);
        data.put("max_tokens", 4000);
        data.put("temperature", 1.0);

        con.setDoOutput(true);
        con.getOutputStream().write(data.toString().getBytes());

        String output = new BufferedReader(new InputStreamReader(con.getInputStream())).lines()
                .reduce((a, b) -> a + b).get();

        System.out.println(new JSONObject(output).getJSONArray("choices").getJSONObject(0).getString("text"));
    }
}
