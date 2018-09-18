package cc.funkemunky.fixer.api.command;

import cc.funkemunky.fixer.Mojank;
import cc.funkemunky.fixer.api.event.MListener;
import lombok.Getter;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class CommandManager extends MListener {
    private List<Command> commands;

    public CommandManager() {
        commands = new ArrayList<>();
        Mojank.getInstance().getServer().getPluginManager().registerEvents(this, Mojank.getInstance());
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        String[] message = event.getMessage().split(" ");
        String command = message[0].replaceAll("/", "");

        Optional<Command> opCommand = commands.stream().filter(cmd -> cmd.getName().equalsIgnoreCase(command)).findFirst();


        if(opCommand.isPresent()) {
            String[] args = event.getMessage().replaceAll(message[0] + " ", "").split(" ");
            opCommand.get().onCommand(event.getPlayer(), args);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onSevercommand(ServerCommandEvent event) {
        String[] message = event.getCommand().split(" ");
        String command = message[0].replaceAll("/", "");

        Optional<Command> opCommand = commands.stream().filter(cmd -> cmd.getName().equalsIgnoreCase(command)).findFirst();


        if(opCommand.isPresent()) {
            String[] args = event.getCommand().replaceAll(message[0] + " ", "").split(" ");
            opCommand.get().onCommand(event.getSender(), args);
            event.setCancelled(true);
        }
    }
}