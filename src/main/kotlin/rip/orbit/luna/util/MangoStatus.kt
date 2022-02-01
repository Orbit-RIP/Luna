package rip.orbit.luna.util

import net.dv8tion.jda.api.entities.Activity

/**
 * @author GrowlyX
 * @since 9/20/2021
 */
data class MangoStatus(
    val activityType: Activity.ActivityType,
    val value: String
)
