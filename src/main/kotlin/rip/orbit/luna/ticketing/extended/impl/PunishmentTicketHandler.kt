package rip.orbit.luna.ticketing.extended.impl

import rip.orbit.luna.ticketing.TicketData
import rip.orbit.luna.ticketing.extended.ExtendedTicketHandler
import rip.orbit.luna.util.MangoUtil.sendMessage
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
