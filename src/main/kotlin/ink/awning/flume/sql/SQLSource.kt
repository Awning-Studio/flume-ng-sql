package ink.awning.flume.sql

import org.apache.flume.Context
import org.apache.flume.PollableSource
import org.apache.flume.PollableSource.Status
import org.apache.flume.conf.Configurable
import org.apache.flume.source.AbstractSource


/**
 * SQL data source.
 * @property connection [Connection]
 * @property query SQL query
 * @property delay polling interval
 * @property lastEvent [StringEvent]?
 */
class SQLSource : AbstractSource(), Configurable, PollableSource {
    private lateinit var connection: Connection
    private lateinit var query: String
    private var delay: Long = 1000
    private var lastEvent: StringEvent? = null

    override fun getBackOffSleepIncrement() = 100L
    override fun getMaxBackOffSleepInterval() = 5000L


    override fun configure(context: Context): Unit = with(context) {
        // Reading config
        val url = getStringOrThrow("sql.url")
        val username = getStringOrThrow("sql.username", "root")
        val password = getStringOrThrow("sql.password")
        query = getStringOrThrow("sql.query")
        delay = getLongOrThrow("sql.delay", delay)

        // Open a connection
        connection = Connection(url, username, password)

        LOGGER.info(Logo)
    }


    /**
     * Send sql query result to [channelProcessor]
     */
    private fun sendData() {
        val data = "${connection.execute(query)}\n"
        if (data != lastEvent?.data) {
            channelProcessor.processEvent(
                StringEvent(data).also { lastEvent = it }
            )
        }
    }


    override fun process() = try {
        Thread.sleep(delay)
        sendData()
        Status.READY
    } catch (e: Exception) {
        LOGGER.error("Process Error", e)
        Status.BACKOFF
    }


    override fun stop() {
        connection.close()
        super.stop()
    }


    private fun Context.getStringOrThrow(key: String) = getString(key)
        ?: error("The configuration of the $key is missing")


    private fun Context.getStringOrThrow(key: String, default: String) =
        getString(key, default) ?: noConfigurationProvided(key)


    private fun Context.getLongOrThrow(key: String, default: Long) =
        getLong(key, default) ?: noConfigurationProvided(key)


    /**
     * Throw when no configuration for [key].
     * @param key
     * @return [Nothing]
     */
    private fun noConfigurationProvided(key: String): Nothing =
        error("The configuration of the $key is missing")


    companion object {
        private val LOGGER = Logger<SQLSource>()
    }
}