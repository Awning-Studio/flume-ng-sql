package ink.awning.flume.sql

import org.slf4j.Logger
import org.slf4j.LoggerFactory


/**
 * Logger constructor
 * @return [Logger]
 */
internal inline fun <reified T> Logger(): Logger =
    LoggerFactory.getLogger(T::class.java)