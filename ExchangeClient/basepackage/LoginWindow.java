package basepackage;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginWindow {

    private Stage loginWindow;
    private TextField usernameField;
    private TextField passwordField;
    private Client client;

    public LoginWindow(Client client) {
        this.client = client;
    }

    public void display() {

        loginWindow = new Stage();

        Label usernameLabel = new Label();
        usernameLabel.setText("Username:");

        Label passwordLabel = new Label();
        passwordLabel.setText("Password:");

        usernameField = new TextField();
        passwordField = new PasswordField();

        Button loginButton = new Button();
        loginButton.setText("Sign in");
        loginButton.setOnAction(e -> {
            try {
                userLogin();
            } catch (IOException | ClassNotFoundException ioe) {
                System.out.println(ioe);
            }
        });

        Button createButton = new Button();
        createButton.setText("Create account");
        createButton.setOnAction(e -> {
            try {
                createAccount();
            } catch (IOException ioe) {
                System.out.println(ioe);
            }
        });

        HBox buttonLayout = new HBox();
        buttonLayout.getChildren().addAll(loginButton, createButton);

        VBox layout = new VBox();
        layout.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, buttonLayout);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        loginWindow.setScene(scene);
        loginWindow.show();
    }

    private void userLogin() throws IOException, ClassNotFoundException {

        client.userLogin(usernameField.getText(), passwordField.getText());

        loginWindow.close();
    }

    private void createAccount() throws IOException {

        client.createAccount(usernameField.getText(), passwordField.getText());
    }
}
