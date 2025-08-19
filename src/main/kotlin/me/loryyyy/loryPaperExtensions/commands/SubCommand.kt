package me.loryyyy.loryPaperExtensions.commands

import org.bukkit.command.CommandSender

interface SubCommand {

    val name: String
    val aliases: List<String>

    fun execute(sender: CommandSender, args: Array<String>)
    fun tabComplete(sender: CommandSender, args: Array<String>): List<String>

}