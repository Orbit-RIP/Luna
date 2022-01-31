package gg.scala.mango.ticketing.extended.impl

import gg.scala.mango.ticketing.TicketData
import gg.scala.mango.ticketing.extended.ExtendedTicketHandler
import gg.scala.mango.util.MangoUtil.sendMessage
import net.dv8tion.jda.api.entities.Member

/**
 * @author niggerretard
 * @since 7/20/2021
 */
class TransactionTicketHandler : ExtendedTicketHandler {

    override fun identifier(): String {
        return "Payment Support"
    }

    override fun handle(): (Member, TicketData) -> Unit {
        return { _, ticketData ->
            sendMessage(
                ticketData.channel!!,
                "**Payment Support**",
                "This ticket has been marked as a payment support ticket.",
                "",
                "> If you are having issues processing your store payment, tell us what's wrong.",
                "> If you want to receive your in-game rewards, please provide your PayPal transaction ID."
            )
        }
    }
}
