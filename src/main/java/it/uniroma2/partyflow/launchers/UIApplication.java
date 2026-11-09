package it.uniroma2.partyflow.launchers;


/**
 * The launchers play a fundamental role: it manages the application's
 * lifecycle, not the business logic.
 */


public interface UIApplication {
    void launch();

    void close();
}
