
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static java.awt.Color.*;
import static java.awt.Font.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

@SuppressWarnings("unused")
public class ${NAME} {
	public JPanel mainPanel;
	private JTextField searchTextField;
	private JTextArea resultsTextArea;
	private JPanel innerPanel;
	private JScrollPane resultsPane;
	private JTextArea textAreaInfo;
	private JEditorPane htmlPane;

	public static final Color BRIGHT_SEAFOAM = new Color(78, 255, 190);
	public static final Color DEEP_GREEN_BLUE = new Color(0, 43, 29);
	public static final Color DEEP_BLUE = new Color(0, 31, 43);
	public static final Color LOSER_BLUE = new Color(180, 228, 245);
	public static final Color BETTER_MAGENTA_PINK = new Color(255, 96, 155);
	public static final Color BETTER_MAGENTA_MED_LIGHT = new Color(253, 159, 255);
	public static final Color BETTER_PURPLE = new Color(164, 93, 249);
	public static final Color MED_GRAY_BLUE_GREEN = new Color(50, 86, 99);
	public static final Color BETTER_TEAL = new Color(0, 212, 201);
	public static final Color SKY_BLUE_1 = new Color(116, 192, 237);
	public static final Color SKY_BLUE_2 = new Color(69, 170, 237);
	public static final Color SKY_BLUE_3 = new Color(0, 160, 230);
	public static final Color SOFTER_CYAN = new Color(132, 249, 255);
	public static final Color SOFTER_SEAFOAM = new Color(192, 255, 230);
	public static final Color SLATE = new Color(25, 26, 34);

	public ${NAME}() {
		searchTextField.addActionListener(e -> {
			System.out.println("action in " + e.getActionCommand());
		});
		searchTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				super.keyTyped(e);
			}
		});
		searchTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
			}
		});

		mainPanel.setBorder(BorderFactory.createLineBorder(DEEP_GREEN_BLUE, 2, true));
		resultsPane.setBackground(DEEP_GREEN_BLUE); // med cobalt blue
		resultsPane.setForeground(SOFTER_CYAN); // bright-ish cyan
		resultsPane.setBorder(
			BorderFactory.createTitledBorder(
				BorderFactory.createBevelBorder(
						BevelBorder.RAISED, CYAN.brighter(), CYAN.darker()
				),
				"Results",
				TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Consolas", BOLD, 16),
				new Color(65, 255, 133).brighter() // bright green blue
			)
		);
		resultsTextArea.setBackground(BLACK);
		resultsTextArea.setForeground(SOFTER_SEAFOAM);
		resultsTextArea.setDisabledTextColor(SOFTER_SEAFOAM);
		resultsTextArea.setBorder(BorderFactory.createLineBorder(SKY_BLUE_3, 1));

		searchTextField.setBackground(DEEP_BLUE);
		searchTextField.setForeground(WHITE);
		searchTextField.setFont(getFont("Consolas"));
		searchTextField.setBorder(BorderFactory.createLineBorder(BRIGHT_SEAFOAM)); // bright seafoam

		textAreaInfo.setBackground(BLACK);

		doStandardComponentBorder(innerPanel);
		doStandardComponentBorder(textAreaInfo);
		doStandardComponentBorder(htmlPane);

		htmlPane.setBackground(SLATE);
		htmlPane.setForeground(SOFTER_CYAN);
		htmlPane.setBorder(BorderFactory.createLineBorder(BETTER_MAGENTA_PINK, 1, false));

		for(JTextComponent x : new JTextComponent[]{resultsTextArea, searchTextField, htmlPane, textAreaInfo}){
			x.setSelectionColor(BETTER_PURPLE);
		}
	}

	protected void doStandardComponentBorder(JComponent jc) {
		jc.setBorder(BorderFactory.createLineBorder(LOSER_BLUE, 2, true));
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("${NAME}");
		frame.setContentPane(new ${NAME}().mainPanel);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	public JTextArea getInfoTextArea () {
		return textAreaInfo;
	}

	void createUIComponents() {
		htmlPane = new JEditorPane("text/html", "<div><b>aoeu</b> snth</div> <hr /> <h3>aoeusntahoeusnth</h3>");

	}
}
