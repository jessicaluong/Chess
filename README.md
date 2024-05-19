# Chess
This is a two-player chess game with a checkmate detector. This program was written using Java object-oriented programming, Java Swing GUI components and the JUnit testing framework.
<p align="center">
<img width="500" alt="image" src="https://user-images.githubusercontent.com/96930184/149872795-1b989d2a-258c-4859-a869-ce46b5c1f0f4.png">
</p>

## User Interface
- Squares change colour to indicate which piece is being moved to which square
- Player turn is shown on the frame

<p align="center">
<img width="500" alt="chessGUI" src="https://user-images.githubusercontent.com/96930184/149879890-07ccd63b-c85b-4ccd-b458-249d8083a4fa.png">
</p>


## Pawn Promotion
A dialog shows up when the pawn reaches the end of the board to prompt the player to pick the piece they want to promote the pawn to.

### Testing

The GUI component used for this is a JOptionPane which required a mock JOptionPane to automate testing. This uses an interface:

```java
public interface OptionPane {
	int showOptionDialog(JPanel component, JLabel label, String title, int optionType, int messageType, ImageIcon icon, Object[] options, Object initialValue);
}
```
This default implementation is called when the program is run normally:

```java
public class DefaultOptionPane implements OptionPane {
	@Override
	public int showOptionDialog(JPanel component, JLabel label, String title, int optionType, int messageType, ImageIcon icon, Object[] options, Object initialValue) {
		return JOptionPane.showOptionDialog(null, label, "Promotion!", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
	}
```

Example of a MockOptionPane that is used during testing:

```java
public class BishopMockOptionPane extends DefaultOptionPane {
	@Override
	public int showOptionDialog(JPanel component, JLabel label, String title, int optionType, int messageType,
			ImageIcon icon, Object[] options, Object initialValue) {
		return 2;
	}
}
```
## Checkmate Detector
- Checks are tracked to prevent players from moving into check or staying in check
- The checkmate detector uses move generation and move validation to determine when there are no more legal moves

<p align="center">
<img width="500" alt="image" src="https://user-images.githubusercontent.com/96930184/149872750-397cd76b-d2be-47c0-8380-9279b3e418aa.png">
</p>

## Features To Add
Two moves that still need to be added to the game are en passant and castling. Implementing this requires the tracking of all previous moves.

## Credits
The images used for the chess pieces in this project are sourced from the ["Rules of Chess" page on Wikipedia](https://en.wikipedia.org/wiki/Rules_of_chess#Initial_setup), specifically from the "Initial Setup" section. 