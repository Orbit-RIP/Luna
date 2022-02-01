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
object TScommand : ListenerAdapter()
{

    @RolePermission(
        names = ["Player"]
    )
    @Command("ts")
    fun onTScommand(context: JDACommandSubject)
    {
        val embedBuilder = EmbedBuilder()
        embedBuilder.setColor(Color.decode("#f3c513"))
        embedBuilder.setTitle("\uD83D\uDD0A Teamspeak IP")
        embedBuilder.setFooter(
            "Orbit Network · ts.orbit.rip",
            "https://i.imgur.com/GgqY0jK.png"
        )
        embedBuilder.setDescription(
            """
                You can connect using **ts.orbit.rip**
            """.trimIndent())
        context.parentEvent.channel.sendMessage(embedBuilder.build()).queue()
    }

}