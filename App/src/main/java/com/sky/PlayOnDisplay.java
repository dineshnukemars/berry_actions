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
        tmCore.turnOn(7);
        tmCore.clearDisplay();

        Segment segment = new Segment(tmCore, new TmMappings());
        Led led = new Led(tmCore);
        Switch switchOps = new Switch(tmCore);
        DisplayData displayData = new DisplayData(segment);

        SwitchWatcher watcher = new SwitchWatcher(
                switchOps,
                (buttonType, switchWatcher) -> listenToButton(board, tmCore, displayData, buttonType, switchWatcher)
        );

        displayData.start();
        watcher.start();
    }

    private Unit listenToButton(TMBoard board, TMCore tmCore, DisplayData displayData, ButtonType buttonType, SwitchWatcher watch) {
        System.out.println("button " + buttonType + "pressed");
        displayData.setPressedBtnType(buttonType);

        if (buttonType == ButtonType.BUTTON_STOP)
            ifBtnStopTerminate(board, tmCore, watch);

        return Unit.INSTANCE;
    }

    private void ifBtnStopTerminate(TMBoard board, TMCore tmCore, SwitchWatcher watch) {

        watch.stopListening();
        tmCore.clearDisplay();
        board.clear();
        System.out.println("stopped listening and clear screen and IO");
    }
}
