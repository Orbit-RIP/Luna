package rip.orbit.luna.cache

import rip.orbit.luna.ticketing.extended.ExtendedTicketHandler
import rip.orbit.luna.ticketing.extended.impl.PunishmentTicketHandler
import rip.orbit.luna.ticketing.extended.impl.TransactionTicketHandler

/**
 * @author GrowlyX
 * @since 7/20/2021
 */
object ExtendedTicketCache {

    val LOCALES: MutableMap<String, ExtendedTicketHandler> = mutableMapOf()

    fun fetchHandler(name: String): ExtendedTicketHandler? {
        return LOCALES.values.firstOrNull { it.identifier().equals(name, true) }
    }

    init {
        LOCALES["Punishment Appeal"] = PunishmentTicketHandler()
        LOCALES["Payment Support"] = TransactionTicketHandler()
    }
}
