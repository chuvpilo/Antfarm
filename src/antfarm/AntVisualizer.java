package antfarm;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.Font;

/**
 * Basic UI to visualize the ant.
 * @author chuvpilo
 *
 */
public class AntVisualizer extends JFrame {
	private static final long serialVersionUID = 6101790828222399336L;
	private JButton b = new JButton("Step");
	private JTextArea t = new JTextArea(35, 70);

	int score = 0;
	int step = 0;

	
	/**
	 * Create an ant visualizer for a given genome.
	 * @param genome
	 */
	public AntVisualizer(String genome) {
		// start position: (x,y, heading); (0,0) = North-West; heading = East
		final Map map = new Map(0, 0, 90);

		final Ant ant = new Ant(genome);

		Font font = new Font("Monospaced", Font.PLAIN, 12);
		t.setFont(font);

		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.print("Step: " + step + "\n");

				if (step != 0) {
					int antAction = ant.update(map.isfood());
					score += map.update(antAction);
				}

				String s2 = new String();
				s2 += "Step: " + step + "\n";
				s2 += map.getAsString();
				s2 += "Score=" + score + "\n";
				t.setText(s2);

				if (score == 89) {
					System.out.print("Victory!\n");
				}
				step++;
			}
		});
		setLayout(new FlowLayout());
		add(new JScrollPane(t));
		add(b);
	}

	public static void main(String[] args) {
		SwingConsole.run(new AntVisualizer(Constants.MANUAL_GENOME), 605, 675);
	}
}