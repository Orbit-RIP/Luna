package rip.orbit.luna.runnable

import rip.orbit.luna.lunaStandaloneApplication
import kotlin.properties.Delegates

/**
 * @author GrowlyX
 * @since 11/23/2021
 */
object DisplayNameUpdater : Thread()
{
    private val client = rip.orbit.luna.lunaStandaloneApplication.client

    private val mappings = mutableMapOf(
        925872233031802940L to "PA",
        925872263209836584L to "HA",
        925872278967845005L to "SA",
        925872324526354482 to "SrMod",
    )

    private val exempt = mutableListOf(
        925870657982566442L,
        925886395728404481L,
        937870779977183273L,
        937870700616765490L,
        937872157827031080L,
        937872264458797077L,
        937872360588083220L,
        937872411838263296L,
        937872360588083220L,
    )

    override fun run()
    {
        val guild = client.getGuildById(925869157126381568)!!
        val members = guild.loadMembers().get()

        for (member in members)
        {
            val displayRole = member.roles
                .maxByOrNull { it.positionRaw } ?: continue

            if (displayRole == guild.publicRole)
            {
                continue
            }

            if (exempt.contains(displayRole.idLong))
            {
                continue
            }

            val formatted = "[${
                mappings[displayRole.idLong] ?: displayRole.name
            }] ${member.user.name}"

            if (member.effectiveName == formatted)
            {
                continue
            }

            try
            {
                member.modifyNickname(formatted).queue()
            } catch (ignored: Exception)
            {
            }
        }

        try
        {
            sleep(2000L)
        } catch (exception: Exception)
        {
            exception.printStackTrace()
        }
    }
}
