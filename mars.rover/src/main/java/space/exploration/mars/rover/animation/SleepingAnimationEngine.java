package space.exploration.mars.rover.animation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import space.exploration.mars.rover.kernel.Rover;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class SleepingAnimationEngine {
    private Rover                    rover         = null;
    private Logger                   logger        = LoggerFactory.getLogger(SleepingAnimationEngine.class);
    private SleepBreather            sleepBreather = null;
    private ScheduledExecutorService nap           = Executors.newSingleThreadScheduledExecutor();

    public SleepingAnimationEngine(Rover rover) {
        this.rover = rover;
    }

    public void sleep() {
        sleepBreather = new SleepBreather(rover);
        ScheduledFuture<?> sleepingRover = nap.scheduleAtFixedRate(sleepBreather, 0l, 120, TimeUnit.SECONDS);
        logger.info("Rover is sleeping now");
    }

    public void wakeupRover() {
        logger.info("Waking up rover");
        if (sleepBreather != null) {
            sleepBreather.hardInterrupt();
        }
        nap.shutdownNow();
    }
}
