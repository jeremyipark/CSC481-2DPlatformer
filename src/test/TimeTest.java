package test;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import time.LocalTimeline;
import time.RealTimeline;

/**
 * This class merely exists to test the timeline functionality.
 *
 * I do not wish for this class to be graded; it's merely for testing purposes.
 *
 * @author jeremypark
 *
 */
class TimeTest {

    @Test
    void test () throws InterruptedException {
        RealTimeline timeline = new RealTimeline();
        timeline.start();
        timeline.setTicSize( 1000 );

        LocalTimeline timeline2 = new LocalTimeline();
        timeline2.anchorTimeline(timeline);
        timeline2.setTicSize( 5 );

        long lastTime = 0;
        long lastTime2 = timeline2.getTime();

        while (timeline.getTime() <= 10) {
            if (timeline.getTime() > lastTime) {
                System.out.println("GLOBAL GAME TIME: " + timeline.getTime());
                lastTime = timeline.getTime();
            }

            if (timeline2.getTime() > lastTime2) {
                System.out.println("LOCAL TIME: " + timeline2.getTime());
                lastTime2 = timeline2.getTime();
            }
        }

        timeline.pause();

        System.out.println("PAUSE GLOBE FOR FIVE SECONDS...");
        TimeUnit.SECONDS.sleep(5);

        timeline.play();

        System.out.println("GLOBAL GAME TIME: " + timeline.getTime());
        System.out.println("LOCAL TIME: " + timeline2.getTime());

        timeline2.pause();
        System.out.println("PAUSE LOCAL FOR FIVE SECONDS, WORLD KEEPS GOING...");
        TimeUnit.SECONDS.sleep(5);
        timeline2.play();

        System.out.println("GLOBAL GAME TIME: " + timeline.getTime());
        System.out.println("LOCAL TIME: " + timeline2.getTime());

        timeline.pause();
        System.out.println("PAUSE GLOBE FOR FIVE SECONDS...");
        TimeUnit.SECONDS.sleep(5);
        timeline.play();

        System.out.println("GLOBAL GAME TIME: " + timeline.getTime());
        System.out.println("LOCAL TIME: " + timeline2.getTime());

        timeline2.pause();
        System.out.println("PAUSE LOCAL FOR FIVE SECONDS, WORLD KEEPS GOING...");
        TimeUnit.SECONDS.sleep(5);
        timeline2.play();

        System.out.println("GLOBAL GAME TIME: " + timeline.getTime());
        System.out.println("LOCAL TIME: " + timeline2.getTime());

        //
        //        System.out.println("TOTAL PAUSE TIME: " + timeline.getTotalPauseTime());
        //        System.out.println("REAL TIME: " + timeline.getRealTime());
        //        System.out.println("GAME TIME: " + timeline.getTime());
        //        System.out.println( "PAUSED: " + timeline.getPause());
        //
        //
        //        timeline.play();
        //        System.out.println("TOTAL PAUSE TIME AFTER PLAY: " + timeline.getTotalPauseTime());
    }
}
