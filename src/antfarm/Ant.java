package antfarm;

/**
 * finite state machine that represents the brain
 * @author chuvpilo
 *
 */
class FSM {
	private final int N = 16; // # states
	private int startState;
	private int currentState;
	private int[] transitionsFood = new int[N];
	private int[] actionsFood = new int[N];
	private int[] transitionsNoFood = new int[N];
	private int[] actionsNoFood = new int[N];

	private static int fourbits2int(int p3, int p2, int p1, int p0) {
		return (p0 * 1 + p1 * 2 + p2 * 4 + p3 * 8);
	}

	private static int twobits2int(int p1, int p0) {
		return (p0 * 1 + p1 * 2);
	}

	// constructor
	FSM(String genome) {
		// System.out.print("Initializing the FSM...\n");

		// parse the genome:

		// convert string to bits
		int[] bits = new int[genome.length()];
		for (int i = 0; i < genome.length(); i++) {
			bits[i] = Integer.parseInt(genome.substring(i, i + 1));
		}

		// group bits together to get states
		startState = fourbits2int(bits[0], bits[1], bits[2], bits[3]);
		currentState = startState;

		// go through all states
		for (int n = 0; n < N; n++) {
			transitionsFood[n] = fourbits2int(bits[4 + n * 12],
					bits[5 + n * 12], bits[6 + n * 12], bits[7 + n * 12]);
			actionsFood[n] = twobits2int(bits[8 + n * 12], bits[9 + n * 12]);
			transitionsNoFood[n] = fourbits2int(bits[10 + n * 12],
					bits[11 + n * 12], bits[12 + n * 12], bits[13 + n * 12]);
			actionsNoFood[n] = twobits2int(bits[14 + n * 12],
					bits[15 + n * 12]);
		}

		// print();
	}

	/**
	 * Print FSM
	 */
	void print() {
		System.out.print("FSM:\n");
		System.out.print("startState=" + startState + "\n");
		System.out.print("state\tfood\tnoFood\n");
		for (int n = 0; n < N; n++) {

			System.out.print(n + ":\t");

			// food
			System.out.print(transitionsFood[n]);
			switch (actionsFood[n]) {
			case 0:
				System.out.print("N ");
				break;
			case 1:
				System.out.print("F ");
				break;
			case 2:
				System.out.print("L ");
				break;
			case 3:
				System.out.print("R ");
				break;
			}

			System.out.print("\t");

			// no food
			System.out.print(transitionsNoFood[n]);
			switch (actionsNoFood[n]) {
			case 0:
				System.out.print("N ");
				break;
			case 1:
				System.out.print("F ");
				break;
			case 2:
				System.out.print("L ");
				break;
			case 3:
				System.out.print("R ");
				break;
			}

			System.out.print("\n");

		}
	}

	/**
	 * Update internal state and return what action the ant should take
	 * @param food
	 * @return action
	 */
	int update(boolean food) {
		int action;

		// decision
		if (food) {
			action = actionsFood[currentState];
			currentState = transitionsFood[currentState];
		} else {
			action = actionsNoFood[currentState];
			currentState = transitionsNoFood[currentState];
		}
		return action;
	}
}


/**
 * Ant class.
 * @author chuvpilo
 *
 */
public class Ant {
	
	// Ant's genome
	private String genome;
	
	// Finite state machine representing the brain of an ant
	private FSM fsm;

	/**
	 * Create an ant for a given genome
	 * @param myGenome
	 */
	Ant(final String myGenome) {
		// System.out.print("Initializing the ant with genome '" + myGenome + "'...\n");
		genome = myGenome;
		fsm = new FSM(genome);
	}

	int update(boolean food) {
		return fsm.update(food);
	}
}
