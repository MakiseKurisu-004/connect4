import processing.core.PApplet;
import processing.core.PFont;

public class c4Runner extends PApplet {
    public static void main(String[] args) {
        System.out.println("Hello World");
        System.out.println();
        PApplet.main("c4Runner", args);
    }


    // global variables
    int gridStart;
    int spacingX;
    int spacingY;
    TTTBoard board;
    int count;
    boolean winner;
    String[][] bArray;
    String winnerChar;


    public void settings() {
        size(1000, 1000);
        count = 0;
        board = new TTTBoard();
        bArray = new String[6][7];
        winner = false;

    }

    public void setup() {
        //for(String s:PFont.list()){System.out.println(s);}
        PFont font = createFont("Comic Sans MS Bold", width / 3 / 2);
        textFont(font);
    }

    public class TTTbox {
        int row;
        int col;
        int size;
        String symbol;
        int x, y;
        boolean wasClicked;

        TTTbox() {
        }

        TTTbox(int row, int col, int s, int x, int y) {
            size = s / 2 / 6;
            this.x = x;
            this.y = y;
            wasClicked = false;
            this.row = row;
            this.col = col;
        }

        boolean isClicked() {
            if (mouseX > x && mouseX < x + size && mouseY > y && mouseY < y + size)
                return true;
            else
                return false;
        }

        void display() {
            if (mousePressed && isClicked() && !wasClicked && !winner) {
                if (row == 5 || bArray[row + 1][col] == "X" || bArray[row + 1][col] == "O") {
                    fill(255, 0, 0);
                    wasClicked = true;
                    if (count % 2 == 0) {
                        symbol = "X";
                    } else symbol = "O";
                    count++;
                }
            }
            else
                fill(255);
            rect(x, y, size, size);
            if (wasClicked) {
                fill(0);
                if (row == 5||bArray[row+1][col] == "X"||bArray[row+1][col]=="O") {
                    bArray[row][col] = symbol;
                    if (symbol == "X") {
                        fill(255, 0, 0);
                        ellipse(x + size / 2, y + size / 2, size * 4 / 5, size * 4 / 5);
                    } else {
                        fill(255, 255, 0);
                        ellipse(x + size / 2, y + size / 2, size * 4 / 5, size * 4 / 5);
                    }
                }
            }
        }
    }

    public class TTTBoard {
        TTTbox[][] board;

        TTTBoard() {
            board = new TTTbox[6][7];
            for (int r = 0; r < board.length; r++) {
                gridStart = width / 6;
                spacingX = width / 2 / 6;
                spacingY = width / 2 / 6;
                for (int c = 0; c < board[r].length; c++) {
                    board[r][c] = new TTTbox(r, c, width, gridStart + c * spacingX, gridStart + r * spacingY);

                }
            }
        }

        public void display() {
            for (int r = 0; r < board.length; r++) {
                for (int c = 0; c < board[0].length; c++) {
                    board[r][c].display();
                }
            }
        }
    }


    public boolean isFilled(String [][] b){
        for(String arr[]: b){
            for(String s: arr){
                if(s == null){
                    return false;
                }
            }
        }
        return true;
    }

    public void bDisplay(){
        for (int r = 0; r < bArray.length; r++) {
            System.out.print("[ ");
            for (int c = 0; c < bArray[0].length; c++) {
                if (bArray[r][c] == null && c != bArray[0].length-1) System.out.print(" , ");
                else if(bArray[r][c] == null) System.out.print(" ");
                else {
                    if (c != bArray[0].length-1) {
                        System.out.print(bArray[r][c] + ", ");
                    }
                    else System.out.print(bArray[r][c]);
                }
            }
            System.out.println("]");
        }
        System.out.println();
    }

    public void draw() {
        fill(0,0,255);
        textAlign(CENTER,CENTER);
        textSize(width/15);
        text("Connect 4",width/2, 50);

        board.display();
        bDisplay();
        //horizontal
        for (int r = 0; r < bArray.length; r++) {
            for(int i = 0; i < 4; i++) {
                if (bArray[r][i] != null && bArray[r][i] == bArray[r][i+1] && bArray[r][i+1] == bArray[r][i+2] && bArray[r][i+2] == bArray[r][i+3]) {
                    winner = true;
                    winnerChar = bArray[r][i];
                }
            }
        }
        //vertical
        for (int c = 0; c < bArray[0].length; c++) {
            for(int i = 0; i < 3; i++) {
                if (bArray[i][c] != null && bArray[i][c] == bArray[i+1][c] && bArray[i+1][c] == bArray[i+2][c] && bArray[i+2][c] == bArray[i+3][c]) {
                    winner = true;
                    winnerChar = bArray[i][c];
                }
            }
        }
        //Diagonal - left to right
        for(int i = 0; i<3; i++) {
            if (bArray[i][i] != null && bArray[i][i] == bArray[i+1][i+1] && bArray[i+1][i+1] == bArray[i+2][i+2] && bArray[i+2][i+2] == bArray[i+3][i+3]) {
                winner = true;
                winnerChar = bArray[i][i];
            }
        }
        for(int i = 0; i<3; i++) {
            for(int j = 0; j<3;j++)
                if (bArray[i][j+1] != null && bArray[i][j+1] == bArray[i+1][j+2] && bArray[i+1][j+2] == bArray[i+2][j+3] && bArray[i+2][j+3] == bArray[i+3][j+4]) {
                    winner = true;
                    winnerChar = bArray[i][j+1];
                }
        }
        for(int i = 0; i<2; i++) {
            for(int j = 0; j<3;j++)
                if (bArray[i+1][j] != null && bArray[i+1][j] == bArray[i+2][j+1] && bArray[i+2][j+1] == bArray[i+3][j+2] && bArray[i+3][j+2] == bArray[i+4][j+3]) {
                    winner = true;
                    winnerChar = bArray[i+1][j];
                }
        }
        //Diagonal - right to left
        for(int i = 0; i<3; i++) {
            for(int j = 6; j>2;j--)
                if (bArray[i][j] != null && bArray[i][j] == bArray[i+1][j-1] && bArray[i+1][j-1] == bArray[i+2][j-2] && bArray[i+2][j-2] == bArray[i+3][j-3]) {
                    winner = true;
                    winnerChar = bArray[i][j];
                }
        }

        if(winner){
            fill(0,0,0);
            textAlign(CENTER);
            if(winnerChar == "X") {
                text("RED" + "\nWINS!!!", width / 2, height / 2);
            }
            else{
                text("YELLOW" + "\nWINS!!!", width / 2, height / 2);
            }
        }
        if(!winner && isFilled(bArray)){
            fill(0,0,0);
            textAlign(CENTER);
            text("You TIED!",width/2, height/2);
        }
        if(keyPressed && key == ' '){
            clear();
            background((255));
            settings();
        }
    }
}
