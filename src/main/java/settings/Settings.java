package settings;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class Settings {

    private static Settings instance;

    private Path settingsFile;
    private Properties settings;

    private Path imagesFolder;
    private Path soundsFolder;

    private Path modulesFolder;
    private String skillsFolder;

    private Settings() {
        skillsFolder = "D:\\test\\skills\\";
        System.out.println("skillFolder: " + skillsFolder);
    }

    private static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
        }
        return instance;
    }

    public static String getSkillsFolder() {
        return getInstance().skillsFolder;
    }
}
