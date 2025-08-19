package me.loryyyy.loryPaperExtensions.commands

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEvent
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SendCommandUsageService(
    val baseMessage: String,
    val hoverText: String
) {

    fun send(usage: String, description: String?, sender: CommandSender) {
        val message = baseMessage
            .replace("<usage>", usage)
            .replace("<description>", description!!)

        if (sender !is Player) {
            sender.sendMessage(message)
            return
        }

        var com: Component = Component.text(message)
        com = com.clickEvent(ClickEvent.suggestCommand(usage))
        com = com.hoverEvent(HoverEvent.showText(Component.text(hoverText)))
        sender.sendMessage(com)
    }

}