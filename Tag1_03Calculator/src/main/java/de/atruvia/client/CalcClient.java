package de.atruvia.client;

import de.atruvia.math.Calculator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CalcClient {

    private final Calculator calculator;

    public void go() {

        for (int i = 0; i < 3; i++) {
            System.out.println(calculator.add(3,4));
        }

    }
}
