package commands.slashadapters.adapters

import commands.Category
import commands.Command
import commands.runnables.utilitycategory.CustomRoleAddCommand
import commands.runnables.utilitycategory.CustomRoleManageCommand
import commands.slashadapters.Slash
import commands.slashadapters.SlashAdapter
import commands.slashadapters.SlashMeta
import mysql.hibernate.entity.guild.GuildEntity
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData

@Slash(
    name = "customrole",
    descriptionCategory = [Category.UTILITY],
    descriptionKey = "customroleadd_description",
    commandAssociations = [CustomRoleAddCommand::class, CustomRoleManageCommand::class],
)
class CustomRoleAdapter : SlashAdapter() {

    private val subcommandAdd = "add"
    private val subcommandManage = "manage"

    public override fun addOptions(commandData: SlashCommandData): SlashCommandData {
        val customRoleAddTrigger = Command.getCommandProperties(CustomRoleAddCommand::class.java).trigger
        val customRoleAddSubcommandData = generateSubcommandData(subcommandAdd, "${customRoleAddTrigger}_description")
            .addOptions(generateOptionData(OptionType.USER, "members", "utility_members", true))

        val customRoleManageTrigger = Command.getCommandProperties(CustomRoleManageCommand::class.java).trigger
        val customRoleManageSubcommandData = generateSubcommandData(subcommandManage, "${customRoleManageTrigger}_description")

        commandData.addSubcommands(customRoleAddSubcommandData, customRoleManageSubcommandData)
        return commandData
    }

    override fun process(event: SlashCommandInteractionEvent, guildEntity: GuildEntity): SlashMeta {
        val clazz = when (event.subcommandName) {
            subcommandAdd -> CustomRoleAddCommand::class.java
            subcommandManage -> CustomRoleManageCommand::class.java
            else -> throw IllegalArgumentException("Unknown subcommand")
        }
        return SlashMeta(clazz, collectArgs(event))
    }

}