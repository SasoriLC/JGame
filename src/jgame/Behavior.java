package jgame;
/**
 * This interface is used to represent any void function. Since it is an Functional Interface 
 * it can be created using lambda expressions (requires java 8 or above).
 * @author David Almeida
 * @since 1.0
 */
@FunctionalInterface
public interface Behavior {
	
	/**
	 * The method to execute
	 * @since 1.0
	 */
	void run();
}
