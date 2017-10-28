package com.wookie.animecontest.controller;

import com.wookie.animecontest.component.OrderedQuestionPane;
import com.wookie.animecontest.component.PlayerPointsListView;
import com.wookie.animecontest.component.dto.PlayerDTO;
import com.wookie.animecontest.component.dto.QuestionDTO;
import com.wookie.animecontest.config.Main;
import com.wookie.animecontest.service.QuestionService;
import com.wookie.animecontest.util.StyleUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class MainController {
    @FXML
    private HBox mainHBox;
    @Autowired
    private Main main;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionController questionController;

    private BorderPane rootLayout;
    private Button addPlayer;
    private PlayerPointsListView players;
    private OrderedQuestionPane[][] buttons;
    private static final int COLUMN_COUNT = 10;
    private static final int ROW_COUNT = 10;
    private Button start;
    private boolean startState = false;
    private PlayerDTO actualPlayer;
    private OrderedQuestionPane actualQuestion;

    public MainController() {
        players = new PlayerPointsListView();
        buttons = new OrderedQuestionPane[COLUMN_COUNT][ROW_COUNT];
    }

    public void setup() {
        GridPane players = initializePlayerList();
        GridPane mainBoard = initializeMainBoard();
        rootLayout.setLeft(players);
        rootLayout.setBottom(null);
        mainHBox.getChildren().addAll(mainBoard);


    }

    private GridPane initializeMainBoard() {
        GridPane pane = new GridPane();
        pane.setHgap(5);
        pane.setVgap(5);
        for (int i = 0; i < COLUMN_COUNT; i++) {
            for (int j = 0; j < ROW_COUNT; j++) {
                preparePane(i, j, pane);
            }
        }
        return pane;
    }

    private void preparePane(int i, int j, GridPane pane) {
        QuestionDTO questionDTO = questionService.getQuestions().get(10 * j + i);
        buttons[i][j] = new OrderedQuestionPane(i, j, questionDTO);
        buttons[i][j].getChildren().add(new Label((10 * (j) + (i + 1)) + ""));
        buttons[i][j].setStyle(new StyleUtils().addBackgroundColor(questionDTO.getPointReward().getColor()).build());
        buttons[i][j].setOnMouseClicked(event -> handleQuestionClick((OrderedQuestionPane) event.getSource()));
        pane.add(buttons[i][j], i, j);
    }

    private void handleQuestionClick(OrderedQuestionPane source) {
        if (startState && source.isEnabled()) {
            actualQuestion = source;
            questionController.show(source);
        }
    }

    private GridPane initializePlayerList() {
        GridPane pane = new GridPane();
        addPlayer = new Button("Dodaj Gracza");
        addPlayer.setOnAction(event -> addPlayer());
        start = new Button("Start");
        start.setOnAction(event -> start());
        start.setDisable(true);

        pane.add(addPlayer, 0, 0);
        pane.add(start, 1, 0);
        pane.add(players.getList(), 0, 1, 2, 1);

        return pane;
    }

    private void start() {
        addPlayer.setDisable(true);
        start.setDisable(true);
        startState = true;
        actualPlayer = players.getFirstPlayer();
    }

    private void addPlayer() {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Dodaj gracza");
        dialog.setHeaderText("Podaj imię gracza");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            players.addPlayer(name);
            start.setDisable(false);
        });
    }

    public void showAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(main.getPrimaryStage());
        alert.setTitle("About");
        alert.setHeaderText("Author: Krzysztof Slodowicz");
        alert.setContentText("If you find any bugs, please, contact me: kslodowicz@o2.pl.");
        alert.showAndWait();
    }

    public BorderPane getRootLayout() {
        return rootLayout;
    }

    public void setRootLayout(BorderPane rootLayout) {
        this.rootLayout = rootLayout;
    }

    public void update(boolean answer) {
        if (answer) {
            players.addPoints(actualPlayer, actualQuestion.getQuestion().getPointReward().getPoints());
        }
        actualPlayer = players.getNextPlayer();
        actualQuestion.setEnabled(false);
        actualQuestion.setStyle(new StyleUtils().addBackgroundColor("black").build());

    }
}
