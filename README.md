# Chess
This is a two-player chess game with a checkmate detector. This program was written using Java object-oriented programming, Java Swing GUI components and the JUnit testing framework.
<p align="center">
<img width="500" alt="image" src="https://user-images.githubusercontent.com/96930184/149872795-1b989d2a-258c-4859-a869-ce46b5c1f0f4.png">
</p>

## Installation

This section provides instructions on how to set up and run the application through the command line interface, as well as how to execute tests.

### Requirements
- Java Development Kit (JDK)

### Run Application

1. Clone the repository:
    ```bash
    git clone https://github.com/jessicaluong/Chess.git
    ```

2. Navigate to the project directory:
    ```bash
    cd Chess
    ```

3. Create the output directory: 
    ```bash
    mkdir -p bin
    ```

4. Compile all source files from the 'game' and 'optionpane' packages: 
    ```bash
    javac -d bin src/game/*.java src/optionpane/*.java
    ```

5. Run the application: 
    ```bash
    java -cp bin game.Game
    ```

### Run Tests

1. Create the library directory if it doesn't exist:
    ```bash
    mkdir -p lib
    ```

2. Download JUnit Platform Console Standalone JAR:
    ```bash
    curl -L "https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.11.0-M2/junit-platform-console-standalone-1.11.0-M2.jar" -o lib/junit-platform-console-standalone-1.11.0-M2.jar
    ```

3. Compile the tests: 
    ```bash
    javac -d bin -cp "src:lib/junit-platform-console-standalone-1.11.0-M2.jar" src/tests/*
    ```

4. Execute the tests: 
    ```bash
    java -jar lib/junit-platform-console-standalone-1.11.0-M2.jar --class-path bin --scan-classpath
    ```
## Features 

### User Interface
- Squares change colour to indicate which piece is being moved to which square
- Player turn is shown on the frame

<p align="center">
<img width="500" alt="chessGUI" src="https://user-images.githubusercontent.com/96930184/149879890-07ccd63b-c85b-4ccd-b458-249d8083a4fa.png">
</p>


### Pawn Promotion
A dialog shows up when the pawn reaches the end of the board to prompt the player to pick the piece they want to promote the pawn to.

<p align="center">
<img width="500" alt="pawn_promotion" src="https://github.com/jessicaluong/Chess/assets/96930184/ab36d7e1-64c7-42af-af26-96b36861ad41">
</p>

### Checkmate Detector
- Checks are tracked to prevent players from moving into check or staying in check
- The checkmate detector uses move generation and move validation to determine when there are no more legal moves

<p align="center">
<img width="500" alt="image" src="https://user-images.githubusercontent.com/96930184/149872750-397cd76b-d2be-47c0-8380-9279b3e418aa.png">
</p>

## Testing

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
## Features To Add
Two moves that still need to be added to the game are en passant and castling. Implementing this requires the tracking of all previous moves.

## Credits
The images used for the chess pieces in this project are sourced from the ["Rules of Chess" page on Wikipedia](https://en.wikipedia.org/wiki/Rules_of_chess#Initial_setup), specifically from the "Initial Setup" section. 
