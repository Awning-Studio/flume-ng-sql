package ink.awning.flume.sql

import org.hibernate.Session


/**
 * SQL connection.
 * @property factory [SessionFactory]
 * @property session [Session]
 * @constructor
 */
class Connection(url: String, username: String, password: String) {
    private val factory = SessionFactory(url, username, password)
    private val session = factory.session()


    /**
     * Execute SQL query.
     * @param query query
     * @return [String]
     */
    fun execute(query: String) = session.createNativeQuery(query, List::class.java).run {
        resultList.joinToString("\n") { (it as List<*>).joinToString() }
    }


    /**
     * Close this session.
     */
    fun close() {
        factory.close()
        session.close()
    }
}