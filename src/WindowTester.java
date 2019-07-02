import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class WindowTester{

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ViewFrame frame = new ViewFrame();
			}
		});
	}
}

class ViewFrame extends JFrame {
	public static final int X = 800;
	public static final int Y = 800;
	
	// change to wait time (in ms) between action events
	// if 0, then no timer will be created
	public static final int PROCESS_TIMER = 25;
	
	public ViewFrame() {
		this.setTitle("To be set");
		this.setPreferredSize(new Dimension(X, Y));
		JPanel panel = new LayoutPanel(); // set to new JPanel class you create
		panel.setFocusable(true);
		panel.requestFocusInWindow();
		
		if (panel instanceof KeyListener) {
			KeyListener kl = (KeyListener) panel;
			panel.addKeyListener(kl);
		}
		
		if (panel instanceof ActionListener && PROCESS_TIMER > 0) {
			ActionListener al = (ActionListener) panel;
			Timer timer = new Timer(30, al);
			timer.setInitialDelay(200);
			timer.start();
		}
		
		this.setContentPane(panel);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}
