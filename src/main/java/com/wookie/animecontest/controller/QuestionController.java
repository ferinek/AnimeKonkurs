package com.wookie.animecontest.controller;

import com.google.common.io.Resources;
import com.wookie.animecontest.component.OrderedQuestionPane;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;

public class QuestionController {
    private Stage stage;

    @Autowired
    private MainController mainController;
    @FXML
    private Label text;
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
        setImage(pane.getQuestion().getPathToImage());
        for (int i = 0; i < answers.length; i++) {
            answers[i].setText(pane.getQuestion().getAnswers()[i]);
        }
        for (AnchorPane aPane : panes) {
            aPane.setOnMouseClicked(event -> resolveAnswer((AnchorPane) (event.getSource()), pane));
        }
    }

    private void setImage(String pathToImage) {
        if (pathToImage != null) {
            URL resource = Resources.getResource(String.format("screens/%s.jpg", pathToImage));
            if (resource == null) {
                resource = Resources.getResource(String.format("screens/%s.png", pathToImage));
            }
            image.setImage(new Image(resource.toString()));
        } else {
            image.setImage(new Image(Resources.getResource("screens/0.jpg").toString()));

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
        String bip = "win.mp3";
        Media hit = new Media(Resources.getResource(bip).toString());
        MediaPlayer mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.play();
        stage.hide();
        mainController.update(true);

    }

    public void guessedWrong() {
        String bip = "lose.mp3";
        Media hit = new Media(Resources.getResource(bip).toString());
        MediaPlayer mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.play();
        stage.hide();
        mainController.update(false);
    }
}
