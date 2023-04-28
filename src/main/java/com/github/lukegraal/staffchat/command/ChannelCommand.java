package com.github.lukegraal.staffchat.command;

import com.github.lukegraal.staffchat.Channel;
import com.github.lukegraal.staffchat.StaffChatPlugin;
import com.google.common.collect.Lists;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ChannelCommand extends Command {
    public ChannelCommand() {
        super("channel", "Change channels", "", Lists.newArrayList("chat", "c", "chan"));
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] args) {
        if (commandSender instanceof Player player) {
            if (args.length == 0) {
                Channel currentChannel = StaffChatPlugin.CHANNEL_MAP.get(player.getUniqueId());
                if (currentChannel == null) currentChannel = Channel.ALL;
                player.sendMessage(Component.text("You are currently in the " +
                        currentChannel.name() + " channel!").style(Style.style(NamedTextColor.YELLOW)));
                player.sendMessage(Component.text("You can access channels: " +
                        Channel.accessible(player).stream().map(Enum::name).collect(Collectors.joining(" "))));
            } else {
                Channel channel = Channel.byAlias(args[0].toLowerCase());
                if (channel == null || (channel.getSendPermission() != null && !player.hasPermission(channel.getSendPermission()))) {
                    player.sendMessage(Component.text("That channel does not exist, or you don't have access to it.")
                            .style(Style.style(NamedTextColor.RED)));
                } else {
                    StaffChatPlugin.CHANNEL_MAP.put(player.getUniqueId(), channel);
                    player.sendMessage(Component.text("You are now in the " + channel.name() + " channel!").style(Style.style(NamedTextColor.YELLOW)));
                }
            }
        }
        return true;
    }
}
