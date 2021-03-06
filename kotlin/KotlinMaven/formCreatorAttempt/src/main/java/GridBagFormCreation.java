import kotlin.Pair;
import njsoly.log.SetupLogger;
import org.apache.log4j.Logger;

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
import static java.awt.Font.BOLD;
import static java.awt.Font.PLAIN;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import static njsoly.util.ColorUtil.*;

@SuppressWarnings("unused")
public class GridBagFormCreation {
	public JPanel mainPanel;
	protected JTextArea inputTextArea;
	protected JTextArea resultsTextArea;
	private JPanel innerPanel;
	private JScrollPane resultsPane;
	private JTextArea textAreaInfo;
	private JEditorPane htmlPane;
	protected HTMLDocument htmlDocument;

	private final static Logger LOGGER = SetupLogger.setupLogger(GridBagFormCreation.class);

	protected static final HashMap<String, Pair<LocalDateTime, LocalDateTime>> TIMINGS = new HashMap<>();

	static final boolean DEBUG = true;

	static {
		if(DEBUG)
			LOGGER.debug("GridBagFormCreation class loaded: " + theTime());
	}

	public static String theTime () {
		String timeWithNanos = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss.n"));
		return timeWithNanos.substring(0, timeWithNanos.length() - 6);
	}

	protected void addStandardKeyListeners() {
		if(DEBUG) LOGGER.debug("GridBagFormCreation(): adding key listener " + theTime());
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				inputTextArea.addKeyListener(new KeyAdapter() {
					@Override
					public void keyTyped(KeyEvent e) {
						super.keyTyped(e);
					}
				});

				inputTextArea.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						super.keyPressed(e);
						if (e.isActionKey()) {
							LOGGER.debug("e (" + e.getKeyCode() + ") is an action key.");
						} else {
							LOGGER.debug("e (" + e.getKeyCode() + ") is NOT an action key.");
						}
						if (e.getKeyCode() == KeyEvent.VK_ENTER) {
							LOGGER.debug("pressed ENTER.");
						}
					}
				});

				if(DEBUG) LOGGER.debug("gridBagFormCreation(): added key listener");
			}
		});


	}

	public GridBagFormCreation() {
		if(DEBUG) LOGGER.debug("GridBagFormCreation() " + theTime());


		SwingUtilities.invokeLater(() -> {
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
			resultsTextArea.setDisabledTextColor(SOFTER_SEAFOAM);
			resultsTextArea.setBorder(BorderFactory.createLineBorder(SKY_BLUE_3, 1));

			inputTextArea.setBorder(BorderFactory.createLineBorder(BRIGHT_SEAFOAM)); // bright seafoam

			textAreaInfo.setBackground(BLACK);

			this.addStandardKeyListeners();

			setBgFg(resultsTextArea, BLACK, SOFTER_SEAFOAM);
			setBgFg(inputTextArea, DEEP_BLUE, WHITE);
			setBgFg(htmlPane, DEEP_GREEN_BLUE, SOFTER_CYAN);

			htmlPane.setDisabledTextColor(GRAY);
			htmlPane.setSelectedTextColor(WHITE);

			htmlDocument = (HTMLDocument) (htmlPane.getDocument());
			htmlDocument.getStyleSheet().addRule("html body { color: #FFFFFF }");

			doStandardComponentBorder(innerPanel, textAreaInfo, htmlPane);
			setMargins(3, textAreaInfo, inputTextArea, resultsTextArea);

			setSelectionBackground(BETTER_PURPLE, resultsTextArea, inputTextArea, htmlPane, textAreaInfo);

			for(JComponent jc : new JComponent[]{resultsTextArea, inputTextArea, textAreaInfo, htmlPane}){
				jc.setFont(new Font("Consolas", PLAIN, 14));
			}
		});


		LOGGER.debug("GridBagFormCreation(): END " + theTime());
	}

	void setBgFg(JComponent jc, Color bg, Color fg) {
		SwingUtilities.invokeLater(() -> {
			LOGGER.debug("setting BG & FG (" + jc.getName() + "): END " + theTime());
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
	 * Given a list of {@link JTextComponent}s, change each of their margins to {@code n}.
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
			if(DEBUG) LOGGER.debug("doStandardComponentBorder(" + jcz.getClass().getSimpleName() + "): " + theTime());
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
				LOGGER.debug("Window is visible [" + times + "]: " + window.isVisible());
				times++;
				if(times > maxTimes) {
					this.cancel();
					if(!window.isVisible()) {
						System.exit(1);
					}
				}
				if (window.isVisible()) {
					LOGGER.info("window is visible.");
					this.cancel();
				}
			}
		}, 0, 2000);

	}

	/**
	 * Make a {@code GridBagFormCreation}, pack it, and set it visible.
	 * @param args not used.
	 */
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
				if(DEBUG) LOGGER.debug("setting window visible: " + theTime());
				super.setVisible(visible);
				if(DEBUG) LOGGER.debug("window is visible " + (visible) + ": " + theTime());
			}

			@Override
			public void pack(){
				if(DEBUG) LOGGER.debug("packing window: " + theTime());
				super.pack();
				if(DEBUG) LOGGER.debug("packed window: " + theTime());
			}

			{
				this.addContainerListener(new ContainerListener(){
					@Override
					public void componentAdded(ContainerEvent e) {
						if(DEBUG) LOGGER.debug("componentAdded: " + e);
					}

					@Override
					public void componentRemoved(ContainerEvent e) {
						if(DEBUG) LOGGER.debug("componentRemoved: " + e);
					}
				});

				this.addComponentListener(new ComponentListener(){

					@Override
					public void componentResized(ComponentEvent e) {
						LOGGER.debug("component resized: " + e.getComponent().getName() + " " + e.paramString());
					}

					@Override
					public void componentMoved(ComponentEvent e) {
						LOGGER.debug("component moved: " + e.getComponent().getName() + " " + e.paramString());

					}

					@Override
					public void componentShown(ComponentEvent e) {
						LOGGER.debug("component shown: " + e.getComponent().getName() + " " + e.paramString());

					}

					@Override
					public void componentHidden(ComponentEvent e) {
						LOGGER.debug("component hidden: " + e.getComponent().getName() + " " + e.paramString());

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

	public JTextArea getInputTextArea() {
		return inputTextArea;
	}

	void createUIComponents() {
		if(DEBUG) LOGGER.debug("createUIComponents() " + theTime());
	}
}
