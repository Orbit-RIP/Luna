package gg.scala.mango.locale

/**
 * @author niggerretard
 * @since 7/14/2021
 */
interface Locale {

    fun get(): Map<String, String>
    fun init()
    val id: String

}
