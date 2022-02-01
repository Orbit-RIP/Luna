package rip.orbit.luna.command

import io.github.revxrsal.cub.annotation.Command
import io.github.revxrsal.cub.jda.JDACommandSubject
import io.github.revxrsal.cub.jda.annotation.RolePermission
import net.dv8tion.jda.api.EmbedBuilder
import java.awt.Color
import net.dv8tion.jda.api.hooks.ListenerAdapter


/**
 * @author Branched
 * @since 10/30/2021
 */
object IPcommand : ListenerAdapter()
{

    @RolePermission(
        names = ["Player"]
    )
    @Command("ip")
    fun onIPcommand(context: JDACommandSubject)
    {
        val embedBuilder = EmbedBuilder()
        embedBuilder.setColor(Color.decode("#f3c513"))
        embedBuilder.setTitle(" \uD83D\uDCAB Server IP")
        embedBuilder.setFooter(
            "Orbit Network · ts.orbit.rip",
            "https://i.imgur.com/GgqY0jK.png"
        )
        embedBuilder.setDescription(
            """
                You can connect using **Orbit.rip** with versions 1.7.10-1.8.9x
            """.trimIndent())
        context.parentEvent.channel.sendMessage(embedBuilder.build()).queue()
    }

    }