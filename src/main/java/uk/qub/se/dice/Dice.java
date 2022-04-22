package uk.qub.se.dice;

public class Dice {
    public  int diceRoll() {

        int dice1 = (int) (Math.random() * 6 + 1);

        System.out.println("Your first die roll is : " + dice1);

        int dice2 = (int) (Math.random() * 6 + 1);

        System.out.println("Your second die roll is : " + dice2);

        System.out.println("That means you move " + (dice1 + dice2) + " spaces");

        return  dice1 + dice2;

    }
}
