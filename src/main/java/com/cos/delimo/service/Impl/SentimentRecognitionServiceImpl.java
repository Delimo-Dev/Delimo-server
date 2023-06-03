package com.cos.delimo.service.Impl;

import com.cos.delimo.domain.DiarySentiment;
import com.cos.delimo.repository.DiarySentimentRepository;
import com.cos.delimo.service.SentimentRecognitionService;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

@Service
public class SentimentRecognitionServiceImpl implements SentimentRecognitionService {
    private final DiarySentimentRepository diarySentimentRepository;

    private static String API_KEY;
    private final static String MODEL_DAVINCI = "text-davinci-003";
    private final static String SYSTEM_TASK_MESSAGE = "Which of the following words best describes the previous sentence? : unknown(0), happiness(1), embarrassment(2), anger(3), fear(4), sadness(5), hurt(6), achievement(7). Give the result as the integer value";
    private final static String FILE_PROTOCOL = "file:/";
    private final static String ROOT_PATH_PROPERTY = "user.dir";
    private final static String PROPERTIES_DIR = "/src/config/apikey.properties";


    private final static int MAX_TOKENS = 1000;
    private final static String STOP = null;
    private final static double TEMPERATURE = 0.1;

    private final static String SAMPLE_USER_PROMPT = "취업 준비 쉽지 않아. 할 일이 정말 많거든";


    private static void getApiKey() throws IOException {
        String filePath = FILE_PROTOCOL +
                System.getProperty(ROOT_PATH_PROPERTY) +
                PROPERTIES_DIR;
        URL propURL = new URL(filePath);

        Properties properties = new Properties();
        properties.load(propURL.openStream());

        API_KEY = properties.getProperty("gptKey");
    }

    @Autowired
    public SentimentRecognitionServiceImpl(DiarySentimentRepository diarySentimentRepository) throws IOException {
        getApiKey();
        this.diarySentimentRepository = diarySentimentRepository;
    }

    @Override
    public DiarySentiment save(DiarySentiment diarySentiment) {
        return diarySentimentRepository.save(diarySentiment);
    }

    @Override
    public int getSentiment(String text) {
        OpenAiService openAiService = new OpenAiService(API_KEY);

        CompletionRequest completionRequest = CompletionRequest
                .builder()
                .prompt(text + SYSTEM_TASK_MESSAGE)
                .model(MODEL_DAVINCI)
                .temperature(TEMPERATURE)
                .build();

        StringBuilder builder = new StringBuilder();
        openAiService.createCompletion(completionRequest)
                .getChoices().forEach(choice -> builder.append(choice.getText()));
        String jsonResponse = builder.toString();

        return Integer.parseInt(jsonResponse.replaceAll("\\D", ""));
    }
}
