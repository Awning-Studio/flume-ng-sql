package ink.awning.flume.sql

import org.apache.flume.Event


/**
 * [String] [Event]
 * @property data
 * @property body
 * @constructor
 */
class StringEvent(val data: String) : Event {
    private var body = data.toByteArray()


    override fun getBody(): ByteArray = body


    override fun setBody(value: ByteArray?) {
        value?.run { body = value }
    }


    override fun getHeaders() = emptyMap<String, String>()


    override fun setHeaders(headers: MutableMap<String, String>?) {}
}