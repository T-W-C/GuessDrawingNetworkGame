package registrationWithGui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller {

    @FXML public TextField emailText;
    @FXML public TextField usernameText;
    @FXML public TextField passwordText;
    @FXML public TextField verifyPasswordText;
    @FXML public Label Message1;
    @FXML public Label Message2;

    @FXML
    public void createAndSubmitClient() throws Exception{
        //Client client = new Client("127.0.0.1", 1254);

        //Message1.setVisible(false);
        //Message2.setVisible(true);
    }

    @FXML
    public void clearEmailText() {
        if(emailText.getText().equals("Enter email here")) {
            emailText.clear();
        }
    }

    @FXML
    public void clearUserText() {
        if(usernameText.getText().equals("Enter username here")) {
            usernameText.clear();
        }
    }
    @FXML
    public void clearPasswordText() {
        if(passwordText.getText().equals("Enter password here")) {
            passwordText.clear();
        }
    }
    @FXML
    public void clearVerifyText() {
        if(verifyPasswordText.getText().equals("Re-enter password here")) {
            verifyPasswordText.clear();
        }
    }
}
