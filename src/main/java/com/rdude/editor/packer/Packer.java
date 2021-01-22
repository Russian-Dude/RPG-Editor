package com.rdude.editor.packer;

import com.rdude.editor.EntityEditorController;
import com.rdude.editor.data.Data;
import com.rdude.editor.resource.ImageResourcePacker;
import com.rdude.editor.settings.Settings;
import ru.rdude.rpg.game.logic.data.EntityData;
import ru.rdude.rpg.game.logic.data.Module;
import ru.rdude.rpg.game.logic.data.SkillData;
import ru.rdude.rpg.game.logic.data.io.GameJsonSerializer;
import ru.rdude.rpg.game.utils.Functions;

import java.io.*;
import java.nio.file.Files;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Packer {

    private GameJsonSerializer jsonSerializer;
    private ImageResourcePacker imageResourcePacker;

    public Packer() {
        jsonSerializer = new GameJsonSerializer();
        imageResourcePacker = new ImageResourcePacker();
    }


    public void pack(Module module) {
        pack(module, Settings.getModulesFolder() + module.getNameInEditor());
    }

    public void pack(Module module, String path) {
        try {
            String pathToFile = path.endsWith(".module") ? path : path + ".module";

            try (// zip out
                 ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(pathToFile));
                 BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(zipOutputStream);
                 // json reader
                 BufferedReader jsonReader = new BufferedReader(new StringReader(jsonSerializer.serialize(module)))) {

                // write images:
                zipOutputStream.putNextEntry(new ZipEntry("images/"));
                File packedImagesModuleFolder = null;
                if (Data.getModuleState(module).isImagesWereChanged()) {
                    packedImagesModuleFolder = imageResourcePacker.pack(module);
                    Data.getModuleState(module).setImagesWereChanged(false);
                }
                if (packedImagesModuleFolder != null) {
                    File[] packedImages = packedImagesModuleFolder.listFiles();
                    if (packedImages != null) {
                        for (File packedImage : packedImages) {
                            zipOutputStream.putNextEntry(new ZipEntry("images/" + packedImage.getName()));
                            FileInputStream fileInputStream = new FileInputStream(packedImage);
                            bufferedOutputStream.write(fileInputStream.readAllBytes());
                            fileInputStream.close();
                        }
                    }
                }


                // write sounds:
                zipOutputStream.putNextEntry(new ZipEntry("sounds/"));

                // write main data
                zipOutputStream.putNextEntry(new ZipEntry("moduledata"));
                int b;
                while ((b = jsonReader.read()) >= 0) {
                    bufferedOutputStream.write(b);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void pack(SkillData skillData) {
        pack(skillData, Settings.getSkillsFolder() + skillData.getName());
    }

    public void pack(SkillData skillData, String path) {
        try {
            String pathToFile = path.endsWith(".skill") ? path : path + ".skill";

            try (// zip out
                 ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(pathToFile));
                 BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(zipOutputStream);
                 // json reader
                 BufferedReader jsonReader = new BufferedReader(new StringReader(jsonSerializer.serialize(skillData)))) {

                // write images:
                zipOutputStream.putNextEntry(new ZipEntry("images/"));
                skillData.getResources().getAllImageResources().forEach(resource -> {
                    File imageFile = new File(Settings.getTempImagesFolder().getPath() + skillData.getGuid() + ".png");
                    try {
                        zipOutputStream.putNextEntry(new ZipEntry("images/" + imageFile.getName()));
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(imageFile));
                        bufferedOutputStream.write(bufferedInputStream.readAllBytes());
                        bufferedInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                // write sounds:
                zipOutputStream.putNextEntry(new ZipEntry("sounds/"));

                // write main data
                zipOutputStream.putNextEntry(new ZipEntry("skilldata"));
                int b;
                while ((b = jsonReader.read()) >= 0) {
                    bufferedOutputStream.write(b);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public EntityData unpack(String path) {
        EntityData result = null;
        boolean isModule = false;
        File metaTempDirectory = new File(Functions.addSlashToString(Settings.getTempFolder().getPath()) + Functions.generateGuid());
        metaTempDirectory.mkdirs();
        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(path))) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                if (!entry.isDirectory()) {
                    if (entry.getName().equals("skilldata")) {
                        result = unpackSkillData(zipInputStream);
                    } else if (entry.getName().equals("moduledata")) {
                        result = unpackModule(zipInputStream);
                        isModule = true;
                    } else if (entry.getName().contains("images")) {
                        BufferedInputStream input = new BufferedInputStream(zipInputStream);
                        File file = new File(Functions.addSlashToString(metaTempDirectory.getPath()) + Functions.trimPath(entry.getName()));
                        BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file));
                        output.write(input.readAllBytes());
                        input.close();
                        output.close();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // move unpacked images from this temp folder to actual temp folder
        File targetFolder = isModule ? Settings.getTempPackedImagesFolder() : Settings.getTempImagesFolder();
        File[] files = metaTempDirectory.listFiles();
        if (files != null) {
            for (File file : files) {
                try {
                    if (isModule && !file.getName().endsWith(".png")) {
                        imageResourcePacker.unpack(file);
                    }
                    Files.move(file.toPath(), new File(Functions.addSlashToString(targetFolder.getPath()) + file.getName()).toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        metaTempDirectory.delete();
        return result;
    }

    private SkillData unpackSkillData(ZipInputStream zipInputStream) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(zipInputStream);
        String jsonString = new String(bufferedInputStream.readAllBytes());
        bufferedInputStream.close();
        return jsonSerializer.deSerializeSkillData(jsonString);
    }

    private Module unpackModule(ZipInputStream zipInputStream) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(zipInputStream);
        String jsonString = new String(bufferedInputStream.readAllBytes());
        bufferedInputStream.close();
        return jsonSerializer.deSerializeModule(jsonString);
    }
}
