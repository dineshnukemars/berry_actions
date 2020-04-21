package com.sky;

import com.pi4j.io.gpio.RaspiBcmPin;
import com.sky.tm1638.*;
import kotlin.Unit;


public class PlayOnDisplay {

    public PlayOnDisplay() {
        TMBoard board = new TMBoard(
                new GpioConfig().gpio,
                new PinConfig(RaspiBcmPin.GPIO_22, RaspiBcmPin.GPIO_17, RaspiBcmPin.GPIO_27),
                null
        );

        TMCore tmCore = board.getTMCore();
        tmCore.turnOn(1);
        tmCore.clearDisplay();

        Segment segment = new Segment(tmCore, new TmMappings());
        Led led = new Led(tmCore);
        Switch switchOps = new Switch(tmCore);
        DisplayData displayData = new DisplayData(segment);

        SwitchWatcher watcher = new SwitchWatcher(switchOps, (buttonType, watch) -> {
            System.out.println("button " + buttonType + "pressed");

            displayData.setPressedBtnType(buttonType);
            ifBtnStopTerminate(board, tmCore, buttonType, watch);
            return Unit.INSTANCE;
        });

        displayData.start();
        watcher.start();
    }

    private void ifBtnStopTerminate(TMBoard board, TMCore tmCore, ButtonType buttonType, SwitchWatcher watch) {
        if (buttonType == ButtonType.BUTTON_STOP) {
            watch.stopListening();
            tmCore.clearDisplay();
            board.clear();
            System.out.println("stopped listening and clear screen and IO");
        }
    }
}
