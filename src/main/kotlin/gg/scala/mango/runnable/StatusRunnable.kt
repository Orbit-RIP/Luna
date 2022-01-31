package gg.scala.mango.runnable

import com.google.common.io.Files
import com.google.gson.reflect.TypeToken
import gg.scala.mango.MangoStandaloneApplication
import gg.scala.mango.util.MangoStatus
import net.dv8tion.jda.api.entities.Activity
import java.io.File
import java.lang.reflect.Type

/**
 * @author niggerretard
 * @since 9/14/2021
 */
object StatusRunnable : Thread() {

    private val dataType: Type = object : TypeToken<List<MangoStatus>>() {}.type

    private val messages: List<MangoStatus>

    init {
        val file = File("activities.json")

        if (!file.exists()) file.createNewFile()

        val reader = Files.newReader(
            file, Charsets.UTF_8
        )

        messages = MangoStandaloneApplication.gson.fromJson(
            reader, dataType
        )
    }

    private var index = -1

    override fun run() {
        while (true)
        {
            try
            {
                val currentTip = try {
                    messages[index++]
                } catch (e: Exception) {
                    index = 0; messages[0]
                }

                MangoStandaloneApplication.client.presence.activity = Activity.of(
                    currentTip.activityType, currentTip.value
                )
            } catch (exception: Exception)
            {
                exception.printStackTrace()
            }

            try
            {
                sleep(10000L)
            } catch (exception: Exception)
            {
                exception.printStackTrace()
            }
        }
    }
}
