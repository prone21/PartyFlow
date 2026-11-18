package it.uniroma2.partyflow.utilities.console;

import java.io.Console;
import java.util.Scanner;

/**
 * Singleton responsible for centralizing the management of CLI input.
 *
 * <p>Provides a single shared Scanner instance associated with the
 * standard input stream ({@code System.in}), allowing all CLI graphic
 * controllers to read user input through a common component.</p>
 *
 * <p>Using multiple Scanner instances associated with the same input
 * stream may cause unexpected behavior. Since each Scanner maintains
 * its own internal buffer, one instance may read and buffer more input
 * than requested, making that input unavailable to other instances.</p>
 *
 * <p>This problem may occur in several scenarios, such as:</p>
 * <ul>
 *     <li>Pasting multiple lines of input into the terminal.</li>
 *     <li>Entering input before the next prompt is displayed.</li>
 *     <li>Running automated tests with predefined input sequences.</li>
 * </ul>
 *
 * <p>Furthermore, closing any Scanner associated with
 * {@code System.in} would also close the underlying input stream,
 * preventing other components from reading further input.</p>
 *
 * <p>The Singleton design pattern is adopted to avoid these issues
 * by ensuring that all CLI graphic controllers use the same Scanner
 * instance throughout the application lifecycle. The Scanner is
 * intentionally kept open to preserve access to standard input.</p>
 *
 * <p>This implementation assumes sequential access from the CLI
 * and is not thread-safe.</p>
 */

public class ConsoleReader {

    private static ConsoleReader instance = null;

    private final Scanner scanner;
    private final Console console;

    private ConsoleReader() {
        this.console = System.console();
        this.scanner = new Scanner(System.in);
    }

    // Ensures a single instance
    public static ConsoleReader getInstance() {
        if (instance == null) {
            instance = new ConsoleReader();
        }

        return instance;
    }

    /**
     * Reads a line from the standard input stream.
     *
     * <p>When a system console is available, Console is used instead
     * of Scanner to ensure that both ordinary input and password
     * input are handled by the same input reader.</p>
     *
     * <p>Using Scanner for ordinary input and Console for password
     * input would introduce two independent readers associated with
     * the same input stream, potentially causing buffering conflicts.</p>
     *
     * <p>If no system console is available, the shared Scanner
     * is used as a fallback for all input operations.</p>
     *
     * @return the line entered by the user, excluding the line separator
     */

    public String readLine() {
        if (this.console == null) {
            return this.scanner.nextLine();
        }

        return this.console.readLine();

    }

    /**
     * Reads a password without displaying the entered characters
     * when a system console is available.
     *
     * <p>If no system console is available, the shared Scanner
     * is used as a fallback. In this case, the password remains
     * visible during input.</p>
     *
     * @return the password entered by the user
     */
    public String readPassword() {
        if (this.console == null) {
            return this.scanner.nextLine();
        }

        char[] password = this.console.readPassword();
        return new String(password);

    }




}