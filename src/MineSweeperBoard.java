
/**
 * @author yanya
 *
 */
public class MineSweeperBoard extends MineSweeperBoardBase {
	private MineSweeperCell[][] board;
	private boolean isGameLost = false;

	/**
	 * The constructor places exactly number_of_mines randomly on the board. Apart
	 * from the cells containing a mine (​MineSweeperCell.MINE​), all other cells
	 * must be covered (​MineSweeperCell.COVERED_CELL​)
	 * 
	 * @param width
	 * @param height
	 * @param number_of_mines
	 */
	public MineSweeperBoard(int width, int height, int number_of_mines) {
		this.board = new MineSweeperCell[height][width];

		for (int i = 0; i < height; ++i) {
			for (int j = 0; j < width; ++j) {
				this.board[i][j] = MineSweeperCell.COVERED_CELL;
			}
		}

		
		while(number_of_mines > 0) { 
			int i = (int) (Math.random() * height); 
			int j = (int) (Math.random() * width); 
			if(this.board[i][j] == MineSweeperCell.COVERED_CELL) { 
				 this.board[i][j] = MineSweeperCell.MINE;
				 number_of_mines--; 
			} 
		} 	 
	}

	@Override
	public int width() {
		return this.board[0].length;
	}

	@Override
	public int height() {
		return this.board.length;
	}

	@Override
	public MineSweeperCell getCell(int x, int y) {
		if (x < this.width() && x >= 0 && y < this.height() && y >= 0) {
			return this.board[y][x];
		} else {
			return MineSweeperCell.INVALID_CELL;
		}

	}

	@Override
	public void uncoverCell(int x, int y) {
		switch (getCell(x, y)) {
		case COVERED_CELL:
			setCell(x, y, MineSweeperCell.adjacentTo(this.numberOfAdjacentMines(x, y)));
			break;
		case MINE:
			setCell(x, y, MineSweeperCell.UNCOVERED_MINE);
			isGameLost = true;
			break;
		default:
			break;
		}
		return;
	}

	@Override
	public void flagCell(int x, int y) {
		switch (getCell(x, y)) {
		case MINE:
			setCell(x, y, MineSweeperCell.FLAGGED_MINE);
			break;
		case COVERED_CELL:
			setCell(x, y, MineSweeperCell.FLAG);
			break;
		case FLAGGED_MINE:
			setCell(x, y, MineSweeperCell.MINE);
			break;
		case FLAG:
			setCell(x, y, MineSweeperCell.COVERED_CELL);
			break;
		default:
			break;
		}
		return;
	}

	@Override
	public boolean isGameLost() {
		return this.isGameLost;
	}

	@Override
	public boolean isGameWon() {
		if (!this.isGameLost()) {
			for (int i = 0; i < this.width(); ++i) {
				for (int j = 0; j < this.height(); ++j) {
					if (this.board[j][i].equals(MineSweeperCell.MINE)) {
						return false;
					}
					if (this.board[j][i].equals(MineSweeperCell.FLAG)) {
						return false;
					}
					if (this.board[j][i].equals(MineSweeperCell.COVERED_CELL)) {
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public int numberOfAdjacentMines(int x, int y) {
		int count = 0;
		for (int i = Math.max(x - 1, 0); i < Math.min(x + 2, this.width()); ++i) {
			for (int j = Math.max(y - 1, 0); j < Math.min(y + 2, this.height()); ++j) {
				if (i == x && j == y) {
					continue;
				}
				if (getCell(i, j) == MineSweeperCell.MINE || getCell(i, j) == MineSweeperCell.FLAGGED_MINE
						|| getCell(i, j) == MineSweeperCell.UNCOVERED_MINE) {
					count++;
				}
			}
		}
		return count;
	}

	@Override
	public void revealBoard() {
		for (int i = 0; i < this.width(); ++i) {
			for (int j = 0; j < this.height(); ++j) {
				if (getCell(i, j) == MineSweeperCell.MINE) {
					setCell(i, j, MineSweeperCell.UNCOVERED_MINE);
				}
				if (getCell(i, j) == MineSweeperCell.COVERED_CELL) {
					setCell(i, j, MineSweeperCell.adjacentTo(this.numberOfAdjacentMines(i, j)));
				}
			}
		}
		return;
	}

	@Override
	protected void setCell(int x, int y, MineSweeperCell value) {
		for (MineSweeperCell m : MineSweeperCell.values()) {
			if (value == m) {
				if (x < this.width() && x >= 0 && y < this.height() && y >= 0) {
					this.board[y][x] = value;
					return;
				}
			}
		}
		return;
	}
}
