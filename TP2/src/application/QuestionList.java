package application;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.*;

public class QuestionList {
    private static final List<QuestionAnswer> questionAnswers = new ArrayList<>();

    public void show(Stage primaryStage) {
        VBox layout = new VBox(10);
        
        // Create scrollable lists
        TextField searchField = new TextField();
        ListView<String> questionListView = new ListView<>();
        ListView<String> answerListView = new ListView<>();
        TextField answerField = new TextField();
        ListView<String> unansweredListView = new ListView<>();
        
        // Search button for questions
        Button searchButton = new Button("Search");
        searchButton.setOnAction(a -> searchQuestions(searchField.getText(), questionListView));
        
        // Submit answer button
        Button answerButton = new Button("Submit Answer");
        answerButton.setOnAction(a -> addAnswer(questionListView, answerField, answerListView, unansweredListView));
        
        // Like buttons
        Button likeButton = new Button("Like Selected Answer");
        likeButton.setOnAction(a -> likeAnswer(questionListView, answerListView));
        
        // Unanswered questions button
        Button showUnansweredButton = new Button("Show Unanswered Questions");
        showUnansweredButton.setOnAction(a -> showUnansweredQuestions(unansweredListView));

        layout.getChildren().addAll(new Label("Search"), searchField, searchButton, questionListView,
                                    new Label("Answers"), answerListView, answerField, answerButton, likeButton,
                                    new Label("Unanswered Questions"), showUnansweredButton, unansweredListView);
        primaryStage.setScene(new Scene(layout, 800, 500));
        primaryStage.setTitle("Question List");
    }
    
    // Adds question to list from Question class
    public static void addQuestion(String question) {
        questionAnswers.add(new QuestionAnswer(question));
    }
    
    // Searches for similar questions 
    private void searchQuestions(String query, ListView<String> questionListView) {
        List<String> results = new ArrayList<>();
        for (QuestionAnswer qa : questionAnswers) { // iterate through questions list
            if (qa.getQuestion().toLowerCase().contains(query.toLowerCase())) { // If questions list contains words similar to question
                results.add(qa.getQuestion()); // Add similar questions to list to return
            }
        }
        questionListView.getItems().setAll(results);
    }
    
    // Add answer
    private void addAnswer(ListView<String> questionListView, TextField answerField, ListView<String> answerListView, ListView<String> unansweredListView) {
        String question = questionListView.getSelectionModel().getSelectedItem(); // Get selected question from user
        String answer = answerField.getText().trim(); // Get rid of extra spaces
        // If not empty, add answer to answer box
        if (question != null && !answer.isEmpty()) {
            for (QuestionAnswer qa : questionAnswers) {
                if (qa.getQuestion().equals(question)) {
                    qa.addAnswer(answer);
                    break;
                }
            }
            answerField.clear(); // clear textfield
            updateAnswers(question, answerListView);
            showUnansweredQuestions(unansweredListView);
        }
    }
    
    // Like answers
    private void likeAnswer(ListView<String> questionListView, ListView<String> answerListView) {
        String question = questionListView.getSelectionModel().getSelectedItem(); // Get selected answer
        int selectedIndex = answerListView.getSelectionModel().getSelectedIndex(); // Create likes for answer
        // If not empty, increase likes
        if (question != null && selectedIndex >= 0) {
            for (QuestionAnswer qa : questionAnswers) {
                if (qa.getQuestion().equals(question)) {
                    qa.increaseLikes(selectedIndex);
                    break;
                }
            }
            updateAnswers(question, answerListView);
        }
    }
    
    // Update answers
    private void updateAnswers(String question, ListView<String> answerListView) {
        for (QuestionAnswer qa : questionAnswers) { // iterate through list
            if (qa.getQuestion().equals(question)) { // If searched question equals question in list
                answerListView.getItems().clear();
                answerListView.getItems().addAll(qa.getFormattedAnswers());
                break;
            }
        }
    }
    
    // Unanswered questions function
    private void showUnansweredQuestions(ListView<String> unansweredListView) {
        List<String> unanswered = new ArrayList<>();
        for (QuestionAnswer qa : questionAnswers) { // If question is unanswered, add to list
            if (qa.isUnanswered()) {
                unanswered.add(qa.getQuestion());
            }
        }
        unansweredListView.getItems().setAll(unanswered); // Get list of unanswered questions
    }
    
    // Class to get questions and answers
    private static class QuestionAnswer {
        private final String question;
        private final List<Answer> answers = new ArrayList<>();
        
        // Set question
        public QuestionAnswer(String question) {
            this.question = question;
        }
        
        // returns question
        public String getQuestion() {
            return question;
        }

        // Answer gets added to list
        public void addAnswer(String answer) {
            answers.add(new Answer(answer));
        }
        
        // Increases likess
        public void increaseLikes(int index) {
            if (index >= 0 && index < answers.size()) {
                answers.get(index).increaseLikes();
            }
        }
        
        // Formats likes
        public List<String> getFormattedAnswers() {
            List<String> formattedAnswers = new ArrayList<>();
            for (Answer a : answers) {
                formattedAnswers.add(a.getText() + " (Likes: " + a.getLikes() + ")");
            }
            return formattedAnswers;
        }
        
        // Returns unanswered questions
        public boolean isUnanswered() {
            return answers.isEmpty();
        }
    }

    // Class for getting answers
    private static class Answer {
        private final String text;
        private int likes;

        public Answer(String text) {
            this.text = text;
            this.likes = 0;
        }

        public String getText() {
            return text;
        }

        public int getLikes() {
            return likes;
        }

        public void increaseLikes() {
            likes++;
        }
    }
}
