package minesweeper.view;

import minesweeper.model.Mine;

import java.util.Random;

public class Field {
    private Mine[][] mines;
    private final int size;
    private final int minesNum;

    public Field(int size, int minesNum, int x, int y) {
        this.size = size;
        this.minesNum = minesNum;
        create(x, y);
    }

    private void create(int xFree, int yFree){
        Random random = new Random();
        mines = new Mine[size][size];
        int x, y;
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                mines[i][j] = new Mine();

        for (int i = 0; i < minesNum; i++) {
            x = random.nextInt(size);
            y = random.nextInt(size);
            if (mines[x][y].isMine() && x == xFree && y == yFree) i--;
            else mines[x][y].setMine();
        }
        countingNearMines();
    }

    public boolean show(){
        int flaggedMines = 0;
        System.out.print(" |");
        for (int i = 1; i <= size; i++) System.out.print(i);
        System.out.println("|");
        System.out.print("-|");
        for (int i = 1; i <= size; i++) System.out.print("-");
        System.out.println("|");
        int k = 0;
        for (Mine[] i : mines){
            ++k;
            System.out.print(k+"|");
            for (Mine j : i){
                //if (j.isMine()) System.out.print("X");
                if (j.isMine() && j.isFlag()) flaggedMines++;
                if (j.isFlag() && !j.isFree()) System.out.print("*");
                else System.out.print(j.isFree() ?
                        (j.nearMinesNum == 0 ? "/" : ""+j.nearMinesNum) : ".");
            }
            System.out.println("|");
        }
        System.out.print("—│");
        for (int i = 1; i <= size; i++) System.out.print("-");
        System.out.println("|");
        return flaggedMines == minesNum;
    }

    public boolean setMark(int x, int y){
        if(mines[x][y].isFree() && mines[x][y].nearMinesNum != 0 && !mines[x][y].isMine())
            return false;
        else mines[x][y].changeFlag();
        return true;
    }

    public boolean setFree(int x, int y){
        if(mines[x][y].isMine()) return false;
        mines[x][y].setFree();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                try {
                    if (!mines[i + x][j + y].isMine()){
                        mines[i + x][j + y].setFree();
                        if (mines[i+x][j+y].nearMinesNum == 0)
                            setNearFree(i+x, j+y);
                    }
                } catch (IndexOutOfBoundsException ignored) {}
            }
        }

        return true;
    }

    public void setNearFree(int x, int y){
        if (mines[x][y].nearMinesNum == 0) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i == 0 && j == 0) continue;
                    try {
                        if (!mines[i + x][j + y].isMine() && !mines[i + x][j + y].isFree()){
                            mines[i + x][j + y].setFree();
                            if (mines[i+x][j+y].nearMinesNum == 0)
                                setNearFree(i+x, j+y);
                        }
                    } catch (IndexOutOfBoundsException ignored) {}
                }
            }
        }
    }

    private void countingNearMines(){
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                if (!mines[i][j].isMine()){
                    mines[i][j].nearMinesNum = getNearMines(i, j);
                }
            }
        }
    }

    private int getNearMines(int x, int y) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                try {
                    if (mines[i + x][j + y].isMine()) count++;
                } catch (IndexOutOfBoundsException ignored) {}
            }
        }
        return count;
    }
}
