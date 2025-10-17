public class LongestRepeatingSubstring {
    
    public static void readLRS(String s1, GridCell[][] grid, int i, int j) {
        if(i == 0 || j == 0) return;

        if(grid[i][j].dir == 'D') {
            readLRS(s1, grid, i-1, j-1);
            System.out.print(s1.charAt(i-1));
        } else if(grid[i][j].dir == 'U') {
            readLRS(s1, grid, i-1, j);
        } else {
            readLRS(s1, grid, i, j-1);
        }
    }

    public static void lrs(String s) {
        int n = s.length();
        GridCell[][] grid = new GridCell[n+1][n+1];
        
        for(int i = 0; i <= n; i++) {
            grid[i][0] = new GridCell(0, 'H');
        }
        for(int j = 0; j <= n; j++) {
            grid[0][j] = new GridCell(0, 'H');
        }
        
        for(int i = 1; i <= n; i++) {
            for(int j = 1; j <= n; j++) {
                if(s.charAt(i-1) == s.charAt(j-1) && i != j) {
                    grid[i][j] = new GridCell(grid[i-1][j-1].val + 1, 'D');
                } else {
                    int up = grid[i-1][j].val;
                    int left = grid[i][j-1].val;
                    if(up >= left) {
                        grid[i][j] = new GridCell(up, 'U');
                    } else {
                        grid[i][j] = new GridCell(left, 'S');
                    }
                }
            }
        }
        
        System.out.println("Length of LRS: " + grid[n][n].val);
        System.out.print("LRS: ");
        readLRS(s, grid, n, n);
        System.out.println();
    }
    
    
    
    public static int max(int i1, int i2, int i3) {
        if(i1 > i2 && i1 > i3) {
            return i1;
        } else if(i2 > i3 && i2 > i1) {
            return i2;
        } else {
            return i3;
        }
    }

    public static void main(String[] args) {
        String s = "AABCBDC";
        System.out.println("Input string: " + s);
        lrs(s);
        
        System.out.println();
        
        String s2 = "AABAAABA";
        System.out.println("Input string: " + s2);
        lrs(s2);
        
        System.out.println();
        
        
    }
}
