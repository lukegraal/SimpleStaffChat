package com.github.lukegraal.staffchat.listener;

import com.github.lukegraal.staffchat.Channel;
import com.github.lukegraal.staffchat.StaffChatPlugin;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class ChatListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChatEvent(AsyncChatEvent event) {
        Channel channel = StaffChatPlugin.CHANNEL_MAP.get(event.getPlayer().getUniqueId());
        if (channel == null) channel = Channel.ALL;
        if (channel != Channel.ALL) {
            channel.publishMessage(event.getPlayer(), event.originalMessage());
            event.setCancelled(true);
        }
    }
}
