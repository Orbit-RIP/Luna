package rip.orbit.luna

import com.google.common.io.Files
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.LongSerializationPolicy
import rip.orbit.luna.listener.UserListener
import rip.orbit.luna.cache.LocaleCache
import rip.orbit.luna.command.*
import rip.orbit.luna.link.DiscordLinkCommand
import rip.orbit.luna.link.DiscordLinkHandler
import rip.orbit.luna.runnable.DisplayNameUpdater
import rip.orbit.luna.runnable.StatusRunnable
import io.github.revxrsal.cub.jda.JDACommandHandler
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.requests.GatewayIntent
import java.io.File

/**
 * @author Growlyx
 * @since 9/17/2021
 */
object lunaStandaloneApplication {

    val gson: Gson = GsonBuilder()
        .setPrettyPrinting()
        .setLongSerializationPolicy(LongSerializationPolicy.STRING)
        .create()

    lateinit var client: JDA
    private lateinit var commandHandler: JDACommandHandler

    lateinit var settings: rip.orbit.luna.MangoArgs

    @JvmStatic
    fun main(args: Array<String>) {
        rip.orbit.luna.lunaStandaloneApplication.settings = rip.orbit.luna.lunaStandaloneApplication.gson.fromJson(
            Files.newReader(
                File("settings.json"), Charsets.UTF_8
            ),
            rip.orbit.luna.MangoArgs::class.java
        )

        rip.orbit.luna.lunaStandaloneApplication.client = JDABuilder.createDefault(rip.orbit.luna.lunaStandaloneApplication.settings.token)
            .enableIntents(
                GatewayIntent.GUILD_BANS,
                GatewayIntent.GUILD_MESSAGE_REACTIONS,
                GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.DIRECT_MESSAGES
            )
            .addEventListeners(
                UserListener,
                ReactionRolesCommand,
                LunaAdminCommand
            ).build()

        LocaleCache.initialLoad()
        DiscordLinkHandler.initialLoad()

        rip.orbit.luna.lunaStandaloneApplication.client.awaitReady().let {
            rip.orbit.luna.lunaStandaloneApplication.commandHandler = JDACommandHandler.create(
                it, JDACommandHandler.Settings.builder()
                    .prefix("-")
                    .stripMarkdown(false)
                    .build()
            )

            rip.orbit.luna.lunaStandaloneApplication.commandHandler.registerCommand(
                LunaAdminCommand,
                CloseCommand(),
                AnnounceCommand(),
                ReactionRolesCommand,
                IPcommand,
                TScommand,
                HelpCommand,
                LinksCommand,
                SteelCommand,
                DiscordLinkCommand,
                PunishmentCommand
            )

            StatusRunnable.start()
            DisplayNameUpdater.start()
        }
    }
}

class MangoArgs(
    val token: String = "",

    val primary: String = "#ffbc00",
    val secondary: String = "#ffbc00",

    val discordName: String = "Orbit",
    val guild: String = "925869157126381568",
    val supportRoles: String = "Platform Admin,Head Admin,Admin,Senior Mod,Developer"
)
