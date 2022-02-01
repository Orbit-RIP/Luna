package rip.orbit.luna.listener

import rip.orbit.luna.lunaStandaloneApplication
import rip.orbit.luna.cache.ExtendedTicketCache
import rip.orbit.luna.cache.LocaleCache
import rip.orbit.luna.ticketing.TicketData
import rip.orbit.luna.ticketing.TicketDataStage
import rip.orbit.luna.util.MangoUtil
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.awt.Color
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.TimeUnit

/**
 * @author GrowlyX
 * @since 9/17/2021
 */
object UserListener : ListenerAdapter()
{
    var kickNow = false

    private val memberCodeMap = hashMapOf<Long, String>()
    private val questionMessages = hashMapOf<Member, MutableList<Message>>()
    private val ticketDataMap = hashMapOf<Member, TicketData>()

    override fun onPrivateMessageReceived(event: PrivateMessageReceivedEvent)
    {
        val rawMessage = event.message.contentRaw
        val code = memberCodeMap[event.author.idLong]

        val privateChannel = event.channel
        val guild = event.jda.getGuildById(rip.orbit.luna.lunaStandaloneApplication.settings.guild)

        if (rawMessage == code)
        {
            val role = guild!!.getRolesByName("Player", false)[0]

            if (role != null)
            {
                guild.addRoleToMember(event.author.idLong, role).queue()
                memberCodeMap.remove(event.author.idLong)

                privateChannel.sendMessage("You've been verified in the **${rip.orbit.luna.lunaStandaloneApplication.settings.discordName} Discord**, have fun!")
                    .queue()
            } else
            {
                privateChannel.sendMessage("Something went wrong whilst verifying you.").queue()
            }
        }
    }

    override fun onMessageReceived(event: MessageReceivedEvent)
    {
        val size = event.message.mentionedUsers.size

        val data = ticketDataMap[event.member]

        if (data != null && event.member != null && event.channel.name == data.channel!!.name)
        {
            val stage = data.stage

            if (stage != null && stage.validResponses.isEmpty())
            {
                if (stage === TicketDataStage.LANGUAGE)
                {
                    val locale = LocaleCache.id(event.message.contentRaw)

                    if (locale == null)
                    {
                        event.channel.sendMessage("Not a valid locale, fallbacking to English.")
                            .queue { message: Message? ->
                                addDeletingMessage(
                                    event.member!!, message!!
                                )
                            }
                    } else
                    {
                        event.channel.sendMessage("You chose the **${locale.id}** language.")
                            .queue { message: Message? ->
                                addDeletingMessage(
                                    event.member!!, message!!
                                )
                            }
                    }

                    addDeletingMessage(event.member!!, event.message)

                    data.locale = locale ?: LocaleCache.DEF
                } else
                {
                    data.responses[stage] = event.message.contentRaw

                    addDeletingMessage(event.member!!, event.message)
                }
            } else
            {
                if (stage == null)
                {
                    return
                }

                if (!stage.isValidResponse(event.message.contentRaw))
                {
                    event.channel.sendMessage(
                        """
                        **${data.locale!!.get()["invalid-response"]}:**
                        ${stage.validResponses.joinToString(separator = ", ")}
                        """.trimIndent()
                    ).queue { message: Message? ->
                        addDeletingMessage(
                            event.member!!, message!!
                        )
                    }

                    addDeletingMessage(event.member!!, event.message)
                    return
                } else
                {
                    data.responses[stage] = event.message.contentRaw
                    addDeletingMessage(event.member!!, event.message)
                }
            }

            data.stage = TicketDataStage.getNext(stage)

            val newStage = data.stage

            if (newStage != null)
            {
                data.channel.sendMessage(
                    data.locale!!.get()[newStage.locale]!!
                ).queue { message ->
                    addDeletingMessage(
                        event.member!!, message
                    )
                }
            } else
            {
                val builder: EmbedBuilder = MangoUtil.getDefaultEmbed(
                    "**Responses**",
                    "Here are " + event.member!!.asMention + "'s question responses."
                )

                data.responses.forEach { (ticketDataStage, response) ->
                    builder.addField(
                        ticketDataStage.description,
                        response,
                        true
                    )
                }

                event.channel.sendMessage(builder.build()).queue { it.pin().queue() }

                var ticketManager = event.textChannel.manager.putPermissionOverride(event.member!!, 3072L, 8192L)
                    .putPermissionOverride(event.guild.getRolesByName("@everyone", true)[0], 0L, 1024L)

                for (supportRole in rip.orbit.luna.lunaStandaloneApplication.settings.supportRoles.split(","))
                {
                    if (event.guild.getRolesByName(supportRole, true).isNotEmpty())
                    {
                        ticketManager = event.textChannel.manager.putPermissionOverride(
                            event.guild.getRolesByName(supportRole, true)[0],
                            listOf(Permission.VIEW_CHANNEL, Permission.MESSAGE_WRITE),
                            ArrayList()
                        )
                    }
                }

                ticketManager.queue()

                questionMessages[event.member]?.forEach {
                    it.delete().queue()
                }

                val whyResponse = data.responses[TicketDataStage.WHY]

                if (whyResponse != null)
                {
                    val ticketHandler = ExtendedTicketCache.fetchHandler(whyResponse)

                    ticketHandler?.handle()?.invoke(event.member!!, data)
                }
            }
        }

        if (size >= 7)
        {
            val role = event.guild.getRolesByName("Muted", false)[0]
            val playerRole = event.guild.getRolesByName("Player", false)[0]

            event.message.delete().queue()

            if (role != null && playerRole != null)
            {
                event.guild.addRoleToMember(event.member!!, role).queue {
                    event.channel.sendMessage(
                        event.member!!.asMention + " has been permanently muted for `pinging more than seven users`."
                    ).queue()
                }
            }
        }
    }

    override fun onGuildMemberJoin(event: GuildMemberJoinEvent)
    {
        val channel = event.guild.getTextChannelsByName("\uD83D\uDC4Bwelcome", true)[0]

        if (kickNow)
        {
            event.member.kick("Kick Now - Mango")
                .queueAfter(600L, TimeUnit.MILLISECONDS)

            println("Attempting to kick member: ${event.member.effectiveName}")
        } else
        {
            channel?.sendMessage(
                "Welcome to the ${rip.orbit.luna.lunaStandaloneApplication.settings.discordName} Discord Server, " + event.member.asMention + "! \uD83D\uDC4B"
            )?.queue()
        }
    }

    override fun onGuildMessageReactionAdd(event: GuildMessageReactionAddEvent)
    {
        if (event.channel.name != "verification")
        {
            return
        }

        val role = event.guild.getRolesByName("Player", false)[0]
        val muted = event.guild.getRolesByName("Muted", false)[0]

        if (role == null || muted == null)
        {
            return
        }

        if (event.member.roles.contains(role) || event.member.roles.contains(muted))
        {
            return
        }

        if (memberCodeMap.containsKey(event.member.idLong))
        {
            event.channel.sendMessage(event.member.asMention + ", you were sent a message by our bot. Check your direct messages for the code.")
                .queue { message: Message ->
                    message.delete().queueAfter(2L, TimeUnit.SECONDS)
                }
            return
        }

        event.member.user.openPrivateChannel().submit().whenComplete { privateChannel, _ ->
            val generatedCode = getRandomString(9)

            try
            {
                val embed = EmbedBuilder()
                embed.setColor(Color.decode("#2f3136"))
                embed.setFooter(
                    "Orbit Network · Discord Verification",
                    "https://i.imgur.com/GgqY0jK.png"
                )
                embed.setTitle("It's me again! \uD83D\uDC4B")

                embed.setDescription(
                    """
                        Please respond with the following code to verify yourself as a 
                        human and gain access to the Discord server.
                        ```
                        $generatedCode
                        ```
                    """.trimIndent()
                )

                privateChannel.sendMessage(embed.build()).queue()

                memberCodeMap[event.member.idLong] = generatedCode
            } catch (ignored: Exception)
            {
                event.channel.sendMessage(
                    event.member
                        .asMention + ", your private-messages are disabled."
                ).queue { message: Message ->
                    message.delete().queueAfter(2L, TimeUnit.SECONDS)
                }
            }
        }
    }

    override fun onButtonClick(event: ButtonClickEvent)
    {
        if (event.channel.name == "\uD83C\uDFABcreate-a-ticket")
        {
            val suffix = getRandomString(5)
            val ticketName = "ticket-${event.member!!.user.name}-$suffix"

            if (event.guild!!.getCategoriesByName("\uD83D\uDC65 Support", true)[0].channels.firstOrNull {
                    it.name.contains(
                        event.member!!.user.name.lowercase(Locale.getDefault())
                    )
                } != null
            )
            {
                return
            }

            val ticket = event.guild!!.createTextChannel(
                ticketName, event.guild!!.getCategoriesByName("\uD83D\uDC65 Support", true)[0]
            ).complete()

            val ticketManager = ticket.manager.putPermissionOverride(event.member!!, 3072L, 8192L)
                .putPermissionOverride(event.guild!!.getRolesByName("@everyone", true)[0], 0L, 1024L)
            ticketManager.queue()

            val data = TicketData(ticket)

            ticket.sendMessage(
                MangoUtil.getDefaultEmbed(
                    "**Support**",
                    "Thanks for contacting Orbit Support! Please answer the questions provided below until one of our support members are available.\n*Use -close to shut this ticket*\nCreated By: " + event.member!!
                        .user.asMention
                ).build()
            ).queue()

            ticket.sendMessage(
                """
                    **Choose a language:**
                    ${LocaleCache.LOCALES.values.joinToString(separator = ", ") { it.id }}
                """.trimIndent()
            ).queue {
                this.addDeletingMessage(
                    event.member!!, it
                )
            }

            ticket.sendMessage(event.member!!.user.asMention).queue()

            ticketDataMap[event.member!!] = data

            return
        }
    }

    private fun addDeletingMessage(member: Member, message: Message)
    {
        val messages = questionMessages[member]

        if (messages == null)
        {
            val messageList: MutableList<Message> = ArrayList()
            messageList.add(message)

            questionMessages[member] = messageList
        } else
        {
            messages.add(message)
        }
    }

    private fun getRandomString(size: Int): String
    {
        val saltedString = StringBuilder()

        while (saltedString.length < size)
        {
            val index = (ThreadLocalRandom.current()
                .nextFloat() * "abcdefghijklmnopqrstuvwxyz1234567890".length.toFloat()).toInt()
            saltedString.append("abcdefghijklmnopqrstuvwxyz1234567890"[index])
        }

        return saltedString.toString()
    }
}
