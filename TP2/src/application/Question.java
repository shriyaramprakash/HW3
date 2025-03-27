
package application;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Question {
    public void show(Stage primaryStage) {
        VBox layout = new VBox();
        
        // Ask a question stuff
        Label askLabel = new Label("Ask a Question");
        TextField questionField = new TextField();
        Button askButton = new Button("Ask");
        
        // Search for question stuff
        Label space = new Label("");
        Label searchForQuestions = new Label("Search for a question");
        Button searchButton = new Button("Search");
        
        // Label to check if question submitted is empty or not
        Label success = new Label();
        success.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
        
        // Button to add question 
        askButton.setOnAction(a -> {
            String question = questionField.getText(); // Store question typed
            success.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
            if (!question.trim().isEmpty()) { // If not empty store question and print success
                QuestionList.addQuestion(question); // Add question to list
                questionField.clear(); // Clear text field
                success.setText("Question asked"); // Output message
                }
            else {
            	// If submitted question is empty
            	success.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            	success.setText("No question asked");
        	}
        });
        
        // Go to QuestionList if button is pressed
        searchButton.setOnAction(a -> {
            new QuestionList().show(primaryStage);
        });

        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
        layout.getChildren().addAll(askLabel, questionField, askButton, success, space, searchForQuestions, searchButton);
        Scene userScene = new Scene(layout, 800, 400);
        primaryStage.setScene(userScene);
        primaryStage.setTitle("Questions");
    }
}