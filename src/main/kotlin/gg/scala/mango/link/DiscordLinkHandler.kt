package gg.scala.mango.link

import com.solexgames.datastore.commons.connection.impl.redis.NoAuthRedisConnection
import com.solexgames.datastore.commons.layer.impl.RedisStorageLayer

/**
 * @author niggerretard
 * @since 11/8/2021
 */
object DiscordLinkHandler
{

    lateinit var linked: RedisStorageLayer<String>
    lateinit var codes: RedisStorageLayer<String>

    fun initialLoad()
    {
        val defaultRedisConn = NoAuthRedisConnection("127.0.0.1", 6379)

        linked = RedisStorageLayer(
            defaultRedisConn,
            "scala:sync:linked",
            String::class.java
        )
        codes = RedisStorageLayer(
            defaultRedisConn,
            "scala:sync:codes",
            String::class.java
        )
    }
}
