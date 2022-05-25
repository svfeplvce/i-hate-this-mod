package net.asrielknight.ihatethismod.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.asrielknight.ihatethismod.IHateThisMod;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class ModConfigs {
    static Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    static final String path = String.valueOf(FabricLoader.getInstance().getConfigDir().resolve("IHTMconf.json"));
    static boolean notExists = Files.notExists(Path.of(path));

    public static void saveConfig() {
        if (notExists) {
            try (FileWriter writer = new FileWriter(path)) {
                GSON.toJson(new ConfigStuff(), writer);
                IHateThisMod.LOGGER.info("WRITTEN");
            } catch (Exception e) {
                IHateThisMod.LOGGER.info(e.toString());
            }
        }
    }

    public static void registerConfigs() {
        IHateThisMod.LOGGER.info("Registering configs in " + IHateThisMod.MOD_ID);
        saveConfig();
    }
}
