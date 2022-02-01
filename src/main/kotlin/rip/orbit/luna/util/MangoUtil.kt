package rip.orbit.luna.util

import rip.orbit.luna.lunaStandaloneApplication
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.TextChannel
import java.awt.Color

/**
 * @author GrowlyX
 * @since 9/17/2021
 */
object MangoUtil {

    @JvmStatic
    fun getDefaultEmbed(title: String, vararg description: String): EmbedBuilder {
        return EmbedBuilder()
            .setTitle(title).setDescription(description.joinToString(separator = "\n"))
            .setColor(Color.decode(rip.orbit.luna.lunaStandaloneApplication.settings.primary))
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
