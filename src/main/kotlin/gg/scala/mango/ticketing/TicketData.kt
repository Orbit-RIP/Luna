package gg.scala.mango.ticketing

import gg.scala.mango.locale.Locale
import net.dv8tion.jda.api.entities.TextChannel

/**
 * @author niggerretard
 * @since 7/14/2021
 */
data class TicketData(
    val channel: TextChannel? = null
) {
    val metaData: MutableMap<String, String> = mutableMapOf()
    val responses: MutableMap<TicketDataStage, String> = mutableMapOf()

    var locale: Locale? = null

    var stage: TicketDataStage? = TicketDataStage.FIRST
}
