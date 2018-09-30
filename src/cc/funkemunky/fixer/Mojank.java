package cc.funkemunky.fixer;

import cc.funkemunky.fixer.api.command.CommandManager;
import cc.funkemunky.fixer.api.data.DataManager;
import cc.funkemunky.fixer.api.event.ListenerManager;
import cc.funkemunky.fixer.api.fixes.Fix;
import cc.funkemunky.fixer.api.fixes.FixManager;
import cc.funkemunky.fixer.api.utils.BlockUtil;
import cc.funkemunky.fixer.api.utils.Color;
import cc.funkemunky.fixer.api.utils.MiscUtil;
import cc.funkemunky.fixer.api.utils.ReflectionsUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class Mojank extends JavaPlugin {

    private static Mojank instance;
    private DataManager dataManager;
    private FixManager fixManager;
    private CommandManager commandManager;
    public String serverVersion;
    private ConsoleCommandSender console;

    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        console = Bukkit.getConsoleSender();

        serverVersion = Bukkit.getServer().getClass().getPackage().getName().substring(23);

        new ListenerManager();
        new MiscUtil();
        new BlockUtil();
        new ReflectionsUtil();
        initializeFixes();

        dataManager = new DataManager();
        commandManager = new CommandManager();
    }

    public void initializeFixes() {
        fixManager = new FixManager();

        for(Fix fix : fixManager.getFixes()) {
            String path = "fixes." + fix.getName();
            if(getConfig().get(path) == null) {
                getConfig().set(path + ".enabled", fix.isEnabled());
            } else {
                fix.setEnabled(getConfig().getBoolean(path + ".enabled"));
            }
            for(String id : fix.getConfigValues().keySet()) {
                if(getConfig().get(path + ".values." + id) == null) {
                    getConfig().set(path + ".values." + id, fix.getConfigValues().get(id));
                } else {
                    fix.getConfigValues().put(id, getConfig().get(path + ".values." + id));
                }
            }
        }
        saveConfig();
    }

    public static Mojank getInstance() {
        return instance;
    }

    public String getPrefix() {
        return Color.translate(getConfig().getString("prefix"));
    }
}
