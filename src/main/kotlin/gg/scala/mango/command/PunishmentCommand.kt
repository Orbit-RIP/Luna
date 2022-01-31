package gg.scala.mango.command

import io.github.revxrsal.cub.annotation.Command
import io.github.revxrsal.cub.annotation.Cooldown
import io.github.revxrsal.cub.jda.JDACommandSubject
import io.github.revxrsal.cub.jda.annotation.RolePermission
import net.dv8tion.jda.api.entities.Member
import java.util.concurrent.TimeUnit

/**
 * @author niggerretard
 * @since 11/26/2021
 */
object PunishmentCommand
{
    @RolePermission(
        names = ["Punishments"]
    )
    @Command("ban")
    @Cooldown(
        value = 2L,
        unit = TimeUnit.SECONDS
    )
    fun onMemberBan(context: JDACommandSubject, member: Member, reason: String?)
    {
        if (reason == null) {
            context.parentEvent.channel.sendMessage("retard happend").queue()
            return
        }
        member.ban(0, reason).queue {
            context.parentEvent.channel.sendMessage(
                "**${member.nickname}** was permanently banned by **${context.asUser().name}** for `${reason}`."
            ).queue()
        }
    }

    @RolePermission(
        names = ["Punishments"]
    )
    @Command("unban")
    @Cooldown(
        value = 2L,
        unit = TimeUnit.SECONDS
    )
    fun onMemberUnban(context: JDACommandSubject, member: Member, reason: String)
    {
        context.parentEvent.guild.unban(member.user).queue {
            context.parentEvent.channel.sendMessage(
                "**${member.nickname}** was unbanned by **${context.asUser().name}** for `${reason}`."
            ).queue()
        }
    }

    @RolePermission(
        names = ["Punishments"]
    )
    @Command("mute")
    @Cooldown(
        value = 2L,
        unit = TimeUnit.SECONDS
    )
    fun onMemberMute(context: JDACommandSubject, member: Member, reason: String)
    {
        val mutedRole = context.parentEvent.guild.getRoleById(890049538784641064L)!!

        if (member.roles.contains(mutedRole))
        {
            context.parentEvent.channel.sendMessage("This user is already muted.").queue()
            return
        }

        context.parentEvent.guild.mute(member, true).queue {
            context.parentEvent.guild.addRoleToMember(member, mutedRole).queue {
                context.parentEvent.channel.sendMessage(
                    "**${member.nickname}** was permanently muted by **${context.asUser().name}** for `${reason}`."
                ).queue()
            }
        }
    }

    @RolePermission(
        names = ["Punishments"]
    )
    @Command("unmute")
    @Cooldown(
        value = 2L,
        unit = TimeUnit.SECONDS
    )
    fun onMemberUnmute(context: JDACommandSubject, member: Member, reason: String)
    {
        val mutedRole = context.parentEvent.guild.getRoleById(890049538784641064L)!!

        if (!member.roles.contains(mutedRole))
        {
            context.parentEvent.channel.sendMessage("This user is not muted.").queue()
            return
        }

        context.parentEvent.guild.mute(member, false).queue {
            context.parentEvent.guild.removeRoleFromMember(member, mutedRole).queue {
                context.parentEvent.channel.sendMessage(
                    "**${member.nickname}** was unmuted by **${context.asUser().name}** for `${reason}`."
                ).queue()
            }
        }
    }
}