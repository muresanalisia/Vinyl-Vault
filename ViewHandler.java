import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewHandler {
    private Scene scene;
    private Stage window;
    private VinylModelManager modelManager;

    public ViewHandler(VinylModelManager modelManager){
        this.modelManager = modelManager;
    }

    public void start(Stage window) {
        this.window = window;

        Parent root = null;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CollectionView.fxml"));
            root = loader.load();
        }
        catch(IOException e){
            e.printStackTrace();
        }

        if(root != null){
            scene = new Scene(root);
            window.setTitle("Vinyl Vault");
            window.setScene(scene);
            window.show();
        }
    }

    public void openView(String id){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(id + ".fxml"));
            Parent root = loader.load();
            scene.setRoot(root);
        }

        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void closeView(){
        window.close();
    }
}
