package me.loryyyy.loryPaperExtensions.commands

import me.loryyyy.loryPaperExtensions.config.managers.MessagesYmlManager.getMessage
import me.loryyyy.loryPaperExtensions.config.models.RuledConfigPath
import org.bukkit.command.CommandSender

abstract class ParentCommand(
    override val name: String,
    override val aliases: List<String>,
    val tooFewArgsPath: RuledConfigPath,
    val unknownArgumentPath: RuledConfigPath,
) : SubCommand {

    protected val subCommands: MutableMap<String, SubCommand> = mutableMapOf()

    protected fun registerSubCommand(cmd: SubCommand) {
        subCommands += cmd.name.lowercase() to cmd
        for (alias in cmd.aliases) {
            subCommands += alias.lowercase() to cmd
        }
    }

    override fun execute(
        sender: CommandSender,
        args: Array<String>
    ) {
        if (args.isEmpty()) {
            val cmd = subCommands[""]
            if (cmd != null) cmd.execute(sender, emptyArray())
            else sender.sendMessage(getMessage(tooFewArgsPath))
            return
        }

        val cmd = subCommands[args[0].lowercase()]

        if (cmd != null) {
            cmd.execute(sender, args.copyOfRange(1, args.size))
        } else {
            sender.sendMessage(getMessage(unknownArgumentPath, args[0]))
        }
    }

    override fun tabComplete(sender: CommandSender, args: Array<String>): List<String> {
        if (args.size == 1) {
            return subCommands.keys
                .filter { it.startsWith(args[0], ignoreCase = true) }
                .distinct()
        }
        val cmd = subCommands[args[0].lowercase()] ?: return emptyList()

        return cmd.tabComplete(sender, args.copyOfRange(1, args.size))
            .filter { it.startsWith(args[args.size - 1], ignoreCase = true) }
            .distinct()
    }

}