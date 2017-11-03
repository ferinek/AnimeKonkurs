package com.wookie.animecontest.controller;

import com.wookie.animecontest.component.OrderedQuestionPane;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;

public class QuestionController {
    private Stage stage;

    @Autowired
    private MainController mainController;
    @FXML
    private Text text;
    @FXML
    private ImageView image;
    @FXML
    private Label answer1;
    @FXML
    private Label answer2;
    @FXML
    private Label answer3;
    @FXML
    private Label answer4;
    @FXML
    private AnchorPane pane1;
    @FXML
    private AnchorPane pane2;
    @FXML
    private AnchorPane pane3;
    @FXML
    private AnchorPane pane4;

    private Label[] answers;
    private AnchorPane[] panes;
    private GridPane rootLayout;

    public GridPane getRootLayout() {
        return rootLayout;
    }

    public void setRootLayout(GridPane rootLayout) {
        this.rootLayout = rootLayout;
    }

    public void show(OrderedQuestionPane pane) {
        if (stage == null) {
            setStage();
        }
        updateStage(pane);
        stage.show();
    }

    private void updateStage(OrderedQuestionPane pane) {
        text.setText(pane.getQuestion().getQuestion());
        for (int i = 0; i < answers.length; i++) {
            answers[i].setText(pane.getQuestion().getAnswers()[i]);
        }
        for (AnchorPane aPane : panes) {
            aPane.setOnMouseClicked(event -> resolveAnswer((AnchorPane) (event.getSource()), pane));
        }
    }

    private void resolveAnswer(AnchorPane pane, OrderedQuestionPane pane2) {
        String answer = ((Label) pane.getChildren().get(0)).getText();
        if (pane2.getQuestion().getCorrectAnswer().equals(answer)) {
            guessedRight();
        } else {
            guessedWrong();
        }
    }

    private void setStage() {
        stage = new Stage();
        stage.setScene(new Scene(rootLayout));
        stage.setTitle("Pytanie");
        stage.setAlwaysOnTop(false);
        stage.setMaximized(true);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(mainController.getRootLayout().getScene().getWindow());
        text = new Text();
        text.setText("placeholder");
        image = new ImageView();

        answers = new Label[4];
        answers[0] = answer1;
        answers[1] = answer2;
        answers[2] = answer3;
        answers[3] = answer4;

        panes = new AnchorPane[4];
        panes[0] = pane1;
        panes[1] = pane2;
        panes[2] = pane3;
        panes[3] = pane4;

    }

    public void guessedRight() {
        stage.hide();
        mainController.update(true);
    }

    public void guessedWrong() {
        stage.hide();
        mainController.update(false);
    }
}
