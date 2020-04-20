package com.sky;

import com.pi4j.io.gpio.RaspiBcmPin;
import com.sky.tm1638.*;
import sky.timing.diagram.TimingRecorder;

public class PlayOnDisplay {

    public PlayOnDisplay() {
        TMBoard board = new TMBoard(
                new GpioConfig().gpio,
                new PinConfig(RaspiBcmPin.GPIO_22, RaspiBcmPin.GPIO_17, RaspiBcmPin.GPIO_27),
                new TimingRecorder()
        );

        TMCore tmCore = board.getTMCore();
        tmCore.turnOn(1);
        tmCore.clearDisplay();

        Segment segment = new Segment(tmCore, new TmMappings());
        Led led = new Led(tmCore);
        Switch button = new Switch(tmCore);

        sendText(segment);
//        turnOnLED(led);
//        rollSegments(segment);
//        readSwitches(button, segment);

        tmCore.clearDisplay();
        board.clear();
    }

    private static void sendText(Segment segment) {
        segment.sendData(1, "dinesh");
        sleep(2000L);
    }

    private static void turnOnLED(Led led) {
        led.sendData(0, true);
        sleep(2000L);
    }

    private static void rollSegments(Segment segment) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                segment.turnSegmentIndexONrOFF(i, j, true);
                sleep(500L);
                segment.turnSegmentIndexONrOFF(i, j, false);
            }
        }
    }

    private static void readSwitches(Switch button, Segment segment) {
        while (!button.getState(7)) {

            if (button.getState(0))
                segment.sendData(0, "D");
            else
                segment.sendData(0, ".");

            sleep(1000L);
        }
    }

    private static void sleep(Long i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
