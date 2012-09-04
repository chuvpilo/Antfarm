package antfarm;

import java.util.Vector;

/**
 * Wrapper class to run evolution.
 * @author chuvpilo
 *
 */
public class Evolution {

	/**
	 * Get the best genome of a population
	 * @param populationSize
	 * @param scores
	 * @return genome number
	 */
	private static int bestGenomeNumber(int populationSize, Vector<Integer> scores) {
		int scoreMax = scores.elementAt(0);
		int posMax = 0;
		for (int i = 1; i < populationSize; i++) {
			if (scores.elementAt(i) > scoreMax) {
				scoreMax = scores.elementAt(i);
				posMax = i;
			}
		}
		return posMax;
	}

	/**
	 * Main.
	 * @param args
	 */
	public static void main(String[] args) {
		// vector of genomes (each is a string)
		Vector<String> genomes = new Vector<String>();

		// vector of scores (for each genome)
		Vector<Integer> scores = new Vector<Integer>();

		// -----------------------------------------------------------------
		// initialize genomes to random strings
		@SuppressWarnings("unused")
		GA ga = new GA();
		genomes = GA.init(Constants.POPULATION_SIZE, Constants.GENOME_LENGTH);
		// get scores for each of the genomes
		for (int i = 0; i < Constants.POPULATION_SIZE; i++) {
			// get the score by running the ant with this genome on the map
			Simulator s = new Simulator(genomes.elementAt(i));
			scores.add(i, s.score());
		}

		// -----------------------------------------------------------------
		// evolve
		String bestGenome = "";
		for (int generation = 1; generation <= Constants.EVOLUTION_STEPS; generation++) {
			genomes = GA.evolve(Constants.POPULATION_SIZE, genomes, scores);
			// get scores for each of the genomes
			for (int i = 0; i < Constants.POPULATION_SIZE; i++) {
				// get the score by running the ant with this genome on the map
				Simulator s = new Simulator(genomes.elementAt(i));
				scores.add(i, s.score());
			}

			System.out.print("generation="
					+ generation
					+ ": bestScore = "
					+ scores.elementAt(bestGenomeNumber(
							Constants.POPULATION_SIZE, scores)) + ", ");

			int avgFitness = 0;
			for (int j = 0; j < Constants.POPULATION_SIZE; j++) {
				avgFitness += scores.elementAt(j);
			}
			avgFitness = avgFitness / Constants.POPULATION_SIZE;
			System.out.print("avgFitness="
					+ avgFitness
					+ ", bestGenome = "
					+ genomes.elementAt(bestGenomeNumber(
							Constants.POPULATION_SIZE, scores)) + "\n");

			bestGenome = genomes.elementAt(bestGenomeNumber(
					Constants.POPULATION_SIZE, scores));

		}
		
		SwingConsole.run(new AntVisualizer(bestGenome), 605, 675);
	}
}
