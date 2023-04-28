package com.github.lukegraal.staffchat;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public enum Channel {
    ALL(Component.empty(), null, null, "a", "local", "e"),
    STAFF(
            Component.text("Staff ")
                    .style(Style.style(TextColor.color(0x3e89c9))
                            .decorate(TextDecoration.BOLD))
                    .append(Component.text("> ")
                            .style(Style.style(NamedTextColor.DARK_GRAY)
                                    .decoration(TextDecoration.BOLD, TextDecoration.State.FALSE))),
            "lukeschat.channel.staff.send",
            "lukeschat.channel.staff.receive",
            "s"
    );

    private static final Set<Channel> VALUES = Sets.newHashSet(values());
    private static final Map<String, Channel> ALIAS_MAP;
    private final Component prefix;
    private final String sendPermission;
    private final String receivePermission;
    private final String[] aliases;


    static {
        ALIAS_MAP = Maps.newHashMap();
        for (Channel value : VALUES) {
            ALIAS_MAP.put(value.name().toLowerCase(), value);
            for (String alias : value.aliases)
                ALIAS_MAP.put(alias.toLowerCase(), value);
        }
    }

    Channel(Component prefix, @Nullable String sendPermission, @Nullable String receivePermission, String... aliases) {
        this.prefix = prefix;
        this.sendPermission = sendPermission;
        this.receivePermission = receivePermission;
        this.aliases = aliases;
    }

    public static Channel byAlias(String alias) {
        return ALIAS_MAP.get(alias.toLowerCase());
    }

    public static Set<Channel> accessible(Player player) {
        return VALUES.stream().filter(c -> c.sendPermission == null ||
                player.hasPermission(c.sendPermission)).collect(Collectors.toSet());
    }

    public void publishMessage(Player sender, Component message) {
        Component msg = prefix.append(sender.displayName()).append(Component.text(": ").style(Style.style(NamedTextColor.DARK_GRAY)))
                .append(message.style(Style.style(NamedTextColor.YELLOW).decoration(TextDecoration.BOLD, TextDecoration.State.FALSE)));
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (receivePermission == null || player.hasPermission(receivePermission))
                player.sendMessage(msg);
        }
    }

    public Component getPrefix() {
        return prefix;
    }

    public String getSendPermission() {
        return sendPermission;
    }

    public String getReceivePermission() {
        return receivePermission;
    }
}
