package rip.orbit.luna.cache

import rip.orbit.luna.locale.Locale
import rip.orbit.luna.locale.impl.EnglishLocale
import rip.orbit.luna.locale.impl.FrenchLocale
import rip.orbit.luna.locale.impl.ItalianLocale
import rip.orbit.luna.locale.impl.SpanishLocale
import java.util.function.Predicate

/**
 * @author GrowlyX
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
