package optionpane;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class QueenMockOptionPane extends DefaultOptionPane {
	
	@Override
	public int showOptionDialog(JPanel component, JLabel label, String title, int optionType, int messageType,
			ImageIcon icon, Object[] options, Object initialValue) {
		return 0;
	}
	
}