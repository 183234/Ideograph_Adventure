package com.ideograph.game;

public class Inventory {
    static String[] Food_names = new String[]{"sushi", "cake", "cookie", "fries", "juice", "meat", "pineapple", "pizza", "ramen", "sunny side up egg", "spaghetti", "taco"};
    static int[] Food_Inv = new int[12];
    static int[] Item_Inv = new int[12];

    public static void addFood(int food_id, int count) {
        if (Food_Inv[food_id] + count <= 99) {
            Food_Inv[food_id] += count;
            //message.toast(toString(count) + "個物品已被加入背包")
            System.out.println(count + " " + Food_names[food_id] + " has been added to inventory. You have a total of " + Food_Inv[food_id] + " items of that category");
        } else {
            Food_Inv[food_id] = 99;
            System.out.println("storage for " + Food_names[food_id] + " is full. " + "You have a total of " + Food_Inv[food_id] + " items of that category");
            //message.toast("該物品儲存量已滿")
        }
    }
}
