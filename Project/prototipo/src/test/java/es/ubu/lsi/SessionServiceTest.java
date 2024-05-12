package es.ubu.lsi;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SessionServiceTest {
    private static final String host = "https://school.moodledemo.net";
    private static final String username = "teacher";
    private static final String password = "moodle";
    private static SessionService sessionService;

    @BeforeAll
    public static void BeforeAll() throws IOException {
        sessionService = SessionService.getInstance(username, password, host);

    }

    @Test
    void getSSkey() {
        assertNotNull(sessionService.getSSKey(host), "The retrieved sskey should not be null");
        assertNotEquals("The retrieved sskey should not be empty", "", sessionService.getSSKey(host));
    }
}
