package org.playground.loom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public final class SneakyThread {

    private static final Logger LOG = LoggerFactory.getLogger(SneakyThread.class);

    public static void join(Thread thread) {
        try {
            LOG.debug(STR."Joining thread [\{thread.getName()}]");
            thread.join();
        } catch (Exception e) {
            throw new RuntimeException("Sneaky join failed");
        }
    }

    public static void sleep(Duration duration) {
        try {
            LOG.debug(STR."Sleeping for [\{duration.getSeconds()}]");
            Thread.sleep(duration.toMillis());
        } catch (Exception e) {
            throw new RuntimeException("Sneaky sleep failed");
        }
    }
}
