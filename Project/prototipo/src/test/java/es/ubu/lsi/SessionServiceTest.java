package es.ubu.lsi;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SessionServiceTest {
    private static final String host = "https://school.moodledemo.net";
    private static final String username = "teacher";
    private static final String password = "moodle";
    private static String sskey;

    @BeforeAll
    public static void BeforeAll() throws IOException {
        SessionService sessionService = SessionService.getInstance();
        sskey = sessionService.getSSkey(username, password, host);

    }

    @Test
    void getSSkey() {
        assertNotNull(sskey);
    }
}
