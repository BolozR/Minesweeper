package minesweeper;

import minesweeper.model.Mine;
import minesweeper.view.Field;

import java.util.Scanner;

public class Main {

    public static void firstView(int size){
        System.out.print(" |");
        for (int i = 1; i <= size; i++) System.out.print(i);
        System.out.println("|");
        System.out.print("-|");
        for (int i = 1; i <= size; i++) System.out.print("-");
        System.out.println("|");
        int k = 0;
        for (int i = 0; i < size; i++){
            ++k;
            System.out.print(k+"|");
            for (int j = 0; j < size; j++){
                System.out.print(".");
            }
            System.out.println("|");
        }
        System.out.print("—│");
        for (int i = 1; i <= size; i++) System.out.print("-");
        System.out.println("|");
    }


    public static boolean gameLoop(Field field, String com, int x, int y){
        switch (com.strip()) {
            case "mine":
                if (field.setMark(x - 1, y - 1)){
                    if (field.show()) {
                        System.out.println("Congratulations! You found all mines!");
                        return false;
                    } } else System.out.println("There is a number here!");
                break;
            case "free":
                if (field.setFree(x - 1, y - 1)){
                    if (field.show()) {
                        System.out.println("Congratulations! You found all mines!");
                        return false;
                    } } else{
                    System.out.println("You stepped on a mine and failed!");
                    return false;
                }
                break;
            default:
                System.out.println("Wrong! Please enter data again.");
                break;
        }
        return true;
    }


    public static void main(String[] args) {
        // write your code here
        System.out.print("How many mines do you want on the field? ");
        Scanner scanner = new Scanner(System.in);
        int minesNum = scanner.nextInt();
        int size = 9;
        firstView(size);
        System.out.print("Set/unset mines marks or claim a cell as free: ");
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        String com = scanner.nextLine();
        Field field = new Field(size, minesNum, y - 1, x - 1);
        if(!gameLoop(field, com, y, x)) return;
        //field.show();
        while (true){
            System.out.print("Set/unset mines marks or claim a cell as free: ");
            x = scanner.nextInt();
            y = scanner.nextInt();
            com = scanner.nextLine();
            if (!gameLoop(field, com, y, x)) break;
        }
    }
}
