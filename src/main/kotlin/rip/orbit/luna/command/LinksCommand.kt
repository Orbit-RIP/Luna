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
object LinksCommand : ListenerAdapter()
{

    @RolePermission(
        names = ["Player"]
    )
    @Command("links")
    fun onLinksCommand(context: JDACommandSubject)
    {
        val embedBuilder = EmbedBuilder()
        embedBuilder.setColor(Color.decode("#f3c513"))
        embedBuilder.setTitle(" \uD83D\uDD17 Links Menu")
        embedBuilder.setFooter(
            "Orbit Network · ts.orbit.rip",
            "https://i.imgur.com/GgqY0jK.png"
        )
        embedBuilder.setDescription(
            """
            **Staff Application**: `https://forms.gle/38QRcGksyeYEJgnVA`
            **Media Application**: `https://forms.gle/47sbjEnzp3FCzNTd8`
            
            **Teamspeak**: `ts.orbit.rip`
            **Minecraft**: `orbit.rip`
            **Telegram**: `https://t.me/orbitdotrip`
            **Website**: `https://www.orbit.rip (coming soon)`
            **Store**: `https://donate.orbit.rip`

               *Last updated 02/01/2022*
            """.trimIndent())
        context.parentEvent.channel.sendMessage(embedBuilder.build()).queue { message: Message ->
            message.delete().queueAfter(10L, TimeUnit.SECONDS)
        }
    }

}