package com.wookie.animecontest.service;

import com.google.common.io.Resources;
import com.wookie.animecontest.component.dto.QuestionDTO;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

@Component
public class QuestionService {

    private List<QuestionDTO> list;

    public QuestionService() {
        parseQuestionsFromFile();
//        parseQuestions();
    }

    private void parseQuestionsFromFile() {
        list = new LinkedList<>();
        String allQuestionsOnce = getAllQuestions();
        String[] allQuestions = allQuestionsOnce.split("\n\n");
        for (String unparserQuestion : allQuestions) {
            QuestionDTO dto = parseQuestion(unparserQuestion);
            list.add(dto);
        }
    }

    private QuestionDTO parseQuestion(String unparserQuestion) {
        QuestionDTO dto = new QuestionDTO();
        String[] lines = unparserQuestion.split("\n");
        dto.setQuestion(lines[1]);
        return dto;
    }


    public List<QuestionDTO> getQuestions() {
        return list;
    }

    private String getAllQuestions() {
        URL url = Resources.getResource("questions.txt");
        String text = null;
        try {
            text = new String(Resources.toByteArray(url), "Cp1250");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
}
