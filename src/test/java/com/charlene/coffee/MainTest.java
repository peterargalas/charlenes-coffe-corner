package com.charlene.coffee;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the code contained in the main class.
 */
class MainTest {
    private final static PrintStream originalSysOut = System.out;
    private final static PrintStream originalSysErr = System.err;

    @AfterEach
    public void resetSystemStreams() {
        System.setOut(originalSysOut);
        System.setErr(originalSysErr);
    }

    @Test
    void invokingWithoutArgumentsShouldPrintUsage() {
        final ByteArrayOutputStream sysOutCapture = new ByteArrayOutputStream();
        System.setOut(new PrintStream(sysOutCapture));
        Main.main(new String[]{});

        assertTrue(sysOutCapture.toString().contains("Usage: "));
    }

    @Test
    void invokingWithValidOrderShouldProduceReceipt() {
        final ByteArrayOutputStream sysOutCapture = new ByteArrayOutputStream();
        System.setOut(new PrintStream(sysOutCapture));
        Main.main(new String[]{"small coffee"});

        assertTrue(sysOutCapture.toString().contains("Coffee (small)"));
    }

    @Test
    void invokingWithValidInputShouldPrintErrorMessage() {
        final ByteArrayOutputStream sysErrCapture = new ByteArrayOutputStream();
        System.setErr(new PrintStream(sysErrCapture));
        Main.main(new String[]{"large tea"});

        assertTrue(sysErrCapture.toString().contains("I'm sorry"));
    }
}