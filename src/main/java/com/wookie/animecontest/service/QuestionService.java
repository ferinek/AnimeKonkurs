package com.wookie.animecontest.service;

import com.google.common.io.Resources;
import com.wookie.animecontest.component.dto.QuestionDTO;
import com.wookie.animecontest.config.PointDifficulty;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
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
        Collections.shuffle(list);
    }

    private QuestionDTO parseQuestion(String unparserQuestion) {
        QuestionDTO dto = new QuestionDTO();
        String[] lines = unparserQuestion.split("\n");

        // parse first line
        String[] split = lines[0].split(" ");
        dto.setPointReward(PointDifficulty.getByPoints(Integer.parseInt(split[2])));

        // parse answers
        dto.setQuestion(lines[1].split("P:")[1].trim());

        String answers = lines[2];
        String[] readyAnswers = new String[4];
        String temp[] = answers.split("D:");
        readyAnswers[3] = temp[1].trim();
        temp = temp[0].split("C:");
        readyAnswers[2] = temp[1].trim();
        temp = temp[0].split("B:");
        readyAnswers[1] = temp[1].trim();
        readyAnswers[0] = temp[0].split("A:")[1].trim();
        dto.setCorrectAnswer(getAnswerByLetter(readyAnswers, split[1]));
        List<String> strings = Arrays.asList(readyAnswers);
        Collections.shuffle(strings);
        dto.setAnswers((String[])strings.toArray());


        if (lines.length > 3) {
            dto.setPathToImage(lines[3]);
        }
        return dto;
    }

    private String getAnswerByLetter(String[] readyAnswers, String letter) {
        switch (letter) {
            case "A":
                return readyAnswers[0].trim();
            case "B":
                return readyAnswers[1].trim();
            case "C":
                return readyAnswers[2].trim();
            case "D":
                return readyAnswers[3].trim();
            default:
                throw new RuntimeException("blebleble");
        }
    }


    public List<QuestionDTO> getQuestions() {
        return list;
    }

    private String getAllQuestions() {
        URL url = Resources.getResource("questions.txt");
        String text = null;
        try {
            text = new String(Resources.toByteArray(url), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
}
