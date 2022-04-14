package wallet.GUI.controllers;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.io.File;

import wallet.App;
import wallet.APP.User;

public class RegisterController {
    @FXML
    private Label wrong;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField nationalId;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirmPassword;
    @FXML
    private Button back;
    @FXML
    private Button register;

    @FXML
    private void onBackButtonClicked() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(new File("build/resources/main/GUI/fxml/hello-view.fxml").toURL());
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) (back.getScene().getWindow());
        Scene scene = new Scene(root, 320, 320);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void onRegisterClicked() {
        if (nationalId.getText().equals("") || firstName.getText().equals("") || lastName.getText().equals("") || password.getText().equals("")) {
            Alert a = new Alert(AlertType.WARNING);
            a.setContentText("All informations are required.");
            a.show();
        }
        else if (nationalId.getText().length() != 11) {
            Alert a = new Alert(AlertType.WARNING);
            a.setContentText("Your national ID must be 11 numbers long.");
            a.show();
        }
        else if (password.getText().equals(confirmPassword.getText())) {
            App.currentUser = new User(firstName.getText().toLowerCase(), lastName.getText().toLowerCase(), nationalId.getText(), password.getText());
            try {
                App.api.post_user(App.currentUser.write_data());
                Alert a = new Alert(AlertType.INFORMATION);
                a.setContentText("Your account has been successffuly created"+'\n'+"your id : "+App.currentUser.get_userID());
                a.show();
                onBackButtonClicked();
            }
            catch (Exception e) {
            }
        } else {
            wrong.setVisible(true);
            PauseTransition visiblePause = new PauseTransition(Duration.seconds(2));
            visiblePause.setOnFinished(
                    event -> wrong.setVisible(false)
            );
            visiblePause.play();
        }
    }

}
