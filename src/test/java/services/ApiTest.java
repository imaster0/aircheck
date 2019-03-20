package services;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ApiTest {

    @Test
    void shouldCheckIfActual() {

        //given
        Api api = mock(Api.class);
        Api api2 = spy(Api.class);
        when(api.getLastTime()).thenReturn(null);
        when(api2.getLastTime()).thenReturn(LocalDateTime.now());

        //when
        boolean apiStatus = api.getActualStatus();
        boolean api2Status = api2.getActualStatus();

        //then
        assertFalse(apiStatus);
        assertTrue(api2Status);
    }
}