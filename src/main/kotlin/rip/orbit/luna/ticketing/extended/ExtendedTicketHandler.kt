package rip.orbit.luna.ticketing.extended

import rip.orbit.luna.ticketing.TicketData
import net.dv8tion.jda.api.entities.Member

/**
 * @author GrowlyX
 * @since 7/20/2021
 */
interface ExtendedTicketHandler {

    fun identifier(): String

    fun handle(): (Member, TicketData) -> Unit

}
