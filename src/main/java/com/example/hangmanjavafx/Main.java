package com.example.hangmanjavafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) {
        Hangman hangman = new Hangman();
        hangman.startGame();

        TextField letterInput = new TextField();
        Button guessButton = new Button("Guess");
        Label hiddenWordLabel = new Label();
        Label attemptsLabel = new Label();
        Label stateLetter = new Label("State the letter");

        VBox window = new VBox(20);
        window.getChildren().addAll(hiddenWordLabel, attemptsLabel, stateLetter, letterInput, guessButton);
        window.setMinWidth(200);
        Scene scene = new Scene(window, 300, 200);

        stage.setScene(scene);
        stage.setTitle("Hangman");
        stage.show();

        guessButton.setOnAction(event -> {
            String input = letterInput.getText().trim();
            if(input.isEmpty()){
                input = " ";
            }
            char letter = input.charAt(0);
            if (Character.isLetter(letter)) {
                hangman.play(letter, letterInput, hiddenWordLabel, attemptsLabel);
                hangman.updateWindow(hiddenWordLabel,attemptsLabel);
                letterInput.clear();
            }
        });
        hangman.updateWindow(hiddenWordLabel,attemptsLabel);
    }
}