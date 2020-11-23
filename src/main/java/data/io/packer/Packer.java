package data.io.packer;

import ru.rdude.rpg.game.logic.data.EntityData;
import ru.rdude.rpg.game.logic.data.Module;
import ru.rdude.rpg.game.logic.data.SkillData;
import ru.rdude.rpg.game.logic.data.io.GameJsonSerializer;
import settings.Settings;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Packer {

    private GameJsonSerializer jsonSerializer;

    public Packer() {
        jsonSerializer = new GameJsonSerializer();
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
        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(path))) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                if (!entry.isDirectory()) {
                    if (entry.getName().equals("skilldata")) {
                        result = unpackSkillData(zipInputStream);
                    }
                    else if (entry.getName().equals("moduledata")) {
                        result = unpackModule(zipInputStream);
                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private SkillData unpackSkillData(ZipInputStream zipInputStream) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(zipInputStream);
        String jsonString = new String(bufferedInputStream.readAllBytes());
        return jsonSerializer.deSerializeSkillData(jsonString);
    }

    private Module unpackModule(ZipInputStream zipInputStream) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(zipInputStream);
        String jsonString = new String(bufferedInputStream.readAllBytes());
        return jsonSerializer.deSerializeModule(jsonString);
    }
}
