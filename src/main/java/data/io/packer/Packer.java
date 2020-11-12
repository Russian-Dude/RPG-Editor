package data.io.packer;

import ru.rdude.rpg.game.logic.data.EntityData;
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

    public void pack(SkillData skillData) {
        try {
            String pathToFile = Settings.getSkillsFolder() + skillData.getName() + ".zip";

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
}
