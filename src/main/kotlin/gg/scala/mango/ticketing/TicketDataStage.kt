package gg.scala.mango.ticketing


/**
 * @author niggerretard
 * @since 7/14/2021
 */
enum class TicketDataStage(
    val locale: String?,
    val description: String,
    val validResponses: List<String>
) {

    LANGUAGE(
        null, "Language", listOf()
    ),
    IGN(
        "ign", "IGN", listOf()
    ),
    WHY(
        "why", "Category", listOf(
            "Punishment Appeal", "Payment Support", "Staff Application", "Reward Claiming", "Other"
        )
    ),
    REASON(
        "reason", "Reason", listOf()
    );

    fun isValidResponse(response: String): Boolean {
        return validResponses.firstOrNull { response.toLowerCase().contains(it.toLowerCase()) } != null
    }

    companion object {
        val FIRST = values()[0]

        fun getNext(before: TicketDataStage): TicketDataStage? {
            return if (values().size == before.ordinal + 1) null else values()[before.ordinal + 1]
        }
    }

}
