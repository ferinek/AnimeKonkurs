package com.wookie.animecontest.config;

import com.wookie.animecontest.controller.MainController;
import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.wookie.animecontest")
public class Main extends Application {
    private static ConfigurableApplicationContext run;
    private Stage primaryStage;
    @Override
    public void start(Stage pStage) throws Exception {
        this.primaryStage=pStage;
        MainController bean = run.getBean(MainController.class);
        BorderPane rootLayout = bean.getRootLayout();
        primaryStage.setTitle("Anime Konkurs, program written by: Wookie");
        primaryStage.setScene(new Scene(rootLayout));
        primaryStage.setMaximized(true);
        primaryStage.show();
        bean.setup();
    }


    public static void main(String[] args) {
        JFXPanel fxPanel = new JFXPanel();
        run = SpringApplication.run(Main.class, args);
        launch(args);
        run.close();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
