package optionpane;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Used for testing pawn promotion.
 */
public interface OptionPane {
	int showOptionDialog(JPanel component, JLabel label, String title, int optionType, int messageType, ImageIcon icon, Object[] options, Object initialValue);
}


