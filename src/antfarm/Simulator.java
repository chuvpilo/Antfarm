package antfarm;

/**
 * Antfarm Simulator
 * @author chuvpilo
 *
 */
public class Simulator {
	
	// Genome compacted in a string
	private static String genome;

	
	/**
	 * Create a simulator for a given genome.
	 * @param myGenome
	 */
	Simulator(String myGenome) {
		genome = myGenome;
	}

	
	/**
	 * Find the score of a given genome via simulation of the ant on the map.
	 * @return Score
	 */
	int score() {
		// start position: (x,y, heading); (0,0) = North-West; heading = East
		Map map = new Map(0, 0, 90);
		Ant ant = new Ant(genome);
		int score = 0;

		// simulate the ant
		for (int step = 0; step < Constants.MAX_STEPS_ON_MAP; step++) {
			int antAction = ant.update(map.isfood());
			score += map.update(antAction);
		}
		return score;
	}
}
