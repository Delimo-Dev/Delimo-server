package com.cos.delimo.service.impl;

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
    private static final String MODEL_DAVINCI = "text-davinci-003";
    private static final String SYSTEM_TASK_MESSAGE = null;
    private static final String FILE_PROTOCOL = "file:/";
    private static final String ROOT_PATH_PROPERTY = "user.dir";
    private static final String PROPERTIES_DIR = "/src/config/apikey.properties";

    private static final double TEMPERATURE = 0.1;

    /**
     * properties 파일에 저장한 GPT API KEY와 SYSTEM TASK MESSAGE (prompt) 를 가져옵니다.
     * @throws IOException
     */
    private static void getApiKeyAndTaskMessage() throws IOException {
        String filePath = FILE_PROTOCOL +
                System.getProperty(ROOT_PATH_PROPERTY) +
                PROPERTIES_DIR;
        URL propURL = new URL(filePath);

        Properties properties = new Properties();
        properties.load(propURL.openStream());

        API_KEY = properties.getProperty("gptKey");
        SYSTEM_TASK_MESSAGE = properties.getProperty("prompt");
    }

    @Autowired
    public SentimentRecognitionServiceImpl(DiarySentimentRepository diarySentimentRepository) throws IOException {
        getApiKeyAndTaskMessage();
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
