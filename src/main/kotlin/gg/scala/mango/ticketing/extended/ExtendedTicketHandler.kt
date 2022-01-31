package gg.scala.mango.ticketing.extended

import gg.scala.mango.ticketing.TicketData
import net.dv8tion.jda.api.entities.Member

/**
 * @author niggerretard
 * @since 7/20/2021
 */
interface ExtendedTicketHandler {

    fun identifier(): String

    fun handle(): (Member, TicketData) -> Unit

}
