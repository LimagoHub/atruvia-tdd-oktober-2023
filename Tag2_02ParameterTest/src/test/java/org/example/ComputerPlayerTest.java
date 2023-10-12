package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class ComputerPlayerTest {

    private ComputerPlayer objectUnderTest;

    @BeforeEach
    void setUp() {
        objectUnderTest = new ComputerPlayer();
    }

    @ParameterizedTest(name = "Durchlauf Nr. {index} stones={0} turn={1}")
    @CsvSource(value = {"20,3", "21,1", "22,1", "23,2"}, delimiter = ',')
    void doTurn(int stones, int turn){
        assertEquals(turn, objectUnderTest.doTurn(stones));
    }
}