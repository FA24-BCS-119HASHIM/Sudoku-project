
public class Validator {

    public boolean isValidPlacement(int[][] grid, int row, int col, int num) {
        // check row
        for (int c = 0; c < 9; c++) {
            if (grid[row][c] == num) return false;
        }
        // check column
        for (int r = 0; r < 9; r++) {
            if (grid[r][col] == num) return false;
        }
        // check box
        int br = (row / 3) * 3;
        int bc = (col / 3) * 3;
        for (int r = br; r < br + 3; r++) {
            for (int c = bc; c < bc + 3; c++) {
                if (grid[r][c] == num) return false;
            }
        }
        return true;
    }


    public void buildTrackers(int[][] grid, MyHashSet[] rowSets, MyHashSet[] colSets, MyHashSet[] boxSets) {
        for (int i = 0; i < 9; i++) {
            rowSets[i] = new MyHashSet();
            colSets[i] = new MyHashSet();
            boxSets[i] = new MyHashSet();
        }
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                int v = grid[r][c];
                if (v != 0) {
                    rowSets[r].add(v);
                    colSets[c].add(v);
                    boxSets[(r / 3) * 3 + (c / 3)].add(v);
                }
            }
        }
    }
}