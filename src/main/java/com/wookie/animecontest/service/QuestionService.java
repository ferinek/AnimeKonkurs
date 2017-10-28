package com.wookie.animecontest.service;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.wookie.animecontest.component.dto.QuestionDTO;
import com.wookie.animecontest.config.PointDifficulty;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.*;

@Component
public class QuestionService {

    private List<QuestionDTO> list;

    public QuestionService() {
//        parseQuestionsFromFile();
        parseQuestions();
    }

    private void parseQuestionsFromFile() {
        String allQuestionsOnce = getAllQuestions();
        String[] allQuestions = allQuestionsOnce.split("\n\n");
        System.out.println(allQuestionsOnce);
    }

    private void parseQuestions() {
        list = new LinkedList<>();
        Random randomGenerator = new Random();
        for (int i = 0; i < 100; i++) {
            QuestionDTO dto = new QuestionDTO();
            dto.setQuestion(UUID.randomUUID().toString());
            dto.setPointReward(PointDifficulty.getByPoints(randomGenerator.nextInt(3) + 1));
            dto.setPathToImage(randomGenerator.nextBoolean() ? "path" : null);
            for (int j = 0; j < 4; j++) {
                dto.getAnswers()[j] = UUID.randomUUID().toString();
            }
            list.add(dto);
        }
        Collections.shuffle(list);
    }

    public List<QuestionDTO> getQuestions() {
        return list;
    }

    private String getAllQuestions() {
        URL url = Resources.getResource("questions.txt");
        String text = null;
        try {
            text = Resources.toString(url, Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
}
