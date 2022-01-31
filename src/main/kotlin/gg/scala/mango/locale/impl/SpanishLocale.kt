package gg.scala.mango.locale.impl

import gg.scala.mango.locale.Locale
import gg.scala.mango.ticketing.TicketDataStage

/**
 * @author niggerretard
 * @since 7/14/2021
 */
class SpanishLocale : Locale {

    private val locale: MutableMap<String, String> = HashMap()

    override fun get(): Map<String, String> {
        return locale
    }

    override fun init() {
        locale["invalid-response"] = "Esa no es una respuesta válida, aquí hay algunas a continuación."
        locale["ign"] = "¿Cuál es tu nombre de usuario en el juego?"
        locale["why"] =
            "¿En qué categoría entraría este boleto?\n\n**Categorías disponibles:**\n" + java.lang.String.join(
                ", ",
                TicketDataStage.WHY.validResponses
            )
        locale["reason"] = "¿Por qué creaste este ticket de soporte?"
        locale["punish"] = "Proporcione su identificación de castigo (N/A si no está relacionado con el castigo)."
        locale["transact"] = "Proporcione su ID de transacción (N/A si no está relacionado con la tienda)."
    }

    override val id: String
        get() = "Español"

}
