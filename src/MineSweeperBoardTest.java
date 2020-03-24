import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author yanya
 *
 */
public class MineSweeperBoardTest {

	/**
	 * create the new MineSweeperBoard object with the constructor 
	 * and test that it is not null
	 */
	@Test
	public void test() {
		MineSweeperBoard newBoard = new MineSweeperBoard(6, 5, 6);
		assertNotNull(newBoard);
	}
	
	/**
	 * create a MineSweeperBoard object and test whether the method "width" 
	 * can return correct value
	 */
	@Test
	public void testWidth() {
		MineSweeperBoard newBoard = new MineSweeperBoard(6, 5, 6);
		assertEquals(6, newBoard.width());
	}

	/**
	 * create a MineSweeperBoard object and test whether the method "height" 
	 * can return correct value
	 */
	@Test
	public void testHeight() {
		MineSweeperBoard newBoard = new MineSweeperBoard(6, 5, 6);
		assertEquals(5, newBoard.height());
	}
	
	/**
	 * create a MineSweeperBoard object and test whether the method "getCell" 
	 * can return correct value when (1) (x, y) is a MINE (2) (x, y) is a COVERED_CELL
	 * (3) (x, y) is an INVALID_CELL
	 */
	@Test
	public void testGetCell() {
		MineSweeperBoard newBoard = new MineSweeperBoard(6, 5, 6);
		String[] board = {"OO+OOO", "OOO+OO", "OOO++O", "OOOOOO", "+OO+OO"};
		newBoard.loadBoardState(board);
		assertEquals(MineSweeperCell.MINE, newBoard.getCell(3, 2));
		assertEquals(MineSweeperCell.COVERED_CELL, newBoard.getCell(1, 2));
		assertEquals(MineSweeperCell.INVALID_CELL, newBoard.getCell(-1, 2));
		assertEquals(MineSweeperCell.INVALID_CELL, newBoard.getCell(6, 3));
		assertEquals(MineSweeperCell.INVALID_CELL, newBoard.getCell(2, -1));
		assertEquals(MineSweeperCell.INVALID_CELL, newBoard.getCell(2, 5));
	}
	
	/**
	 * create a MineSweeperBoard object and test whether the method "uncoverCell" 
	 * can return correct value when (1) (x, y) is a COVERED_CELL (2) (x, y) is a MINE
	 * (3) (x, y) is an INVALID_CELL
	 */
	@Test
	public void testUncoverCell() {
		MineSweeperBoard newBoard = new MineSweeperBoard(6, 5, 6);
		String[] board = {"OO+OOO", "OOO+OO", "OOO++O", "OOOOOO", "+OO+OO"};
		newBoard.loadBoardState(board);
		newBoard.uncoverCell(1, 1);
		assertEquals(MineSweeperCell.ADJACENT_TO_1, newBoard.getCell(1, 1));
		newBoard.uncoverCell(4, 1);
		assertEquals(MineSweeperCell.ADJACENT_TO_3, newBoard.getCell(4, 1));
		newBoard.uncoverCell(3, 2);
		assertEquals(MineSweeperCell.UNCOVERED_MINE, newBoard.getCell(3, 2));
		assertEquals(true, newBoard.isGameLost());
		newBoard.uncoverCell(6, 5);
		assertEquals(MineSweeperCell.INVALID_CELL, newBoard.getCell(6, 5));
	}
	
	/**
	 * create a MineSweeperBoard object and test whether the method "flagCell" 
	 * can return correct value when (1) (x, y) is a COVERED_CELL (2) (x, y) is a FLAG
	 * (3) (x, y) is an MINE (4) (x, y) is an FLAGGED_MINE (5) (x, y) is an INVALID_CELL
	 */
	@Test
	public void testFlagCell() {
		MineSweeperBoard newBoard = new MineSweeperBoard(6, 5, 6);
		String[] board = {"OO+OOO", "OOO+OO", "OOO++O", "OOOOOO", "+OO+OO"};
		newBoard.loadBoardState(board);
		newBoard.flagCell(1, 1);
		assertEquals(MineSweeperCell.FLAG, newBoard.getCell(1, 1));
		newBoard.flagCell(1, 1);
		assertEquals(MineSweeperCell.COVERED_CELL, newBoard.getCell(1, 1));
		newBoard.flagCell(3, 2);
		assertEquals(MineSweeperCell.FLAGGED_MINE, newBoard.getCell(3, 2));
		newBoard.flagCell(3, 2);
		assertEquals(MineSweeperCell.MINE, newBoard.getCell(3, 2));
		newBoard.flagCell(6, 5);
		assertEquals(MineSweeperCell.INVALID_CELL, newBoard.getCell(6, 5));
	}
	
	/**
	 * create a MineSweeperBoard object and test whether the method "isGameLost" 
	 * can return correct value when (1) uncover a COVERED_CELL (2) uncover a MINE
	 */
	@Test
	public void testIsGameLost() {
		MineSweeperBoard newBoard = new MineSweeperBoard(6, 5, 6);
		String[] board = {"OO+OOO", "OOO+OO", "OOO++O", "OOOOOO", "+OO+OO"};
		newBoard.loadBoardState(board);
		newBoard.uncoverCell(1, 1);
		assertEquals(false, newBoard.isGameLost());
		newBoard.uncoverCell(3, 2);
		assertEquals(true, newBoard.isGameLost());
	}
	
	/**
	 * create a MineSweeperBoard object and test whether the method "isGameWon" 
	 * can return correct value when (1) the board has MINE (2) the board has FLAG 
	 * (3) the board has COVERED_CELL (4) the board has UNCOVERED_MINE (5) the game
	 * is won 
	 */
	@Test
	public void testIsGameWon() {
		MineSweeperBoard newBoard = new MineSweeperBoard(6, 5, 6);
		String[] board_a = {" 1+21 ", " 13M31", "  2MM1", "112331", "M11M1 "};
		newBoard.loadBoardState(board_a);
		assertFalse(newBoard.isGameWon());
		String[] board_b = {"O1M21 ", " 13M31", "  2MM1", "112331", "M11M1 "};
		newBoard.loadBoardState(board_b);
		assertFalse(newBoard.isGameWon());
		String[] board_c = {" 1M21 ", " 13M31", "  2MM1", "11F331", "M11M1 "};
		newBoard.loadBoardState(board_c);
		assertFalse(newBoard.isGameWon());
		String[] board_d = {" 1M21 ", " 13M31", "  2MM1", "112331", "M11M1 "};
		newBoard.loadBoardState(board_d);
		assertTrue(newBoard.isGameWon());
		newBoard.loadBoardState(board_a);
		newBoard.uncoverCell(2, 0);
		assertFalse(newBoard.isGameWon());
	}
	
	/**
	 * create a MineSweeperBoard object and test whether the method "numberOfAdjacentMines" 
	 * can return correct value when (1) (x, y) is a COVERED_CELL (2) (x, y) is a INVALID_CELL
	 * (3) when the board contains FLAGGED_MINE or UNCOVERED_MINE, they should be still counted
	 */
	@Test
	public void testNumberOfAdjacentMines() {
		MineSweeperBoard newBoard = new MineSweeperBoard(6, 5, 6);
		String[] board_a = {"OO+OOO", "OOO+OO", "OOO++O", "OOOOOO", "+OO+OO"};
		newBoard.loadBoardState(board_a);
		assertEquals(3, newBoard.numberOfAdjacentMines(2, 1));
		assertEquals(0, newBoard.numberOfAdjacentMines(0, 0));
		assertEquals(0, newBoard.numberOfAdjacentMines(6, 5));
		String[] board_b = {"OOFOOO", "OOO+OO", "OO*++O", "OOOOOO", "+OO+OO"};
		newBoard.loadBoardState(board_b);
		assertEquals(3, newBoard.numberOfAdjacentMines(2, 1));
	}
	
	/**
	 * create a MineSweeperBoard object and test whether the method "revealBoard" 
	 * can return correct value. The FLAG and FLAGGED_MINE are not revealed. Use 
	 * the method "equals" to compare the revealed board with the correct board
	 */
	@Test
	public void testRevealBoard() {
		MineSweeperBoard newBoard = new MineSweeperBoard(6, 5, 6);
		String[] board_a = {"OO+OOO", "OOO+OO", "OOO++O", "OOOOOO", "+OO+OO"};
		newBoard.loadBoardState(board_a);
		newBoard.revealBoard();
		MineSweeperBoard otherBoard = new MineSweeperBoard(6, 5, 6);
		String[] board_b = {" 1*21 ", " 13*31", "  2**1", "112331", "*11*1 "};
		otherBoard.loadBoardState(board_b);
		assertTrue(newBoard.equals(otherBoard));
		String[] board_c = {"O1+OOO", "OOO+OO", "O O+MO", "OOOOOO", "+FO+OO"};
		newBoard.loadBoardState(board_c);
		newBoard.revealBoard();
		String[] board_d = {" 1*21 ", " 13*31", "  2*M1", "112331", "*F1*1 "};
		otherBoard.loadBoardState(board_d);
		assertTrue(newBoard.equals(otherBoard));
	}
	
	/**
	 * create a MineSweeperBoard object and test whether the method "setCell" 
	 * can return correct value when (1) set (x, y) to a valid value (2) (x, y) is 
	 * out of bound (3) set (x, y) to an invalid value
	 */
	@Test
	public void testSetCell() {
		MineSweeperBoard newBoard = new MineSweeperBoard(6, 5, 6);
		String[] board = {"OO+OOO", "OOO+OO", "OOO++O", "OOOOOO", "+OO+OO"};
		newBoard.loadBoardState(board);
		newBoard.setCell(1, 1, MineSweeperCell.ADJACENT_TO_1);
		assertEquals(MineSweeperCell.ADJACENT_TO_1, newBoard.getCell(1, 1));
		newBoard.setCell(-1, 2, MineSweeperCell.FLAGGED_MINE);
		assertEquals(MineSweeperCell.INVALID_CELL, newBoard.getCell(-1, 2));
		newBoard.setCell(6, 3, MineSweeperCell.FLAGGED_MINE);
		assertEquals(MineSweeperCell.INVALID_CELL, newBoard.getCell(6, 3));
		newBoard.setCell(2, -1, MineSweeperCell.FLAGGED_MINE);
		assertEquals(MineSweeperCell.INVALID_CELL, newBoard.getCell(2, -1));
		newBoard.setCell(3, 5, MineSweeperCell.FLAGGED_MINE);
		assertEquals(MineSweeperCell.INVALID_CELL, newBoard.getCell(3, 5));
	}
}
