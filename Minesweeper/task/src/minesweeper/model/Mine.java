package minesweeper.model;

public class Mine {
    private boolean mine;
    private boolean free;
    //public int xAxis, yAxis;
    private boolean flag;
    public int nearMinesNum;

    public Mine() {
        this.mine = false;
        this.flag = false;
        this.free = false;
        this.nearMinesNum = 0;
    }

    public boolean isFree() { return free; }

    public void setFree() { this.free = true; }

    public boolean isMine() {
        return mine;
    }

    public void setMine(){
        this.mine = true;
    }

    public boolean isFlag() {
        return flag;
    }

    public void changeFlag() {
        this.flag = !flag;
    }

}
