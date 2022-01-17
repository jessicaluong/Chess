package optionpane;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class DefaultOptionPane implements OptionPane{

	@Override
	public int showOptionDialog(JPanel component, JLabel label, String title, int optionType, int messageType, ImageIcon icon, Object[] options, Object initialValue) {

		return JOptionPane.showOptionDialog(null, label, "Promotion!", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
	}

	
}