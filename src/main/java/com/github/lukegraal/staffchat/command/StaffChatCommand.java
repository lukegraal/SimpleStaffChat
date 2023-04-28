package com.github.lukegraal.staffchat.command;

import com.github.lukegraal.staffchat.Channel;
import com.google.common.collect.Lists;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StaffChatCommand extends Command {
    public StaffChatCommand() {
        super("staffchat", "Access or use the staff chat", "",
                Lists.newArrayList("sc", "schat"));
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 0) {
            commandSender.sendMessage(Component.text("You must specify a message. " +
                    "If you want to talk in staff chat constantly, use /channel staff.").style(Style.style(NamedTextColor.RED)));
            return true;
        }
        if (commandSender instanceof Player player) {
            String message = String.join(" ", strings);
            if (!player.hasPermission(Channel.STAFF.getSendPermission())) {
                player.sendMessage(Component.text("You don't have permission to access this channel.")
                        .style(Style.style(NamedTextColor.RED)));
            } else {
                Channel.STAFF.publishMessage(player, Component.text(message)
                        .style(Style.style(NamedTextColor.YELLOW)
                                .decoration(TextDecoration.BOLD, TextDecoration.State.FALSE)));
            }
        }
        return true;
    }
}
