package gui;

import exceptions.FileException;
import exceptions.GrootException;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import manager.FileManager;
import manager.TaskManager;

import java.util.Objects;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private GrootGUI groot;

    private final Image userImage = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/images/DaUser.png")));
    private final Image grootImage = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/images/DaGroot.png")));
    private static boolean isFileCorrupted = false;
    TaskManager taskManager;


    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().add( //greet user on start
                DialogBox.getGrootDialog(GrootGUI.greet(), grootImage)
        );

        try {
            taskManager = new TaskManager();
        } catch (FileException.FileCorruptedException e) {
            isFileCorrupted = true;

            dialogContainer.getChildren().add(
                    DialogBox.getGrootDialog(GrootGUI.buildReply(e.getMessage()), grootImage));
            handleFileCorrupted();
        } catch (FileException e) {
            dialogContainer.getChildren().add(
                    DialogBox.getGrootDialog(GrootGUI.buildReply(e.getMessage()), grootImage)
            );
        }

    }

    /**
     * Injects the Duke instance
     */
    public void setGroot(GrootGUI g) {
        groot = g;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response;

        if (isFileCorrupted){
            handleFileCorruptedInput();
            return;
        }else{
            try {
                response = groot.getResponse(input);
            } catch (FileException.FileCorruptedException e) {
                isFileCorrupted = true;

                response = e.getMessage();
            } catch (GrootException e) {
                response = e.getMessage();
            }

            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input, userImage),
                    DialogBox.getGrootDialog(response, grootImage)
            );
        }

        userInput.clear();

        if (response.equals(GrootGUI.exit())) { //close program
            exit();
        }
    }

    private void handleFileCorrupted() {
        String question = "Do you want to continue with an empty list? (y/n)";
        dialogContainer.getChildren().addAll(
                DialogBox.getGrootDialog(GrootGUI.buildReply(question), grootImage)
        );
        handleFileCorruptedInput();
    }

    @FXML
    private void handleFileCorruptedInput() {
        String input = userInput.getText();
        String response;

        if (input.equalsIgnoreCase("y")) {
            TaskManager.clearTasklist();
            isFileCorrupted = false;
            try{
                FileManager.emptyFile();
                taskManager = new TaskManager();
            }catch (FileException e){
                response = e.getMessage();
                dialogContainer.getChildren().add(
                        DialogBox.getGrootDialog(GrootGUI.buildReply(response), grootImage)
                );
            }
            userInput.clear();
        } else if (input.equalsIgnoreCase("n")) {
            isFileCorrupted = false;
            dialogContainer.getChildren().add(
                    DialogBox.getGrootDialog(GrootGUI.exit(), grootImage)
            );
            exit();
        }else{
            dialogContainer.getChildren().add(
                    DialogBox.getGrootDialog(GrootGUI.buildReply("Invalid input"), grootImage)
            );
            handleFileCorrupted();
        }
    }

    private void exit(){
        PauseTransition delay = new PauseTransition(Duration.seconds(0.8));

        delay.setOnFinished(event -> Platform.exit());

        delay.play();
    }
}
