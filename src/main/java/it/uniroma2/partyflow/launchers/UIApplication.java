package it.uniroma2.partyflow.launchers;

/**
 * Interface defining the lifecycle operations of the PartyFlow application.
 *
 * <p>Provides a common contract for launching different UI implementations
 * (CLI or GUI), keeping application lifecycle management
 * separate from business logic.</p>
 */

public interface UIApplication {
    void launch();
}
