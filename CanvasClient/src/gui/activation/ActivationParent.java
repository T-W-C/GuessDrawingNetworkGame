package gui.activation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class ActivationParent {
    private static ActivationParent activationParent;

    public static ActivationParent getInstance() {
        if (activationParent == null) {
            activationParent = new ActivationParent();
            return activationParent;
        }
        return activationParent;
    }

    private Parent getParent() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("Activation.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }
    public Scene getScene(){
        return new Scene(this.getParent(), 590, 360);
    }
}
