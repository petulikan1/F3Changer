package me.petulikan1.f3changer;

import me.devtec.bungeetheapi.TheAPI;
import me.devtec.bungeetheapi.configapi.Config;
import me.devtec.bungeetheapi.scheduler.Tasker;
import me.devtec.gscoreloader.Extension;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class Loader extends Extension implements Listener {
    public static Config c;

    @Override
    public void enable() {
        c=loadConfig("Words.yml");
        getProxy().getPluginManager().registerListener(getPlugin(),this);
        getProxy().getPluginManager().registerCommand(getPlugin(),new F3Command("f3","glowsite.command.f3"));
        for(ProxiedPlayer p: BungeeCord.getInstance().getPlayers()){
            sendLocally(p,"GlowSite.cz");
        }
    }
    @Override
    public void disable() {
        getProxy().getPluginManager().unregisterListener(this);
    }

    @EventHandler(priority= EventPriority.HIGHEST)
    public void playerJoin(ServerConnectedEvent e){
        new Tasker(){
            @Override
            public void run() {
                sendLocally(e.getPlayer(), "GlowSite.cz");
            }
        }.runLater(10);
    }
    public void sendLocally(ProxiedPlayer player, String brand) {
        player.sendData("minecraft:brand", (new PacketSerializer(ChatColor.AQUA + brand + ChatColor.RESET)).toArray());

    }



    public static class F3Command extends Command{

        public F3Command(String name, String permission, String... aliases) {
            super(name, permission, aliases);
        }

        @Override
        public void execute(CommandSender s, String[] args) {
            if(!s.hasPermission("glowsite.command.f3")){
                return;
            }
            TheAPI.msg(Loader.c.getString("Reload"),s);
            Loader.c.reload();
        }
    }
}

