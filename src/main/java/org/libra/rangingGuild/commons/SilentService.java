package main.java.org.libra.rangingGuild.commons;

import org.rspeer.event.Service;

/** A service which doesn't require special handling on initialization or termination */
public interface SilentService extends Service {

    @Override
    default void onSubscribe() {}

    @Override
    default void onUnsubscribe() {}
}
