package me.loryyyy.loryPaperExtensions.commands

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

interface Permission {
    val value: String
}

fun CommandSender.hasPermission(permission: Permission): Boolean {
    return this !is Player || this.isOp || this.hasPermission(permission.value)
}