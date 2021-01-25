package com.rdude.editor.settings;

import ru.rdude.rpg.game.logic.data.Module;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;

public class Settings {

    private static Settings instance;

    private Properties properties;

    private boolean autosaveModulesWhenEntityAdded;
    private boolean askAutoLoadModules;
    private boolean autoLoadModules;

    private File modulesFolder;
    private File skillsFolder;
    private File itemsFolder;
    private File monstersFolder;

    private File tempFolder;
    private File tempImagesFolder;
    private File tempPackedImagesFolder;

    private File loadImageFolder;

    private Settings() {
        properties = new Properties();
        try {
            properties.load(new FileReader("properties.properties"));
        }
        catch (IOException e) {
            System.err.println("Can not load properties file");
            e.printStackTrace();
        }
        autosaveModulesWhenEntityAdded = Boolean.parseBoolean((String) properties.getOrDefault("autosave_modules", true));
        askAutoLoadModules = Boolean.parseBoolean((String) properties.getOrDefault("ask_auto_load_modules", true));
        autoLoadModules = Boolean.parseBoolean((String) properties.getOrDefault("auto_load_modules", true));
        modulesFolder = new File((String) properties.getOrDefault("modules_folder", "modules\\"));
        skillsFolder = new File((String) properties.getOrDefault("skills_folder", "entities\\skills\\"));
        itemsFolder = new File((String) properties.getOrDefault("items_folder", "entities\\items\\"));
        monstersFolder = new File((String) properties.getOrDefault("monsters_folder", "entities\\monsters\\"));
        tempFolder = new File((String) properties.getOrDefault("temp_folder", "temp\\"));
        tempImagesFolder = new File((String) properties.getOrDefault("temp_images_folder", "temp\\images\\"));
        tempPackedImagesFolder = new File((String) properties.getOrDefault("temp_packed_images_folder", "temp\\packed_images\\"));
        loadImageFolder = new File((String) properties.getOrDefault("load_image_folder", ""));
    }

    private static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
        }
        return instance;
    }

    public static File getSkillsFolder() {
        return getInstance().skillsFolder;
    }

    public static boolean isAutosaveModulesWhenEntityAdded() {
        return getInstance().autosaveModulesWhenEntityAdded;
    }

    public static void setAutosaveModulesWhenEntityAdded(boolean autosaveModulesWhenEntityAdded) {
        getInstance().autosaveModulesWhenEntityAdded = autosaveModulesWhenEntityAdded;
        getInstance().properties.setProperty("autosave_modules", String.valueOf(autosaveModulesWhenEntityAdded));
        getInstance().storeProperties();
    }

    public static File getModulesFolder() {
        return getInstance().modulesFolder;
    }

    public static void setModulesFolder(File modulesFolder) {
        getInstance().modulesFolder = modulesFolder;
        getInstance().properties.setProperty("modules_folder", modulesFolder.getPath());
        getInstance().storeProperties();
    }

    public static boolean isAskAutoLoadModules() {
        return getInstance().askAutoLoadModules;
    }

    public static void setAskAutoLoadModules(boolean askAutoLoadModules) {
        getInstance().askAutoLoadModules = askAutoLoadModules;
        getInstance().properties.setProperty("ask_auto_load_modules", String.valueOf(askAutoLoadModules));
        getInstance().storeProperties();
    }

    public static boolean isAutoLoadModules() {
        return getInstance().autoLoadModules;
    }

    public static void setAutoLoadModules(boolean autoLoadModules) {
        getInstance().autoLoadModules = autoLoadModules;
        getInstance().properties.setProperty("auto_load_modules", String.valueOf(autoLoadModules));
        getInstance().storeProperties();
    }

    public static void setSkillsFolder(File skillsFolder) {
        getInstance().skillsFolder = skillsFolder;
        getInstance().properties.setProperty("skills_folder", skillsFolder.getPath());
        getInstance().storeProperties();
    }

    public static File getItemsFolder() {
        return getInstance().itemsFolder;
    }

    public static void setItemsFolder(File itemsFolder) {
        getInstance().itemsFolder = itemsFolder;
        getInstance().properties.setProperty("items_folder", itemsFolder.getPath());
        getInstance().storeProperties();
    }

    public static File getMonstersFolder() {
        return getInstance().monstersFolder;
    }

    public static void setMonstersFolder(File monstersFolder) {
        getInstance().monstersFolder = monstersFolder;
        getInstance().properties.setProperty("monsters_folder", monstersFolder.getPath());
        getInstance().storeProperties();
    }

    public static File getTempImagesFolder() {
        return getInstance().tempImagesFolder;
    }

    public static void setTempImagesFolder(File tempImagesFolder) {
        getInstance().tempImagesFolder = tempImagesFolder;
        getInstance().properties.setProperty("temp_images_folder", tempImagesFolder.getPath());
        getInstance().storeProperties();
    }

    public static File getTempPackedImagesFolder() {
        return getInstance().tempPackedImagesFolder;
    }

    public static void setTempPackedImagesFolder(File tempPackedImagesFolder) {
        getInstance().tempPackedImagesFolder = tempPackedImagesFolder;
        getInstance().properties.setProperty("temp_packed_images_folder", tempPackedImagesFolder.getPath());
        getInstance().storeProperties();
    }

    public static File getTempFolder() {
        return getInstance().tempFolder;
    }

    public static void setTempFolder(File tempFolder) {
        getInstance().tempFolder = tempFolder;
        getInstance().properties.setProperty("temp_folder", tempFolder.getPath());
        getInstance().storeProperties();
    }

    public static File getLoadImageFolder() {
        return getInstance().loadImageFolder;
    }

    public static void setLoadImageFolder(File loadImageFolder) {
        getInstance().loadImageFolder = loadImageFolder;
        getInstance().properties.setProperty("load_image_folder", loadImageFolder.getPath());
        getInstance().storeProperties();
    }

    private void storeProperties() {
        try (FileWriter fileWriter = new FileWriter(new File("properties.properties"))) {
            getInstance().properties.store(fileWriter, "");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
