package com.medikiosk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.medikiosk.util.PasswordUtil;
import org.junit.jupiter.api.Test;

class PasswordUtilTest {

    @Test
    void correctPasswordVerifies() {
        assertTrue(PasswordUtil.verify("Secret#1", PasswordUtil.hash("Secret#1")));
    }

    @Test
    void wrongPasswordFails() {
        assertFalse(PasswordUtil.verify("wrong", PasswordUtil.hash("Secret#1")));
    }

    @Test
    void sameInputGivesDifferentHashesBecauseOfSalt() {
        assertNotEquals(PasswordUtil.hash("Secret#1"), PasswordUtil.hash("Secret#1"));
    }

    @Test
    void sampleHashesInSchemaSqlMatchDemoPasswords() {
        assertTrue(PasswordUtil.verify("Admin@123",
            "PBKDF2$120000$e620edb641cbce55745c49a4f797f6d2$384c58184aaf05cd26f8555ec0393203c5a5f7f1792a085390b680ab2e2f33b7"));
        assertTrue(PasswordUtil.verify("Doctor@123",
            "PBKDF2$120000$92868bf209a5bcaad553562c1c305a76$82eb6ea9e884119882acde383f0b65fbe820755e0eb81a10be39bb73b69e33b4"));
        assertTrue(PasswordUtil.verify("Doctor@456",
            "PBKDF2$120000$c5619adb14e779d46cb29c26dfeb1004$4969c268506e073b518280cdc89c0f240a84799339f99c2a87a1c73152f1fcd3"));
    }

    @Test
    void malformedHashIsRejectedNotCrashing() {
        assertEquals(false, PasswordUtil.verify("x", "not-a-hash"));
    }
}
