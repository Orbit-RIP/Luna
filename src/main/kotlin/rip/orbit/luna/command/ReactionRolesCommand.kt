package rip.orbit.luna.command

import io.github.revxrsal.cub.annotation.Command
import io.github.revxrsal.cub.jda.JDACommandSubject
import io.github.revxrsal.cub.jda.annotation.RolePermission
import net.dv8tion.jda.api.entities.Emoji
import net.dv8tion.jda.api.events.interaction.SelectionMenuEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.components.selections.SelectionMenu
import java.util.concurrent.TimeUnit

/**
 * @author GrowlyX
 * @since 10/30/2021
 */
object ReactionRolesCommand : ListenerAdapter()
{

    @RolePermission(
        names = ["Owner"]
    )
    @Command("rolepanel")
    fun onRolePanel(context: JDACommandSubject)
    {
        val alertRoleMenu = SelectionMenu.create("menu:roles")
            .setPlaceholder("Choose your alert roles")
            .setRequiredRange(0, 5)
            .addOption("HCF", "HCF-alerts", Emoji.fromUnicode("\uD83C\uDF4E"))
            .addOption("KitMap", "KitMap-alerts", Emoji.fromUnicode("\uD83C\uDFF9"))
            .addOption("Practice", "Practice-alerts", Emoji.fromUnicode("\uD83C\uDFF9"))
            .addOption("Beta", "beta-alerts", Emoji.fromUnicode("\uD83D\uDCAB"))
            .build()

        context.parentEvent.channel.sendMessage(
            "**Use the following drop-down menu to select which roles you'd like to equip.**\n*You may remove your roles by simply unselecting the role within the drop-down menu.*"
        ).setActionRow(alertRoleMenu).queue()
    }

    override fun onSelectionMenu(event: SelectionMenuEvent)
    {
        event.interaction.selectedOptions!!.forEach {
            val roles = event.guild!!.getRolesByName(it.value, true)

            if (roles.isNotEmpty())
            {
                val role = roles[0]

                if (!event.member!!.roles.contains(role))
                {
                    event.guild!!.addRoleToMember(event.member!!, role).queue()
                }
            }
        }

        val unselected = event.selectionMenu!!.options.filter {
            !event.interaction.selectedOptions!!.contains(it)
        }

        unselected.forEach {
            val roles = event.guild!!.getRolesByName(it.value, true)

            if (roles.isNotEmpty())
            {
                val role = roles[0]

                if (event.member!!.roles.contains(role))
                {
                    event.guild!!.removeRoleFromMember(event.member!!, role).queue()
                }
            }
        }

        event.reply("**Your roles have been updated!**").setEphemeral(true).queue()
    }
}
