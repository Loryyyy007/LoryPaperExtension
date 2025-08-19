package me.loryyyy.loryPaperExtensions.commands

import me.loryyyy.loryPaperExtensions.config.managers.MessagesYmlManager.getMessage
import me.loryyyy.loryPaperExtensions.config.models.RuledConfigPath
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEvent
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object SendCommandUsageService {

    lateinit var baseMessagePath: RuledConfigPath
    lateinit var hoverTextPath: RuledConfigPath

    fun setup(baseMessagePath: RuledConfigPath, hoverTextPath: RuledConfigPath){
        this.baseMessagePath = baseMessagePath
        this.hoverTextPath = hoverTextPath
    }

    fun send(usage: String, description: String?, sender: CommandSender) {
        val message = getMessage(baseMessagePath, mapOf(
            "usage" to usage,
            "description" to description
        ))
        val hoverText = getMessage(hoverTextPath)

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