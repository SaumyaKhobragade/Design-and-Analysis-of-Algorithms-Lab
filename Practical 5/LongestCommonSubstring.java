public class LongestCommonSubstring {
    public static void repeatingString(String s1) {
        GridCell[][] grid = new GridCell[s1.length()+1][s1.length()+1];
        for(int i = 0;i<grid.length;i++) {
            for(int j = 0;j<grid[0].length;j++) {
                grid[i][j] = new GridCell(0, 'H');
            }
        }

        for(int i = 1;i<=s1.length();i++) {
            for(int j = 1;j<=s1.length();j++) {
                if(s1.charAt(i-1)!=s1.charAt(j-1) && i!=j) {
                    grid[i][j].val = max(grid[i-1][j-1].val,grid[i-1][j].val,grid[i][j-1].val);
                    if(grid[i-1][j].val >= grid[i][j-1].val) {
                        grid[i][j].dir = 'U';
                    } else grid[i][j].dir = 'S';
                } else {
                    grid[i][j].val =grid[i-1][j-1].val + 1;
                    grid[i][j].dir = 'D';
                }
            }
        }
        readLCS(s1, grid, s1.length(), s1.length());


    }
    public static void readLCS(String s1,GridCell[][] grid,int i , int j) {
        if(i == 0 || j == 0) return;

        if(grid[i][j].dir == 'D') {
            readLCS(s1,grid, i-1, j-1);
            System.out.print(s1.charAt(i-1));
        } else if(grid[i][j].dir == 'U')  {
            readLCS(s1,grid, i-1, j);
        } else {
            readLCS(s1,grid, i, j-1);
        }

    }

    public static void lcs(String s1,String s2) {

        
        GridCell[][] grid= new GridCell[s1.length()+1][s2.length()+1];
        for(int i = 0;i<=s1.length();i++) {
            grid[i][0] = new GridCell(0, 'H');
        }
        for(int j = 0;j<=s2.length();j++) {
            grid[0][j] = new GridCell(0, 'H');
        }
        for(int i = 1;i<=s1.length();i++) {
            for(int j = 1;j<=s2.length();j++) {
                if(s1.charAt(i-1)!=s2.charAt(j-1)) {
                    grid[i][j] = new GridCell(0, 'H');
                    grid[i][j].val = max(grid[i-1][j-1].val,grid[i-1][j].val,grid[i][j-1].val);
                    if(grid[i-1][j].val >= grid[i][j-1].val) {
                        grid[i][j].dir = 'U';
                    } else grid[i][j].dir = 'S';
                } else {
                    grid[i][j] = new GridCell(0, 'H');
                    grid[i][j].val =grid[i-1][j-1].val + 1;
                    grid[i][j].dir = 'D';
                }
            }
        }
        System.out.println("Length: "+grid[s1.length()][s2.length()].val);
        readLCS(s1, grid, s1.length(), s2.length());
    }

    

    public static int max(int i1,int i2,int i3) {
        if(i1>i2 && i1>i3) {
            return i1;
        } else if(i2>i3 && i2>i1) {
            return i2;
        } else return i3;
    }
    public static void main(String[] args) {
        String s1="AGCCCTAAGGGCTACCTAGCTT";
        String s2 = "GACAGCCTACAAGCGTTAGCTTG";

        lcs(s1,s2);

        repeatingString(s1);
    }
}
