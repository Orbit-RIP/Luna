package rip.orbit.luna.locale.impl

import rip.orbit.luna.locale.Locale
import rip.orbit.luna.ticketing.TicketDataStage

/**
 * @author GrowlyX
 * @since 7/14/2021
 */
class EnglishLocale : Locale {

    private val locale: MutableMap<String, String> = HashMap()

    override fun get(): Map<String, String> {
        return locale
    }

    override fun init() {
        locale["invalid-response"] = "That's not a valid response, here are some below"
        locale["ign"] = "What is your in-game username?"
        locale["why"] =
            "What category would this ticket fall in?\n\n**Available Categories:**\n" + java.lang.String.join(
                ", ",
                TicketDataStage.WHY.validResponses
            )
        locale["reason"] = "Why did you create this support ticket?"
        locale["punish"] = "Provide your full IGN and your version of events as it happened (Use N/A if not punishment related)."
        locale["transact"] = "Provide your transaction ID (Use N/A if not store related)."
    }

    override val id: String
        get() = "English"

}
