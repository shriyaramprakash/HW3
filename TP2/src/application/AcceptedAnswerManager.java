public class AcceptedAnswerManager {
    private QuestionList questionList;  // Assuming a collection of questions exists

    // Constructor
    public AcceptedAnswerManager(QuestionList questionList) {
        this.questionList = questionList;
    }

    /**
     * Allows the author of the question to mark an answer as accepted.
     *
     * @param questionId The ID of the question.
     * @param answerId The ID of the answer to be accepted.
     * @param user The student making the request.
     * @return A success or failure message.
     */
    public String markAnswerAsAccepted(int questionId, int answerId, User user) {
        Question question = questionList.getQuestionById(questionId);
        if (question == null) {
            return "Question not found.";
        }

        // Ensure only the question's author can mark an answer
        if (!question.getAuthor().equals(user)) {
            return "You are not authorized to accept an answer for this question.";
        }

        // Find and update the answer
        boolean answerUpdated = false;
        for (Answer answer : question.getAnswers()) {
            if (answer.getId() == answerId) {
                answer.setAccepted(true);
                answerUpdated = true;
                break;
            }
        }

        if (!answerUpdated) {
            return "Answer not found for the given question.";
        }

        return "Answer successfully marked as accepted!";
    }
}
