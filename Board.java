import java.util.ArrayList;
import java.util.Collections;


public class Board {
    private final int[][] puzzle = new int[9][9];
    private final int[][] solution = new int[9][9];
    private int numberOfEmptyCells = 20;
    private final Validator validator = new Validator();
    private final Solver solver = new Solver();

    public int[][] getPuzzleCopy() {
        int[][] copy = new int[9][9];
        for (int r = 0; r < 9; r++) System.arraycopy(puzzle[r], 0, copy[r], 0, 9);
        return copy;
    }

    public int[][] getSolutionCopy() {
        int[][] copy = new int[9][9];
        for (int r = 0; r < 9; r++) System.arraycopy(solution[r], 0, copy[r], 0, 9);
        return copy;
    }

    public void setNumberOfEmptyCells(int n) { numberOfEmptyCells = Math.max(1, Math.min(81, n)); }

    public void generateNew() {
        clear(solution);
        fillDiagonalBlocks(solution);
        if (!solver.solve(solution)) {
            // extremely unlikely; try again
            generateNew();
            return;
        }
        // copy solution to puzzle and remove blanks
        for (int r = 0; r < 9; r++) System.arraycopy(solution[r], 0, puzzle[r], 0, 9);
        removeRandomCells(numberOfEmptyCells);
    }

    private void clear(int[][] grid) {
        for (int r = 0; r < 9; r++) for (int c = 0; c < 9; c++) grid[r][c] = 0;
    }

    private void fillDiagonalBlocks(int[][] grid) {
        // fill each 3x3 diagonal block with shuffled 1..9
        for (int block = 0; block < 3; block++) {
            ArrayList<Integer> nums = new ArrayList<>();
            for (int i = 1; i <= 9; i++) nums.add(i);
            Collections.shuffle(nums);
            int br = block * 3;
            int bc = block * 3;
            int idx = 0;
            for (int r = br; r < br + 3; r++) {
                for (int c = bc; c < bc + 3; c++) {
                    grid[r][c] = nums.get(idx++);
                }
            }
        }
    }

    private void removeRandomCells(int count) {
        ArrayList<Integer> positions = new ArrayList<>();
        for (int i = 0; i < 81; i++) positions.add(i);
        Collections.shuffle(positions);
        for (int i = 0; i < count && i < 81; i++) {
            int pos = positions.get(i);
            puzzle[pos / 9][pos % 9] = 0;
        }
    }

    public boolean placeNumber(int row, int col, int num) {
        if (num < 0 || num > 9) return false;
        if (num == 0) {
            puzzle[row][col] = 0;
            return true;
        }
        if (validator.isValidPlacement(puzzle, row, col, num)) {
            puzzle[row][col] = num;
            return true;
        } else {
            return false;
        }
    }

    public boolean isSolved() {
        for (int r = 0; r < 9; r++) for (int c = 0; c < 9; c++) if (puzzle[r][c] == 0 || puzzle[r][c] != solution[r][c]) return false;
        return true;
    }

    public void revealSolution() {
        for (int r = 0; r < 9; r++) System.arraycopy(solution[r], 0, puzzle[r], 0, 9);
    }
}