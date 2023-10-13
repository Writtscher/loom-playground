package org.playground.loom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.stream.IntStream;

public class Application {

    private static final int THREAD_AMOUNT = 100_000;

    private static final Duration SLEEP_DURATION = Duration.ofSeconds(5L);

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        //runDefaultThreadExample(); // Run into out of memory
        runVirtualThreadExample();
    }

    private static void runDefaultThreadExample() {
        LOG.info(STR. "Starting [\{ THREAD_AMOUNT }] threads" );
        var threads = IntStream.range(0, THREAD_AMOUNT).mapToObj($ -> {
            Runnable runnable = () -> {
                LOG.trace("Running thread");
                SneakyThread.sleep(SLEEP_DURATION);
                LOG.trace("Finished thread");
            };
            return new Thread(runnable, STR. "thread-\{ $ }" );
        }).toList();

        try {
            threads.forEach(Thread::start);
        } catch (Throwable ignore) {
        }

        var threadsRunning = threads.stream().filter(Thread::isAlive).count();
        LOG.info(STR. "Wanted [\{ threads.size() }] threads, started [\{ threadsRunning }] threads" );
    }

    private static void runVirtualThreadExample() {
        LOG.info(STR. "Starting [\{ THREAD_AMOUNT }] threads" );
        var threads = IntStream.range(0, THREAD_AMOUNT).mapToObj($ -> {
            Runnable runnable = () -> {
                LOG.trace("Running thread");
                SneakyThread.sleep(SLEEP_DURATION);
                LOG.trace("Finished thread");
            };
            return Thread.ofVirtual()
                    .name(STR. "thread-\{ $ }" )
                    .start(runnable);
        }).toList();

        var threadsRunning = threads.stream().filter(Thread::isAlive).count();
        LOG.debug(STR. "Running threads [\{ threadsRunning }]" );
        while (threadsRunning > 0) {
            LOG.debug(STR. "Running threads [\{ threadsRunning }]" );
            threadsRunning = threads.stream().filter(Thread::isAlive).count();
        }
        LOG.info(STR. "Wanted [\{ threads.size() }] threads, started [\{ threads.size() }] threads" );
    }
}
