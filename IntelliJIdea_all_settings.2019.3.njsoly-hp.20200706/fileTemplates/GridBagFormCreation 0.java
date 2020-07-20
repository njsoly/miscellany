
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@SuppressWarnings("unused")
public class ${NAME} {
	public JPanel mainPanel;
	private JTextField searchTextField;
	private JTextArea resultsTextArea;
	private JPanel innerPanel;
	private JScrollPane resultsPane;
	private JEditorPane textAreaInfo;
	private JEditorPane editorPane1;

	public ${NAME}() {
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
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("${NAME}");
		frame.setContentPane(new ${NAME}().mainPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	void createUIComponents() {
		textAreaInfo = new JEditorPane("text/html", "<div><b>aoeu</b> snth</div> <hr /> <h3>aoeusntahoeusnth</h3>");
	}
}
