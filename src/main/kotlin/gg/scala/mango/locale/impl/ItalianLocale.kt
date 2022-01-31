package gg.scala.mango.locale.impl

import gg.scala.mango.locale.Locale
import gg.scala.mango.ticketing.TicketDataStage

/**
 * @author niggerretard
 * @since 7/14/2021
 */
class ItalianLocale : Locale {

    private val locale: MutableMap<String, String> = HashMap()

    override fun get(): Map<String, String> {
        return locale
    }

    override fun init() {
        locale["invalid-response"] = "Non è una risposta valida, eccone alcune qui sotto"
        locale["ign"] = "Qual è il tuo nome utente nel gioco?"
        locale["why"] =
            "In quale categoria rientrerebbe questo biglietto?\n\n**Categorie disponibili:**\n" + java.lang.String.join(
                ", ",
                TicketDataStage.WHY.validResponses
            )
        locale["reason"] = "Perché hai creato questo ticket di supporto?"
        locale["punish"] = "Fornisci il tuo ID di punizione (N/A se non è correlato alla punizione)."
        locale["transact"] = "Fornisci il tuo ID transazione (N/A se non è correlato al negozio)."
    }

    override val id: String
        get() = "Italiano"

}
