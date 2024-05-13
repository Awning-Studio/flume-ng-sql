package ink.awning.flume.sql

import org.hibernate.CacheMode
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import org.hibernate.cfg.Configuration


/**
 * Build [SessionFactory] with basic [Configuration]s.
 * @param url jdbc:mysql://localhost:3306/database
 * @param username username
 * @param password password
 * @return [SessionFactory]
 */
internal fun SessionFactory(
    url: String,
    username: String,
    password: String,
): SessionFactory = Configuration().run {
    val confId = "hibernate.connection"
    setProperty("$confId.url", url)
    setProperty("$confId.username", username)
    setProperty("$confId.password", password)

    val serviceRegistry = StandardServiceRegistryBuilder()
        .applySettings(properties).build()
    buildSessionFactory(serviceRegistry)
}


/**
 * Open [Session].
 * @receiver [SessionFactory]
 * @return [Session]
 */
internal fun SessionFactory.session(): Session = openSession().apply {
    cacheMode = CacheMode.IGNORE
    isDefaultReadOnly = false
}