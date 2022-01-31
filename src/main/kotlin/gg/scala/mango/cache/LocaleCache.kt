package gg.scala.mango.cache

import gg.scala.mango.locale.Locale
import gg.scala.mango.locale.impl.EnglishLocale
import gg.scala.mango.locale.impl.FrenchLocale
import gg.scala.mango.locale.impl.ItalianLocale
import gg.scala.mango.locale.impl.SpanishLocale
import java.util.function.Predicate

/**
 * @author niggerretard
 * @since 7/14/2021
 */
object LocaleCache {

    @JvmStatic
    val LOCALES: MutableMap<String, Locale> = mutableMapOf()

    @JvmStatic
    val DEF: EnglishLocale = EnglishLocale()

    fun initialLoad() {
        listOf(DEF, ItalianLocale(), SpanishLocale(), FrenchLocale()).forEach {
            it.init(); LOCALES[it.id] = it
        }
    }

    fun id(id: String): Locale? {
        return LOCALES.values.firstOrNull { it.id.equals(id, true) }
    }
}
