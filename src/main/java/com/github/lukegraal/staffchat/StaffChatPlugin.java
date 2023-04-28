package com.github.lukegraal.staffchat;

import com.github.lukegraal.staffchat.command.ChannelCommand;
import com.github.lukegraal.staffchat.command.StaffChatCommand;
import com.github.lukegraal.staffchat.listener.ChatListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

public class StaffChatPlugin extends JavaPlugin {
    public static final WeakHashMap<UUID, Channel> CHANNEL_MAP = new WeakHashMap<>();
    public static StaffChatPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        getServer().getCommandMap().register("staffchat", new StaffChatCommand());
        getServer().getCommandMap().register("channel", new ChannelCommand());
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
    }
}