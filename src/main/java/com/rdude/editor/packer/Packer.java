package com.rdude.editor.packer;

import com.rdude.editor.data.Data;
import com.rdude.editor.enums.EntityType;
import com.rdude.editor.resource.ImageResourcePacker;
import com.rdude.editor.settings.Settings;
import ru.rdude.rpg.game.logic.data.EntityData;
import ru.rdude.rpg.game.logic.data.Module;
import ru.rdude.rpg.game.logic.data.io.GameJsonSerializer;
import ru.rdude.rpg.game.logic.data.resources.Resource;
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

    public void pack(EntityData entityData) {
        EntityType type = EntityType.of(entityData);
        pack(entityData, type.getInitialLoadDirectory().getPath(), type);
    }

    public void pack(EntityData entityData, String path) {
        EntityType type = EntityType.of(entityData);
        pack(entityData, path, type);
    }

    private void pack(EntityData entityData, String path, EntityType type) {
        try {
            String pathToFile = path.endsWith(type.getExtension()) ? path : path + type.getExtension();
            try (// zip out
                 ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(pathToFile));
                 // json reader
                 BufferedReader jsonReader = new BufferedReader(new StringReader(jsonSerializer.serialize(entityData)))) {

                // write images:
                //zipOutputStream.putNextEntry(new ZipEntry("images/"));
                // if entity is module
                if (type == EntityType.MODULE) {
                    File packedImagesModuleFolder = imageResourcePacker.pack((Module) entityData);
                    Data.getModuleState((Module) entityData).setImagesWereChanged(false);
                    if (packedImagesModuleFolder != null) {
                        File[] packedImages = packedImagesModuleFolder.listFiles();
                        if (packedImages != null) {
                            for (File packedImage : packedImages) {
                                zipOutputStream.putNextEntry(new ZipEntry("images/" + packedImage.getName()));
                                Files.copy(packedImage.toPath(), zipOutputStream);
                                zipOutputStream.closeEntry();
                            }
                        }
                    }
                }
                // if entity is not module
                else {
                    for (Resource resource : entityData.getResources().getImageResources()) {
                        if (resource == null) {
                            continue;
                        }
                        File imageFile = new File(Functions.addSlashToString(Settings.getTempImagesFolder().getPath()) + resource.getGuid() + ".png");
                        zipOutputStream.putNextEntry(new ZipEntry("images/" + imageFile.getName()));
                        Files.copy(imageFile.toPath(), zipOutputStream);
                        zipOutputStream.closeEntry();
                    }
                }

                // write sounds:
                zipOutputStream.putNextEntry(new ZipEntry("sounds/"));

                // write main data
                zipOutputStream.putNextEntry(new ZipEntry(type.getMainDataFileName()));
                int b;
                while ((b = jsonReader.read()) >= 0) {
                    zipOutputStream.write(b);
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
        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(path));
             BufferedInputStream bufferedInputStream = new BufferedInputStream(zipInputStream)) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                if (!entry.isDirectory()) {
                    // skill
                    if (entry.getName().equals(EntityType.SKILL.getMainDataFileName())) {
                        result = jsonSerializer.deSerializeSkillData(new String(bufferedInputStream.readAllBytes()));
                    }
                    // module
                    else if (entry.getName().equals(EntityType.MODULE.getMainDataFileName())) {
                        result = jsonSerializer.deSerializeModule(new String(bufferedInputStream.readAllBytes()));
                        isModule = true;
                    }
                    // images
                    else if (entry.getName().contains("images")) {
                        File file = new File(Functions.addSlashToString(metaTempDirectory.getPath()) + Functions.trimPath(entry.getName()));
                        BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file));
                        output.write(bufferedInputStream.readAllBytes());
                        output.close();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // move unpacked images from this temp folder to actual temp folder
        // if entity is already loaded do not move files
        if (result != null && !Data.isLoaded(result.getGuid())) {
            File packedImagesFromThisModule = new File(Functions.addSlashToString(Settings.getTempPackedImagesFolder().getPath()) + result.getGuid() + "\\");
            packedImagesFromThisModule.deleteOnExit();
            File targetFolder = isModule ? packedImagesFromThisModule : Settings.getTempImagesFolder();
            File[] files = metaTempDirectory.listFiles();
            if (files != null) {
                for (File file : files) {
                    try {
                        if (!file.isDirectory() && isModule && !file.getName().endsWith(".png")) {
                            imageResourcePacker.unpack(file);
                            if (!targetFolder.exists()) {
                                targetFolder.mkdirs();
                            }
                        }
                        File endPacked = new File(Functions.addSlashToString(targetFolder.getPath()) + file.getName());
                        endPacked.deleteOnExit();
                        Files.move(file.toPath(), endPacked.toPath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        // if some files still in metaTemp directory - delete them
        File[] remainedFiles = metaTempDirectory.listFiles();
        if (remainedFiles != null) {
            for (File remainedFile : remainedFiles) {
                remainedFile.delete();
            }
        }
        metaTempDirectory.delete();
        return result;
    }
}
