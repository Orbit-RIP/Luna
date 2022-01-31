package gg.scala.mango.runnable

import gg.scala.mango.MangoStandaloneApplication
import kotlin.properties.Delegates

/**
 * @author niggerretard
 * @since 11/23/2021
 */
object DisplayNameUpdater : Thread()
{
    private val client = MangoStandaloneApplication.client

    private val mappings = mutableMapOf(
        883839967049154560L to "PA",
        883839977979523212L to "HA",
        883839990226898964L to "SA",
        883840011781419048L to "SrMod",
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
