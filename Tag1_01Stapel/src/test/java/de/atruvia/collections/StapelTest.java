package de.atruvia.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StapelTest {

    private Stapel objectUnderTest;

    @BeforeEach
    void setUp() {
        objectUnderTest = new Stapel();
    }
    @Test
    void xyz() {
        assertEquals(true, objectUnderTest.isEmtpy());
    }
    @Test
    void abc() {
        objectUnderTest.push(1);
        assertEquals(false, objectUnderTest.isEmtpy());
    }

}