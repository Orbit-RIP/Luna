package gg.scala.mango.util

import net.dv8tion.jda.api.entities.Activity

/**
 * @author niggerretard
 * @since 9/20/2021
 */
data class MangoStatus(
    val activityType: Activity.ActivityType,
    val value: String
)
