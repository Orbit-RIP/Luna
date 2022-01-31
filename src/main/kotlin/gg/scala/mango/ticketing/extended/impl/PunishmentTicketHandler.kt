package gg.scala.mango.ticketing.extended.impl

import gg.scala.mango.ticketing.TicketData
import gg.scala.mango.ticketing.extended.ExtendedTicketHandler
import gg.scala.mango.util.MangoUtil.sendMessage
import net.dv8tion.jda.api.entities.Member

/**
 * @author niggerretard
 * @since 7/20/2021
 */
class PunishmentTicketHandler : ExtendedTicketHandler {

    override fun identifier(): String {
        return "Punishment Appeal"
    }

    override fun handle(): (Member, TicketData) -> Unit {
        return { _, ticketData ->
            sendMessage(
                ticketData.channel!!,
                "**Punishment Support**",
                "This ticket has been marked as a punishment appeal ticket.",
                "",
                "> Please provide your punishment ID so we can look into why you were punished."
            )
        }
    }
}
