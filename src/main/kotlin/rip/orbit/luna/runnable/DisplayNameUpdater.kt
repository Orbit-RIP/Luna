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
        890049538784641064L,
        907340064927907863L,
        890049499177812059L,
        884475217269899316L,
        883840639052165130L,
        883840660745125900L,
        904046158127718491L,
        904051241917775952L
    )

    override fun run()
    {
        val guild = client.getGuildById(842825042886262784)!!
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
