package gg.scala.mango.command

import io.github.revxrsal.cub.annotation.Command
import io.github.revxrsal.cub.annotation.Cooldown
import io.github.revxrsal.cub.jda.JDACommandSubject
import io.github.revxrsal.cub.jda.annotation.RolePermission
import net.dv8tion.jda.api.EmbedBuilder
import java.awt.Color
import java.time.Instant
import java.util.concurrent.TimeUnit

/**
 * @author niggerretard
 * @since 9/17/2021
 */
class AnnounceCommand
{

    @RolePermission(
        names = ["Owner", "Platform-Admin"]
    )
    @Command(
        "announcement",
        aliases = ["announce"]
    )
    @Cooldown(
        value = 5L,
        unit = TimeUnit.SECONDS
    )
    fun onTicketPanel(context: JDACommandSubject, message: String)
    {
        val broadcast = message.endsWith(" -a")

        val embedBuilder = EmbedBuilder()
        embedBuilder.setColor(Color.decode("#f3c513"))
        embedBuilder.setTitle("Announcement")
        embedBuilder.setFooter(context.asMember().effectiveName, context.asUser().avatarUrl)
        embedBuilder.setTimestamp(Instant.now())
        embedBuilder.setDescription(
            "${
                if (broadcast)
                {
                    context.parentEvent.guild.publicRole.asMention
                } else
                {
                    ""
                }
            } ${message.removeSuffix(" -a")}"
        )

        context.parentEvent.channel.sendMessage(embedBuilder.build()).queue()

        if (broadcast)
        {
            context.parentEvent.channel.sendMessage(
                context.parentEvent.guild.publicRole.asMention
            ).queue { toDelete ->
                toDelete.delete().queueAfter(10L, TimeUnit.MILLISECONDS)
            }
        }
    }

}
