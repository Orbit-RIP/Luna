package rip.orbit.luna.locale

/**
 * @author GrowlyX
 * @since 7/14/2021
 */
interface Locale {

    fun get(): Map<String, String>
    fun init()
    val id: String

}
