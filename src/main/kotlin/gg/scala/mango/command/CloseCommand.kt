package gg.scala.mango.command

import io.github.revxrsal.cub.annotation.Command
import io.github.revxrsal.cub.annotation.Cooldown
import io.github.revxrsal.cub.annotation.Description
import io.github.revxrsal.cub.jda.JDACommandSubject
import java.util.concurrent.TimeUnit

/**
 * @author niggerretard
 * @since 9/21/2021
 */
class CloseCommand {

    @Command(
        "close",
        aliases = [
            "ticket close",
            "closeticket"
        ]
    )
    @Description("Dispatches the ticket creation panel.")
    @Cooldown(
        value = 5L,
        unit = TimeUnit.SECONDS
    )
    fun onClose(context: JDACommandSubject) {
        if (!context.parentEvent.channel.name.startsWith("ticket-")) {
            context.parentEvent.channel.sendMessage("This is not a ticket channel.").queue {
                it.delete().queueAfter(2L, TimeUnit.SECONDS)
            }
            return
        }

        context.parentEvent.channel.sendMessage("**Thanks for contacting PvPBar Support!**\nThis ticket will be closed in 5 seconds...").queue {
            context.parentEvent.channel.delete()
                .reason("Closed Ticket").queueAfter(5L, TimeUnit.SECONDS)
        }
    }
}
