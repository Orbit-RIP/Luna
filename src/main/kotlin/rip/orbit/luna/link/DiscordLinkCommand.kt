package rip.orbit.luna.link

import rip.orbit.luna.MangoStandaloneApplication
import io.github.revxrsal.cub.annotation.Command
import io.github.revxrsal.cub.annotation.Cooldown
import io.github.revxrsal.cub.jda.JDACommandSubject
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * @author GrowlyX
 * @since 11/8/2021
 */
object DiscordLinkCommand
{
    @Command(
        "link",
        aliases = [
            "sync"
        ]
    )
    @Cooldown(
        value = 5L,
        unit = TimeUnit.SECONDS
    )
    fun onSync(context: JDACommandSubject, code: String)
    {
        val verifiedRole = context.asMember().jda
            .getRoleById(907340064927907863L)

        if (verifiedRole == null)
        {
            context.reply("Sorry, you cannot link at this time.")
            return
        }

        if (context.asMember().roles.contains(verifiedRole))
        {
            context.reply("Your account is already linked.")
            return
        }

        context.parentEvent.channel.sendMessage("Attempting account link...").queue { message ->
            DiscordLinkHandler.codes.fetchAllEntries().thenApply {
                for (mutableEntry in it)
                {
                    if (mutableEntry.value == code)
                    {
                        return@thenApply UUID.fromString(mutableEntry.key)
                    }
                }

                return@thenApply null
            }.thenAccept {
                if (it == null)
                {
                    message.editMessage("Failed to link your account. Did you provide the correct code?").queue()
                    return@thenAccept
                }

                message.editMessage(
                    "Your account has been linked!\nYou've been given the `Discord Linked` role."
                ).queue { _ ->
                    context.asMember().guild.addRoleToMember(context.asMember(), verifiedRole).queue()

                    DiscordLinkHandler.linked.saveEntry(it.toString(), "${
                        context.asUser().name
                    }#${
                        context.asUser().discriminator
                    }")

                    DiscordLinkHandler.codes.deleteEntry(it.toString())
                }
            }
        }
    }
}
