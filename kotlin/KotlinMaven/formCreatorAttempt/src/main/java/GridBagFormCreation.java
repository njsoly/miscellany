import kotlin.Pair;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.JTextComponent;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.StyleSheet;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import static java.awt.Color.*;
import static java.awt.Font.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

@SuppressWarnings("unused")
public class GridBagFormCreation {
	public JPanel mainPanel;
	private JTextArea searchTextArea;
	private JTextArea resultsTextArea;
	private JPanel innerPanel;
	private JScrollPane resultsPane;
	private JTextArea textAreaInfo;
	private JEditorPane htmlPane;
	protected HTMLDocument htmlDocument;

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

	protected static final HashMap<String, Pair<LocalDateTime, LocalDateTime>> TIMINGS = new HashMap<>();

	static final boolean DEBUG = true;

	static {
		if(DEBUG)
			System.out.println("GridBagFormCreation class loaded: " + theTime());
	}

	public static String theTime () {
		String timeWithNanos = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss.n"));
		return timeWithNanos.substring(0, timeWithNanos.length() - 6);
	}

	protected void addStandardKeyListeners() {
		if(DEBUG) System.out.println("GridBagFormCreation(): adding key listener " + theTime());
		searchTextArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				super.keyTyped(e);
			}
		});

		searchTextArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				if (e.isActionKey()) {
					System.out.println("e (" + e.getKeyCode() + ") is an action key.");
				} else {
					System.out.println("e (" + e.getKeyCode() + ") is NOT an action key.");
				}
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					System.out.println("pressed ENTER.");
				}
			}
		});

		if(DEBUG) System.out.println("gridBagFormCreation(): added key listener");

	}

	public GridBagFormCreation() {
		if(DEBUG) System.out.println("GridBagFormCreation() " + theTime());

		this.addStandardKeyListeners();

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
//		resultsTextArea.setBackground(BLACK);
//		resultsTextArea.setForeground(SOFTER_SEAFOAM);

		setFgBg(resultsTextArea, SOFTER_SEAFOAM, BLACK);
		resultsTextArea.setDisabledTextColor(SOFTER_SEAFOAM);
		resultsTextArea.setBorder(BorderFactory.createLineBorder(SKY_BLUE_3, 1));

		setFgBg(searchTextArea, WHITE, DEEP_BLUE);
//		searchTextField.setBackground(DEEP_BLUE);
//		searchTextField.setForeground(WHITE);
		searchTextArea.setBorder(BorderFactory.createLineBorder(BRIGHT_SEAFOAM)); // bright seafoam

		textAreaInfo.setBackground(BLACK);

		htmlPane.setBackground(DEEP_GREEN_BLUE);
		htmlPane.setForeground(SOFTER_CYAN);
		htmlPane.setDisabledTextColor(GRAY);
		htmlPane.setSelectedTextColor(WHITE);
		htmlDocument = (HTMLDocument) (htmlPane.getDocument());
		htmlDocument.getStyleSheet().addRule("html body { color: #FFFFFF }");

		doStandardComponentBorder(innerPanel, textAreaInfo, htmlPane);
		setMargins(3, textAreaInfo, searchTextArea, resultsTextArea);

		setSelectionBackground(BETTER_PURPLE, resultsTextArea, searchTextArea, htmlPane, textAreaInfo);

		for(JComponent jc : new JComponent[]{resultsTextArea, searchTextArea, textAreaInfo, htmlPane}){
			jc.setFont(new Font("Consolas", PLAIN, 14));
		}

		System.out.println("GridBagFormCreation(): END " + theTime());
	}

	void setFgBg (JComponent jc, Color fg, Color bg) {
		SwingUtilities.invokeLater(() -> {
			System.out.println("setting BG & FG (" + jc.getName() + "): END " + theTime());
			jc.setForeground(fg);
			jc.setBackground(bg);
		});
	}

	protected StyleSheet getHtmlPaneStylesheet() {
		return htmlDocument.getStyleSheet();
	}

	public void addCssRuleToHtmlPane(String cssRule){
		htmlDocument.getStyleSheet().addRule(cssRule);
	}

	void setSelectionBackground(Color c, JTextComponent... jtcz){
		SwingUtilities.invokeLater(() -> {
			for(JTextComponent jtc : jtcz){
				jtc.setSelectionColor(c);
			}
		});
	}

	/**
	 * Given a list of {@link JTextComponent}s, change eac hof their margins to {@code n}.
	 * @param n
	 * @param jtcz
	 */
	protected void setMargins(int n, JTextComponent... jtcz){
		SwingUtilities.invokeLater(() -> {
			for (JTextComponent jtc : jtcz){
				SwingUtilities.invokeLater(() -> jtc.setMargin(new Insets(n, n, n, n)));
			}
		});
	}

	protected void doStandardComponentBorder(JComponent... jcz) {
		SwingUtilities.invokeLater(() -> {
			if(DEBUG) System.out.println("doStandardComponentBorder(" + jcz.getClass().getSimpleName() + "): " + theTime());
			for(JComponent jc : jcz){
				jc.setBorder(BorderFactory.createLineBorder(BETTER_MAGENTA_PINK, 2, true));
			}
		});
	}

	private void isVisibleLoop(final JFrame window, final int maxTimes) {
		Timer t = new Timer(){
			int times = 0;
			int max = maxTimes;

		};
		t.scheduleAtFixedRate(new TimerTask() {

			int times = 0;
			final int max = maxTimes;
			@Override
			public void run() {
				System.out.println("Window is visible [" + times + "]: " + window.isVisible());
				times++;
				if(times > maxTimes) {
					this.cancel();
					if(!window.isVisible()) {
						System.exit(1);
					}
				}
			}
		}, 0, 2000);

	}

	public static void main(String[] args) {
		GridBagFormCreation form = new GridBagFormCreation();
		JFrame window = form.getGridBagFormCreationJframe();
		SwingUtilities.invokeLater(() -> {
			window.pack();
			window.setVisible(true);
		});
	}

	public JFrame getGridBagFormCreationJframe () {

		JFrame frame = new JFrame("GridBagFormCreation"){
			@Override
			public void setVisible(boolean visible){
				if(DEBUG) System.out.println("setting window visible: " + theTime());
				super.setVisible(visible);
				if(DEBUG) System.out.println("window is visible " + (visible) + ": " + theTime());
			}

			@Override
			public void pack(){
				if(DEBUG) System.out.println("packing window: " + theTime());
				super.pack();
				if(DEBUG) System.out.println("packed window: " + theTime());
			}

			{
				this.addContainerListener(new ContainerListener(){
					@Override
					public void componentAdded(ContainerEvent e) {
						if(DEBUG) System.out.println("componentAdded: " + e);
					}

					@Override
					public void componentRemoved(ContainerEvent e) {
						if(DEBUG) System.out.println("componentRemoved: " + e);
					}
				});

				this.addComponentListener(new ComponentListener(){

					@Override
					public void componentResized(ComponentEvent e) {
						System.out.println("component resized: " + e.getComponent().getName() + " " + e.paramString());
					}

					@Override
					public void componentMoved(ComponentEvent e) {
						System.out.println("component moved: " + e.getComponent().getName() + " " + e.paramString());

					}

					@Override
					public void componentShown(ComponentEvent e) {
						System.out.println("component shown: " + e.getComponent().getName() + " " + e.paramString());

					}

					@Override
					public void componentHidden(ComponentEvent e) {
						System.out.println("component hidden: " + e.getComponent().getName() + " " + e.paramString());

					}
				});
			}
		};
		isVisibleLoop(frame, 20);
		frame.setContentPane(new GridBagFormCreation().mainPanel);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.pack();
		return frame;
	}

	public JTextArea getInfoTextArea () {
		return textAreaInfo;
	}

	public JTextArea getInputArea () {
		return searchTextArea;
	}

	void createUIComponents() {
		if(DEBUG) System.out.println("createUIComponents() " + theTime());
		htmlPane = new JEditorPane("text/html", "<div><b>aoeu</b> snth</div> <hr /> <h3>aoeusntahoeusnth</h3>");
	}
}
