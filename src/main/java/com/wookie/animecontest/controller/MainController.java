package com.wookie.animecontest.controller;

import com.wookie.animecontest.component.OrderedQuestionPane;
import com.wookie.animecontest.component.PlayerPointsListView;
import com.wookie.animecontest.component.dto.PlayerDTO;
import com.wookie.animecontest.component.dto.QuestionDTO;
import com.wookie.animecontest.config.Main;
import com.wookie.animecontest.service.QuestionReadingService;
import com.wookie.animecontest.util.StyleUtils;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class MainController {
    @FXML
    private HBox mainHBox;
    @Autowired
    private Main main;
    @Autowired
    private QuestionReadingService questionReadingService;
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
        initializeDebug();
    }

    private void initializeDebug() {
        KeyCombination ctrlo = KeyCombination.keyCombination("Ctrl+o");
        KeyCombination ctrlp = KeyCombination.keyCombination("Ctrl+p");
        KeyCombination ctrlk = KeyCombination.keyCombination("Ctrl+k");
        KeyCombination ctrll = KeyCombination.keyCombination("Ctrl+l");

        rootLayout.setOnKeyPressed(event -> {
            if (ctrlo.match(event)) {
                players.addPoints(actualPlayer, 1);
            }
            if (ctrlp.match(event)) {
                players.addPoints(actualPlayer, -1);
            }
            if (ctrlk.match(event)) {
                actualPlayer = players.getNextPlayer();
            }
            if (ctrll.match(event)) {
                actualPlayer = players.getPreviousPlayer();
            }
        });
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
        QuestionDTO questionDTO = questionReadingService.getQuestions().get(10 * j + i);
        buttons[i][j] = new OrderedQuestionPane(i, j, questionDTO);
        Label label = new Label((10 * (j) + (i + 1)) + "");
        label.setTextFill(Color.WHITE);
        StackPane.setAlignment(label, Pos.CENTER);
        buttons[i][j].getChildren().add(label);
        buttons[i][j].setStyle(new StyleUtils().addBackgroundColor(questionDTO.getPointReward().getColor()).build());
        buttons[i][j].setOnMouseClicked(event -> handleQuestionClick((OrderedQuestionPane) event.getSource()));
        pane.add(buttons[i][j], i, j);
    }

    private void handleQuestionClick(OrderedQuestionPane source) {
        if (startState && source.isEnabled()) {
            System.out.println(String.format("Player: %s", actualPlayer.getName()));
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
        pane.getRowConstraints().add(new RowConstraints());
        RowConstraints rc = new RowConstraints();
        rc.setVgrow(Priority.ALWAYS);
        rc.setFillHeight(true);

        pane.getRowConstraints().add(rc);

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
