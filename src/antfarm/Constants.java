package antfarm;

/**
 * Some useful constants.
 * @author chuvpilo
 *
 */
public class Constants {
	/** Length of the genome */
	static final int GENOME_LENGTH = 196;

	/** The size of the population to evolve. */
	static final int POPULATION_SIZE = 100;
	
	/** How many steps of evolution are we allowed. */
	static final int EVOLUTION_STEPS = 1000;

	/** When do we stop walking the map. */
	static final int MAX_STEPS_ON_MAP = 200;

	/** Hand-designed genome for testing purposes */
	static final String MANUAL_GENOME = 
			"0001" + // start state
			// 4 bits = next state when food; 2-bit action
			// (00=0=N, 01=1=F, 10=2=L, 11=3=R)
			// next 4 bits = next state when no food; 2-bit action
			"0000" + "00" + "0000" + "00" + // state 0
			"0010" + "01" + "0011" + "11" + // state 1
			"0010" + "01" + "0011" + "11" + // state 2
			"0010" + "01" + "0100" + "11" + // state 3
			"0010" + "01" + "0101" + "11" + // state 4
			"0010" + "01" + "0110" + "11" + // state 5
			"0010" + "01" + "0010" + "01" + // state 6
			"0000" + "00" + "0000" + "00" + // state 7
			"0000" + "00" + "0000" + "00" + // state 8
			"0000" + "00" + "0000" + "00" + // state 9
			"0000" + "00" + "0000" + "00" + // state 10
			"0000" + "00" + "0000" + "00" + // state 11
			"0000" + "00" + "0000" + "00" + // state 12
			"0000" + "00" + "0000" + "00" + // state 13
			"0000" + "00" + "0000" + "00" + // state 14
			"0000" + "00" + "0000" + "00"; // state 15
}
