package rip.orbit.luna.command

import io.github.revxrsal.cub.annotation.Command
import io.github.revxrsal.cub.jda.JDACommandSubject
import io.github.revxrsal.cub.jda.annotation.RolePermission
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Message
import java.awt.Color
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.util.concurrent.TimeUnit


/**
 * @author Branched
 * @since 10/30/2021
 */
object HelpCommand : ListenerAdapter()
{

    @RolePermission(
        names = ["Player"]
    )
    @Command("help")
    fun onHelpCommand(context: JDACommandSubject)
    {
        val embedBuilder = EmbedBuilder()
        embedBuilder.setColor(Color.decode("#f3c513"))
        embedBuilder.setTitle("\uD83D\uDC4B Help Menu")
        embedBuilder.setFooter(
            "Orbit Network · ts.orbit.rip",
            "https://i.imgur.com/GgqY0jK.png"
        )
        embedBuilder.setDescription(
            """
               **-IP** `Will display the sever ip and version`
               **-TS** `will display the Teamspeak IP`
               **-Links** `Will show all links a player may need`
               **-steelpvp** `Information on our sister network`
               **-Help** `What you are currently reading`
               
               *Last updated 02/01/2022*
            """.trimIndent())
        context.parentEvent.channel.sendMessage(embedBuilder.build()).queue { message: Message ->
            message.delete().queueAfter(10L, TimeUnit.SECONDS)
        }
    }

}