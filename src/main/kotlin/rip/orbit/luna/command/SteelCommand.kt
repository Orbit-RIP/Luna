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
object SteelCommand : ListenerAdapter()
{

    @RolePermission(
        names = ["Player"]
    )
    @Command("steelpvp")
    fun onSteelCommand(context: JDACommandSubject)
    {
        val embedBuilder = EmbedBuilder()
        embedBuilder.setColor(Color.decode("#f3c513"))
        embedBuilder.setTitle("SteelPvP")
        embedBuilder.setFooter(
            "Orbit Network · ts.orbit.rip",
            "https://i.imgur.com/GgqY0jK.png"
        )
        embedBuilder.setDescription(
            """
            **SteelPvP** is our partnered 1.16 HCF server. They offer the best that 1.16 HCF currently has to offer
            **SteelPvP** has a SOTW every saturday, For more information join thier discord
            
            🔗 **Links**
            IP: Steelpvp.com
            Discord: discord.gg/HCF
            
            """.trimIndent())
        context.parentEvent.channel.sendMessage(embedBuilder.build()).queue { message: Message ->
            message.delete().queueAfter(10L, TimeUnit.SECONDS)
        }
    }

}