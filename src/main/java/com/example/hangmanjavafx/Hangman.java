package com.example.hangmanjavafx;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Hangman {
    private List<String> words;
    private String word;
    private String hiddenWord = "";

    private int attempts = 10;


    public String getHiddenWord() {
        return hiddenWord;
    }

    public int getAttempts() {
        return attempts;
    }


    public void startGame() {
        attempts = 10;
        loadListOfWords("src/main/resources/words.csv");
        randomlySelectPassword();
        prepareGameplay();

    }

    private void loadListOfWords(String fileName) {
        words = new ArrayList<>();
        BufferedReader bufferedReader;

        try {
            bufferedReader = new BufferedReader(new FileReader(fileName));
            String line = bufferedReader.readLine();
            while (line != null) {
                line = line.replaceAll("[^a-zA-Z]", " ");
                String[] splitWords = line.split("\\s+");
                for (String w : splitWords) {
                    w = w.toLowerCase();
                    if (w.length() > 6 && !(words.contains(w))) {
                        words.add(w);
                    }
                }
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void randomlySelectPassword() {
        if (!words.isEmpty()) {
            Random random = new Random();
            word = words.get(random.nextInt(words.size()));
        } else {
            throw new IllegalArgumentException("List of words is incorrect.");
        }
    }

    private void prepareGameplay() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            stringBuilder.append("-");
        }
        hiddenWord = stringBuilder.toString();
    }

    public void play(char letter, TextField letterInput, Label hiddenWordLabel, Label attemptsLabel) {
        boolean letterFound = false;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == letter) {
                hiddenWord = hiddenWord.substring(0, i) + letter + hiddenWord.substring(i + 1);
                letterFound = true;
            }
        }
        if (!letterFound) {
            attempts--;
        }
        if (attempts == 0 || !hiddenWord.contains("-")) {
            showTheEnd(letterInput, hiddenWordLabel,attemptsLabel);
        }
    }
    public void showTheEnd(TextField letterInput, Label hiddenWordLabel, Label attemptsLabel ) {
        boolean isWin = attempts != 0;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Game over");
        alert.setHeaderText(null);
        alert.setContentText(isWin ? "Congratulations! You won!\nDo you want to play again?"
                : "Game Over. You lost.\nDo you want to play again?");

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == buttonTypeYes) {
                startGame();
                updateWindow(hiddenWordLabel,attemptsLabel);
            } else if (buttonType == buttonTypeNo) {
                Stage stage = (Stage) letterInput.getScene().getWindow();
                stage.close();
            }
        });
    }
    public void updateWindow(Label hiddenWordLabel, Label attemptsLabel) {
        hiddenWordLabel.setText("Hidden word: " + getHiddenWord());
        attemptsLabel.setText("Attempts left: " + getAttempts());
    }

}

