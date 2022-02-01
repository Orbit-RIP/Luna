package rip.orbit.luna.ticketing

import rip.orbit.luna.locale.Locale
import net.dv8tion.jda.api.entities.TextChannel

/**
 * @author GrowlyX
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
