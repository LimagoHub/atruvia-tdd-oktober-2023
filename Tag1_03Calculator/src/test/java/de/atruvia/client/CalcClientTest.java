package de.atruvia.client;

import de.atruvia.math.Calculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class CalcClientTest {

    @InjectMocks
    private CalcClient objectUnderTest;
    @Mock(strictness = Mock.Strictness.LENIENT)
    private Calculator calculatorMock;



    @Test
    void gotest() {

        // Arrange
        // Recordmode
        //when(calculatorMock.add(5.0, 7.0)).thenReturn(42.0);

        doThrow(IllegalArgumentException.class).when(calculatorMock).doSomething();

        when(calculatorMock.add(3.0, 4.0))
                .thenReturn(47.11)
                .thenReturn(34.1)
                .thenThrow(IllegalArgumentException.class);

        // Replay

        // Act
        objectUnderTest.go();

        // Assert
        verify(calculatorMock, atLeast(1)).add(3.0,4.0);

    }


}