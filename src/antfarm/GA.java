package antfarm;

import java.util.Vector;
import java.util.Random;
 
/**
 * Generic version of a genetic algorithm
 * @author chuvpilo
 *
 */
public class GA {

	// percentage of the population to keep unchanged
	private static double elitismRate;

	// percentage of the population that can pro-create
	private static double selectionRate;

	// crossover and mutation rates, per bit of the genome
	private static double crossoverRate;
	private static double mutationRate;

	/** 
	 * Ctor with some defaults
	 */
	GA() {
		elitismRate = 0.05;
		selectionRate = 0.4;
		crossoverRate = 0.1;
		mutationRate = 0.1;
	}

	/**
	 * Ctor with custom evolution parameters
	 * @param myElitismRate
	 * @param mySelectionRate
	 * @param myCrossoverRate
	 * @param myMutationRate
	 */
	GA(double myElitismRate, double mySelectionRate, double myCrossoverRate,
			double myMutationRate) {
		elitismRate = myElitismRate;
		selectionRate = mySelectionRate;
		crossoverRate = myCrossoverRate;
		mutationRate = myMutationRate;
	}

	/**
	 * Print evolution parameters.
	 */
	static void printParameters() {
		System.out.print("elitismRate=" + elitismRate + ", selectionRate="
				+ selectionRate + ", crossoverRate=" + crossoverRate
				+ ", mutationRate=" + mutationRate + "\n");
	}

	/**
	 * Create random genomes.
	 * @param populationSize
	 * @param genomeLength
	 * @return genomes as strings
	 */
	static Vector<String> init(int populationSize, int genomeLength) {
		Vector<String> genomes = new Vector<String>();
		Random generator = new Random();

		for (int individual = 0; individual < populationSize; individual++) {
			String s = "";
			// build up a random string for a given individual
			for (int bit = 0; bit < genomeLength; bit++) {
				double r = generator.nextDouble();
				if (r < 0.5) {
					s = s + "0";
				} else {
					s = s + "1";
				}
			}
			genomes.add(individual, s);
		}
		return genomes;
	}

	/**
	 * Cross two parents to generate a child
	 * @param parent1
	 * @param parent2
	 * @return child
	 */
	private static String crossover(String parent1, String parent2) {
		Random generator = new Random();

		// find where we begin taking the genome from (50/50 chance)
		int currentGenomeSource;
		if (generator.nextDouble() < 0.5) {
			currentGenomeSource = 1;
		} else {
			currentGenomeSource = 2;
		}

		// create a child
		String child = "";
		// get one bit of the genome from one of the parents
		for (int i = 0; i < parent1.length(); i++) {
			switch (currentGenomeSource) {
			case 1:
				child += parent1.charAt(i);
				break;
			case 2:
				child += parent2.charAt(i);
				break;
			}

			// decide on whether we should change from one parent to the other
			// or not
			if (generator.nextDouble() < crossoverRate) {
				switch (currentGenomeSource) {
				case 1:
					currentGenomeSource = 2;
					break;
				case 2:
					currentGenomeSource = 1;
					break;
				}
			}
		}
		return child;
	}

	// crossover for two parents to generate a child
	private static String mutate(String genome) {
		Random generator = new Random();

		String mutatedGenome = "";
		// mutate one bit at a time
		for (int i = 0; i < genome.length(); i++) {
			// mutate
			if (generator.nextDouble() < mutationRate) {
				switch (genome.charAt(i)) {
				case '0':
					mutatedGenome += '1';
					break;
				case '1':
					mutatedGenome += '0';
					break;
				}
			}
			// don't mutate
			else {
				mutatedGenome += genome.charAt(i);
			}
		}
		return mutatedGenome;

	}

	// one step of evolution: take in old genomes and scores, produce new genomes
	static Vector<String> evolve(int populationSize,
			Vector<String> oldGenomes, Vector<Integer> scores) {
		Vector<String> newGenomes = new Vector<String>();

		// -----------------------------------------------------------------
		// find min and max scores
		int minScore = scores.elementAt(0);
		int maxScore = scores.elementAt(0);

		for (int i = 1; i < populationSize; i++) {
			// found local max
			if (scores.elementAt(i) > maxScore) {
				maxScore = scores.elementAt(i);
			}
			// found local min
			if (scores.elementAt(i) < minScore) {
				minScore = scores.elementAt(i);
			}
		}

		// -----------------------------------------------------------------
		// find the elite
		Vector<Boolean> elite = new Vector<Boolean>();
		for (int i = 0; i < populationSize; i++) {
			// elite: score greater than, say, 90% of scores (for elitismRate = 0.1)
			if (scores.elementAt(i) >= ((maxScore - minScore) * (1 - elitismRate))) {
				elite.add(i, true);
			} else { // not elite
				elite.add(i, false);
			}
		}

		// -----------------------------------------------------------------
		// find the eligible parents
		Vector<Boolean> parents = new Vector<Boolean>();
		for (int i = 0; i < populationSize; i++) {
			// elite: score greater than, say, 90% of scores (for elitismRate = 0.1)
			if (scores.elementAt(i) >= ((maxScore - minScore) * (1 - selectionRate))) {
				parents.add(i, true);
			} else { // not elite
				parents.add(i, false);
			}
		}

		// -----------------------------------------------------------------
		// cross over to generate the whole new population
		Random generator = new Random();
		int count = 0;
		do {
			// find parent 1
			int parent1 = -1;
			do {
				// generate a random index
				int candidate = generator.nextInt(populationSize);
				// see if the guy at this index is an eligible parent
				if (parents.elementAt(candidate)) {
					parent1 = candidate;
				}
			} while (parent1 < 0);

			// find parent 2
			int parent2 = -1;
			do {
				// generate a random index
				int candidate = generator.nextInt(populationSize);
				// see if the guy at this index is an eligible parent
				if (parents.elementAt(candidate)) {
					parent2 = candidate;
				}
			} while (parent2 < 0);

			newGenomes.add(
					count,
					crossover(oldGenomes.elementAt(parent1),
							oldGenomes.elementAt(parent2)));
			count++;
		} while (count < populationSize);

		// -----------------------------------------------------------------
		// pass each genome to mutation function
		for (int i = 0; i < populationSize; i++) {
			newGenomes.add(i, mutate(newGenomes.elementAt(i)));
		}

		// -----------------------------------------------------------------
		// promote the elite to new genomes (overwrite old ones)
		for (int i = 0; i < populationSize; i++) {
			// found a member of the elite
			if (elite.elementAt(i)) {
				newGenomes.add(i, oldGenomes.elementAt(i));
			}
		}

		return newGenomes;
	}
}
