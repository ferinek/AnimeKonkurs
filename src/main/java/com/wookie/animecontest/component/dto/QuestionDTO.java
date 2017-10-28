package com.wookie.animecontest.component.dto;

import com.wookie.animecontest.config.PointDifficulty;

public class QuestionDTO {

    private String question;
    private String pathToImage;
    private PointDifficulty pointReward;
    private String[] answers;
    private int correctAnswerIndex;

    public QuestionDTO() {
        answers = new String[4];
    }

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public void setCorrectAnswerIndex(int correctAnswerIndex) {
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getPathToImage() {
        return pathToImage;
    }

    public void setPathToImage(String pathToImage) {
        this.pathToImage = pathToImage;
    }

    public PointDifficulty getPointReward() {
        return pointReward;
    }

    public void setPointReward(PointDifficulty pointReward) {
        this.pointReward = pointReward;
    }
}
