package de.atruvia;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestTest {
    @Test
    void xxx(){
        Object object = mock();
        Antwort mockedAnswer = mock();

        String answer = "42 is the Answer to the Ultimate Question of Life, the Universe, and Everything";
        when(mockedAnswer.retrieveAnswer()).thenReturn(answer);
        when(object.toString()).thenReturn("Hallo");

        assertEquals(answer, mockedAnswer.retrieveAnswer());
        assertEquals(object.toString(), "Hallo");
//        try (MockedStatic mocked = mockStatic(System.class)) {
//
//            // Mocking
//            mocked.when(System::currentTimeMillis).thenReturn(10L);
//
//
//            // Mocked behavior
//            assertEquals(10L, System.currentTimeMillis());
//
//
//            // Verifying mocks.
//            mocked.verify(()->System.currentTimeMillis(),times(1));
//
//        }
    }
}

final class Antwort {

    public String retrieveAnswer() {
        return "The answer is 2";
    }
}
