package gg.scala.mango.util

import gg.scala.mango.MangoStandaloneApplication
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.TextChannel
import java.awt.Color

/**
 * @author niggerretard
 * @since 9/17/2021
 */
object MangoUtil {

    @JvmStatic
    fun getDefaultEmbed(title: String, vararg description: String): EmbedBuilder {
        return EmbedBuilder()
            .setTitle(title).setDescription(description.joinToString(separator = "\n"))
            .setColor(Color.decode(MangoStandaloneApplication.settings.primary))
    }

    @JvmStatic
    fun sendMessage(channel: TextChannel, vararg messages: String?) {
        channel.sendMessage(
            messages.joinToString(
                separator = "\n"
            )
        ).queue()
    }
}
