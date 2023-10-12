package de.atruvia;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestTest {
    @Test
    void xxx(){
//        Object object = mock();
//        Antwort mockedAnswer = mock();
//
//        String answer = "42 is the Answer to the Ultimate Question of Life, the Universe, and Everything";
//        when(mockedAnswer.retrieveAnswer()).thenReturn(answer);
//        when(object.toString()).thenReturn("Hallo");
//
//        assertEquals(answer, mockedAnswer.retrieveAnswer());
//        assertEquals(object.toString(), "Hallo");
        UUID result = UUID.fromString("8cf917c5-8069-479d-bb8e-ed76658d5b85");
        try (MockedStatic<UUID> mocked = mockStatic(UUID.class)) {


            // Mocking
            mocked.when(UUID::randomUUID).thenReturn(result);


            // Mocked behavior
            assertEquals("8cf917c5-8069-479d-bb8e-ed76658d5b85", UUID.randomUUID().toString());


            // Verifying mocks.
            mocked.verify(()->UUID.randomUUID(),times(1));

        }


    }
}

final class Antwort {

    public String retrieveAnswer() {
        return "The answer is 2";
    }
}
