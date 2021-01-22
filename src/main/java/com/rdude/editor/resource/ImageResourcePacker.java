package com.rdude.editor.resource;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TextureUnpacker;
import com.rdude.editor.data.Data;
import com.rdude.editor.settings.Settings;
import javafx.scene.image.Image;
import ru.rdude.rpg.game.logic.data.Module;
import ru.rdude.rpg.game.utils.Functions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


public class ImageResourcePacker {

    private String pathToPackedImageFolder;
    private String pathToUnpackedImageFolder;

    public ImageResourcePacker() {
        pathToPackedImageFolder = Functions.addSlashToString(Settings.getTempPackedImagesFolder().getPath());
        pathToUnpackedImageFolder = Functions.addSlashToString(Settings.getTempImagesFolder().getPath());
    }

    /**
     * Pack images to atlas
     *
     * @return folder with atlas files
     */
    public File pack(Module module) {
        // output is a directory with module guid name
        File outputDir = new File(pathToPackedImageFolder + module.getGuid());
        if (!outputDir.exists()) {
            outputDir.mkdirs();
            outputDir.deleteOnExit();
        }
        // delete old packed files
        File[] packedFilesOld = outputDir.listFiles();
        if (packedFilesOld != null) {
            for (File file : packedFilesOld) {
                file.delete();
            }
        }
        // add unpacked images to texture packer
        TexturePacker texturePacker = new TexturePacker(Settings.getTempImagesFolder(), new TexturePacker.Settings());
        module.getAllEntities().forEach(entityData -> {
            entityData.getResources().getAllImageResources().forEach(resource -> {
                File file = new File(pathToUnpackedImageFolder + resource.getGuid() + ".png");
                if (file.exists()) {
                    texturePacker.addImage(file);
                }
            });
        });
        // pack
        texturePacker.pack(outputDir, String.valueOf(module.getGuid()));
        // make created packed files be deleted on exit
        File[] packedFilesNew = outputDir.listFiles();
        if (packedFilesNew != null) {
            for (File file : packedFilesNew) {
                file.deleteOnExit();
            }
        }
        return outputDir;
    }

    public void unpack(File packFile) {
        // unpack
        var textureAtlasData = new TextureAtlas.TextureAtlasData(Gdx.files.absolute(packFile.getAbsolutePath()),
                Gdx.files.absolute(Settings.getTempImagesFolder().getAbsolutePath()), false);
        TextureUnpacker textureUnpacker = new TextureUnpacker();

        File metaTempFolder = new File(pathToUnpackedImageFolder + Functions.generateGuid());
        metaTempFolder.mkdir();
        textureUnpacker.splitAtlas(textureAtlasData, Functions.addSlashToString(metaTempFolder.getAbsolutePath()));
        // add created unpacked files to data map and make them be deleted on exit
        File[] tempImagesFiles = metaTempFolder.listFiles();
        if (tempImagesFiles != null) {
            for (File file : tempImagesFiles) {
                String name = file.getName();
                name = name.substring(0, name.lastIndexOf("."));
                long guid = Long.parseLong(name);
                File moved = new File(pathToUnpackedImageFolder + file.getName());
                try {
                    Files.move(file.toPath(), moved.toPath());
                    Data.getImages().putIfAbsent(guid, new Image(moved.getAbsolutePath()));
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            metaTempFolder.delete();
        }
    }
}
