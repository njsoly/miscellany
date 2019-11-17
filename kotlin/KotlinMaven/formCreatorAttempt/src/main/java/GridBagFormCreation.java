
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@SuppressWarnings("unused")
public class GridBagFormCreation {
	public JPanel mainPanel;
	private JTextField searchTextField;
	private JTextArea resultsTextArea;
	private JPanel innerPanel;
	private JScrollPane resultsPane;
	private JTextArea textAreaInfo;
	private JEditorPane htmlPane;

	public GridBagFormCreation() {
		searchTextField.addActionListener(e -> {

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

		mainPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new Color(150, 200, 200), new Color(70, 160, 200)));
		innerPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.RED.darker(), Color.CYAN.darker().darker()));
		resultsPane.setForeground(Color.white);
		resultsTextArea.setDisabledTextColor(new Color(192, 255, 230));
		searchTextField.setForeground(Color.cyan);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("GridBagFormCreation");
		frame.setContentPane(new GridBagFormCreation().mainPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
