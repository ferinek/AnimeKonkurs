package com.wookie.animecontest.component;

import com.wookie.animecontest.component.dto.QuestionDTO;
import javafx.scene.layout.StackPane;

public class OrderedQuestionPane extends StackPane {

    private int row;
    private int column;
    private QuestionDTO question;
    private boolean enabled;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public OrderedQuestionPane() {
        super();
    }

    public OrderedQuestionPane(int row, int column, QuestionDTO question) {
        super();
        this.row = row;
        this.column = column;
        this.question = question;
        enabled = true;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public QuestionDTO getQuestion() {
        return question;
    }

    public void setQuestion(QuestionDTO question) {
        this.question = question;
    }
}
