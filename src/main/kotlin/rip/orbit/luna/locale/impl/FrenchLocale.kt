package rip.orbit.luna.locale.impl

import rip.orbit.luna.locale.Locale
import rip.orbit.luna.ticketing.TicketDataStage

/**
 * @author GrowlyX
 * @since 7/14/2021
 */
class FrenchLocale : Locale {

    private val locale: MutableMap<String, String> = HashMap()

    override fun get(): Map<String, String> {
        return locale
    }

    override fun init() {
        locale["invalid-response"] = "Ce n'est pas une réponse valide, en voici quelques-unes ci-dessous"
        locale["ign"] = "Quel est votre nom d'utilisateur dans le jeu?"
        locale["why"] =
            "Dans quelle catégorie appartiendrait ce billet?\n\n**Catégories disponibles:**\n" + java.lang.String.join(
                ", ",
                TicketDataStage.WHY.validResponses
            )
        locale["reason"] = "Pourquoi avez-vous créé ce ticket de support?"
        locale["punish"] = "Fournissez votre identifiant de punition (N/A s'il n'est pas lié à la punition)."
        locale["transact"] = "Fournissez votre identifiant de transaction (N/A si ce n'est pas lié au magasin)."
    }

    override val id: String
        get() = "Français"

}
