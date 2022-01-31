package gg.scala.mango.cache

import gg.scala.mango.ticketing.extended.ExtendedTicketHandler
import gg.scala.mango.ticketing.extended.impl.PunishmentTicketHandler
import gg.scala.mango.ticketing.extended.impl.TransactionTicketHandler

/**
 * @author niggerretard
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
