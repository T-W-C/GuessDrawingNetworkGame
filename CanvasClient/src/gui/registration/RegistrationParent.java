package gui.registration;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class RegistrationParent {
    private static RegistrationParent registrationParent;

    public static RegistrationParent getInstance() {
        if (registrationParent == null) {
            registrationParent = new RegistrationParent();
            return registrationParent;
        }
        return registrationParent;
    }

    private Parent getParent() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("Registration.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }
    public Scene getScene(){
        return new Scene(this.getParent(),  580, 655);
    }
}
