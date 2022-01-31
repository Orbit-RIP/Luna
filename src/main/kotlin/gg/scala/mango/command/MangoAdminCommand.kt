package gg.scala.mango.command

import gg.scala.mango.listener.UserListener
import io.github.revxrsal.cub.annotation.Command
import io.github.revxrsal.cub.annotation.Description
import io.github.revxrsal.cub.annotation.Subcommand
import io.github.revxrsal.cub.jda.JDACommandSubject
import io.github.revxrsal.cub.jda.annotation.RolePermission
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Emoji
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.components.Button
import net.dv8tion.jda.api.interactions.components.ButtonStyle
import java.awt.Color
import java.util.concurrent.TimeUnit

/**
 * @author niggerretard
 * @since 9/17/2021
 */
@Command(
    "mango",
    aliases = ["magconf", "mangoconfig", "mangoadmin"]
)
object MangoAdminCommand : ListenerAdapter()
{

    @RolePermission(
        names = ["Owner"]
    )
    @Subcommand("verify-panel")
    fun onVerificationPanel(context: JDACommandSubject)
    {
        val embed = EmbedBuilder()
        embed.setColor(Color.decode("#2f3136"))
        embed.setFooter(
            "PvPBar Network · Discord Verification",
            "https://cdn.discordapp.com/attachments/906652259327291422/906922061480493066/logo.png"
        )
        embed.setTitle("Hello there! Welcome to our Discord \uD83D\uDC4B")
        embed.setImage("https://media.discordapp.net/attachments/906652259327291422/906990622894866432/BAREMBED_10.png")

        embed.setDescription(
            """
               Welcome to **PvPBar's Official Discord!** We can't wait for you to join the server. You're very close to becoming a full member, just follow these steps below

               **Step 1️⃣** - Enable Direct Messages/DMs, you can do this by selecting the menu for our server, and selecting "Privacy Settings"

               **Step 2️⃣** - On this message, react with the 🛡️ emoji below, which will then prompt the bot to send you a DM

               **Step 3️⃣** - Check your DMs with the PvPBar Bot

               **Step 4️⃣** - Type the code the bot has given you into the direct messages with it.

               *That's it! Once you've completed those steps you'll be in. Can't wait to see you on the other side 🧡*
            """.trimIndent()
        )

        context.parentEvent.channel.sendMessage(embed.build()).queue() {
            it.addReaction("\uD83D\uDEE1").queue()
        }
    }

    @RolePermission(
        names = ["Owner"]
    )
    @Subcommand("guidelines-panel")
    fun onGuidelinesPanel(context: JDACommandSubject)
    {
        val embed = EmbedBuilder()
        embed.setColor(Color.decode("#2f3136"))
        embed.setFooter(
            "PvPBar Network · Information Page",
            "https://cdn.discordapp.com/attachments/906652259327291422/906922061480493066/logo.png"
        )
        embed.setTitle("Our Discord Server Guidelines \uD83D\uDCDC")
        embed.setImage("https://media.discordapp.net/attachments/906652259327291422/906971176100851772/BAREMBED_9.png")

        embed.setDescription(
            """
                These rules are constantly updating, so be sure to check back! Major changes will be announced in <#883841239793950720>.
                
                1️⃣ `Don't be Toxic, and don't start Drama`
                Toxicity will result in a mute, Drama doesn't belong here either.
                2️⃣ `Inappropriate/NSFW Content is not allowed`
                i.e: inappropriate websites, photos, and/or videos are prohibited
                3️⃣ `No Spamming Please`
                Flooding chats with text, emojis, or other characters are not allowed.
                4️⃣ `No Advertising of any kind`
                Please do not advertise other discord servers, or other projects
                Please do not advertise other discord servers, or other projects
                5️⃣ `Any kind of discrimination is not allowed`
                Racism, Sexism or Homophobia will result in immediate punishment.
                6️⃣ `Doxxing/DDOSing threats are not tolerated at all`
                DDOS threats are not a joke, and doxxing is in fact illegal.
                7️⃣ `Listen & Respect Staff Members`
                They are here to help you, impersonating them is also an offense.
                8️⃣ `Do not ping anyone of importance`
                i.e: Staff, Media, Owners, and others
            """.trimIndent()
        )

        context.parentEvent.channel.sendMessage(embed.build()).queue()
    }

    @RolePermission(
        names = ["Owner"]
    )
    @Subcommand("welcome-panel")
    @Description("Dispatches the welcome panel.")
    fun onWelcomePanel(context: JDACommandSubject)
    {
        val embed = EmbedBuilder()
        embed.setColor(Color.decode("#2f3136"))
        embed.setFooter(
            "PvPBar Network · Information Page",
            "https://cdn.discordapp.com/attachments/906652259327291422/906922061480493066/logo.png"
        )
        embed.setTitle("Welcome to PvPBar's Official Discord \uD83D\uDC4B")
        embed.setImage("https://media.discordapp.net/attachments/906652259327291422/906967308759617566/BAREMBED_7.png")

        embed.setDescription(
            """
                Hey there! We wish you a warm welcome to the **Official PvPBar Discord Server**!
                
                PvPBar is a Competitive PvP Minecraft Server, striving to give you the best experience possible experience throughout our network, which currently supports Minecraft versions **1.7.10 through 1.12.x**.
                
                Below you can find a series of buttons, which enables you to look through our information menu. By clicking on one of the buttons, an embed will be sent to this chat, which only you can see.
                
                We hope you have an amazing time in our Discord! 🧡
                `-PvPBar Management`
            """.trimIndent()
        )

        context.parentEvent.channel.sendMessage(embed.build())
            .setActionRow(
                Button.success("server-ip", "Server IPs"),
                Button.success("guidelines", "Guidelines"),
                Button.success("socials", "Socials"),
                Button.success("faq", "Frequently Asked"),
            )
            .queue()
    }

    override fun onButtonClick(event: ButtonClickEvent)
    {
        val button = event.button
            ?: return

        when (button.id)
        {
            "server-ip" ->
            {
                val embed = EmbedBuilder()
                embed.setColor(Color.decode("#2f3136"))
                embed.setFooter(
                    "PvPBar Network · Information Page",
                    "https://cdn.discordapp.com/attachments/906652259327291422/906922061480493066/logo.png"
                )
                embed.setTitle("Our Server IPs \uD83D\uDCE1")
                embed.setImage("https://media.discordapp.net/attachments/906652259327291422/907044148249493524/BAREMBED_12.png")

                embed.addField(
                    MessageEmbed.Field(
                        "`North America` \uD83C\uDDFA\uD83C\uDDF8",
                        "na.pvp.bar", true
                    )
                )

                embed.addField(
                    MessageEmbed.Field(
                        "`Europe` \uD83C\uDDEA\uD83C\uDDFA",
                        "eu.pvp.bar", true
                    )
                )

                embed.addField(
                    MessageEmbed.Field(
                        "`South America` \uD83C\uDDE7\uD83C\uDDF7",
                        "sa.pvp.bar", true
                    )
                )

                embed.setDescription(
                    """
                        Looking to connect to our server? We have multiple proxies that are based in different corners of the world, that will give you optimal performance for your location.

                        ‼ **There are currently 3 different proxies/IPs, have a look below**
                        ㅤ
                    """.trimIndent()
                )

                event.deferReply().setEphemeral(true)
                    .addEmbeds(embed.build()).queue()
            }
            "guidelines" ->
            {
                val embed = EmbedBuilder()
                embed.setColor(Color.decode("#2f3136"))
                embed.setFooter(
                    "PvPBar Network · Information Page",
                    "https://cdn.discordapp.com/attachments/906652259327291422/906922061480493066/logo.png"
                )
                embed.setTitle("Our Guidelines")

                embed.setDescription(
                    """
                        Hey! You can view our server guidelines in this channel. Scroll up!
                    """.trimIndent()
                )

                event.deferReply().setEphemeral(true)
                    .addEmbeds(embed.build()).queue()
            }
            "socials" ->
            {
                val embed = EmbedBuilder()
                embed.setColor(Color.decode("#2f3136"))
                embed.setFooter(
                    "PvPBar Network · Information Page",
                    "https://cdn.discordapp.com/attachments/906652259327291422/906922061480493066/logo.png"
                )
                embed.setTitle("Our Socials")

                embed.setDescription(
                    """
                        > Twitter - `@PvPBarMC`
                    """.trimIndent()
                )

                event.deferReply().setEphemeral(true)
                    .addEmbeds(embed.build()).queue()
            }
            "faq" ->
            {
                val embed = EmbedBuilder()
                embed.setColor(Color.decode("#2f3136"))
                embed.setFooter(
                    "PvPBar Network · Information Page",
                    "https://cdn.discordapp.com/attachments/906652259327291422/906922061480493066/logo.png"
                )
                embed.setTitle("FAQs")

                embed.setDescription(
                    """
                        Frequently Asked Questions is coming very soon!
                    """.trimIndent()
                )

                event.deferReply().setEphemeral(true)
                    .addEmbeds(embed.build()).queue()
            }
        }
    }

    @RolePermission(
        names = ["*"]
    )
    @Subcommand("kicknow")
    fun onKickNow(context: JDACommandSubject)
    {
        UserListener.kickNow = !UserListener.kickNow

        context.parentEvent.channel
            .sendMessage("Kick now's status is now: ${UserListener.kickNow}")
            .queue {
                it.delete().queueAfter(5L, TimeUnit.SECONDS)
            }
    }

    @RolePermission(
        names = ["Owner"]
    )
    @Subcommand("ticket-panel")
    @Description("Dispatches the ticket creation panel.")
    fun onTicketPanel(context: JDACommandSubject)
    {
        val embed = EmbedBuilder()
        embed.setColor(Color.decode("#2f3136"))
        embed.setFooter(
            "PvPBar Network · Support System",
            "https://cdn.discordapp.com/attachments/906652259327291422/906922061480493066/logo.png"
        )
        embed.setTitle("Create a Support Ticket \uD83C\uDFAB")
        embed.setImage("https://media.discordapp.net/attachments/906652259327291422/907015753583173712/BAREMBED_11.png")

        embed.setDescription(
            """
                Need Help? You're at the right place! Make sure to read below with information on how to create a ticket, and more

                **To make a ticket,** click the button below that is marked with ✉️

                **If you're in need of immediate support**, Discord may not be the best option for you. Please connect to our TeamSpeak @ **ts.pvp.bar**

                *Abuse of the ticket system will result in punishment.*
            """.trimIndent()
        )

        context.parentEvent.channel.sendMessage(
            embed.build()
        ).setActionRow(
            Button.primary("create", "Click to Create")
                .withStyle(ButtonStyle.SUCCESS)
                .withEmoji(Emoji.fromUnicode("✉"))
        ).queue()
    }

}
