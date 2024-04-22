package GraphicalUserInterface;

import Code.Domain.Entity;
import Code.Domain.EntityConverter;
import Code.Domain.GenericConverter;
import Code.Repo.BinaryFileRepository;
import Code.Repo.IRepository;
import Code.Repo.MemoryRepository;
import Code.Repo.TextFileRepository;
import Code.Service.Service;
import Code.Settings;
import Code.UI.UI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    public static final EntityConverter<Entity> entityConverter = new GenericConverter();
    public static final Settings settings = Settings.getInstance();
    public static Service service = getService(settings, entityConverter);
    public static UI console = new UI(service);
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 465, 355);

        HelloController hc = fxmlLoader.getController();
        hc.setService(service);
        hc.loadEntities();

        stage.setTitle("Car renting app");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        if(settings.getStartMode().equals("gui"))
            launch();
        if(settings.getStartMode().equals("console"))
            console.runConsole();
    }

    @NotNull
    private static Service getService(Settings settings, EntityConverter<Entity> entityConverter) {
        IRepository<Entity> repo = null;
        if(Objects.equals(settings.getRepoType(), "memory")){
            repo = new MemoryRepository<>();
        }
        if(Objects.equals(settings.getRepoType(), "text")){
            repo = new TextFileRepository<>("src/main/java/Code/Resources/Entities.txt", entityConverter);
        }
        if(Objects.equals(settings.getRepoType(), "binary")){
            repo = new BinaryFileRepository<>("src/main/java/Code/Resources/Entities.bin");
        }
        Service service = new Service(repo);
        return service;
    }
}