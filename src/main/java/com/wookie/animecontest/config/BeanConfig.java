package com.wookie.animecontest.config;

import com.wookie.animecontest.controller.MainController;
import com.wookie.animecontest.controller.QuestionController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class BeanConfig {
    private static final String QUESTION_WINDOW_CREATION_ERROR = "Couldn't create QuestionController bean: ";
    private static final String MAIN_WINDOW_CREATION_ERROR = "Couldn't create MainWindowController bean: ";
    private static final String VIEW_NEW_GAME_WINDOW_FXML = "/view/Question.fxml";
    private static final String VIEW_MAIN_WINDOW_FXML = "/view/MainWindow.fxml";
    private static final Logger LOGGER = Logger.getLogger(BeanConfig.class);

    @Bean
    public MainController getMainController() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource(VIEW_MAIN_WINDOW_FXML));
        try {
            BorderPane rootLayout = (BorderPane) loader.load();
            MainController controller = loader.getController();
            controller.setRootLayout(rootLayout);
            return controller;
        } catch (IOException e) {
            LOGGER.error(MAIN_WINDOW_CREATION_ERROR, e);
        }

        return null;
    }

    @Bean
    public QuestionController getQuestionController() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource(VIEW_NEW_GAME_WINDOW_FXML));
        try {
            GridPane rootLayout =  loader.load();
            QuestionController controller = loader.getController();
            controller.setRootLayout(rootLayout);
            return controller;
        } catch (IOException e) {
            LOGGER.error(QUESTION_WINDOW_CREATION_ERROR, e);

        }
        return null;
    }


}
