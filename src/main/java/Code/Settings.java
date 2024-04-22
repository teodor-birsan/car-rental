package Code;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Settings {
    private static Settings instance;
    private final String startMode;

    private final String repoType;

    private final String repoFile;

    private Settings(String startMode, String repoType, String repoFile) {
        this.startMode = startMode;
        this.repoType = repoType;
        this.repoFile = repoFile;
    }

    public String getRepoFile() {
        return repoFile;
    }

    public String getRepoType() {
        return repoType;
    }
    public String getStartMode(){
        return startMode;
    }
    private static Properties loadSettings() {
        try (FileReader fr = new FileReader("src/main/java/Code/Resources/settings.properties")) {
            Properties settings = new Properties();
            settings.load(fr);
            return settings;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized Settings getInstance() {
        Properties properties = loadSettings();
        instance = new Settings(properties.getProperty("startMode"), properties.getProperty("repo_type"), properties.getProperty("repo_file") );
        return instance;
    }
}
