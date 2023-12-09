package itcs3112project;

/**
 *
 * @author Micah Petterson
 */

import java.util.*;
import java.io.*;

public class ITCS3112Project
{
    static Scanner scan = new Scanner(System.in);
    
    public static void main(String[] args)
    {
        try
        {
        ArrayList<Fighter> fighterList = new ArrayList<>();
        ArrayList<Fighter> newFighters = new ArrayList<>();
        Fighter chosenCharacter = null;
        Fighter opponent = null;
        String difficultyLabel = null;
        ArrayList<Item> chosenItems = new ArrayList<>();
        GameEngine engine = null;
        Scanner fileScanner;
        
        try
        {
            fileScanner = new Scanner(new File("PlayerList.txt"));

            while(fileScanner.hasNext())
            {
                String line = fileScanner.nextLine();
                String[] list = line.split(",");

                fighterList.add(new Fighter(list[0], Integer.parseInt(list[1]), Double.parseDouble(list[2]), Double.parseDouble(list[3]), Double.parseDouble(list[4]), Double.parseDouble(list[5]), Double.parseDouble(list[6]), Integer.parseInt(list[7]), list[8], list[9], list[10]));
            }
        }
        catch(FileNotFoundException exp) {System.out.println("Caught FileNotFoundException for PlayerList.txt. Try again making sure the file name and path are correct.");}
        
        int inputNum = 0;
        
        while(inputNum != 6)
        {
            System.out.println("Welcome to Skaian Skirmish!");
            System.out.println("How would you like to prepare for your battle?");
            System.out.println("Choose with the corresponding number!");
            System.out.print("1) Choose Your Character");
            if(chosenCharacter == null) System.out.println(" [NOT CHOSEN!]");
            else System.out.println(" [Chosen: " + chosenCharacter.name + "]");
            System.out.println("2) Make A Character");
            System.out.print("3) Customize Your Inventory");
            if(chosenItems.isEmpty()) System.out.println(" [Empty!]");
            else System.out.println("");
            System.out.print("4) Select Difficulty");
            if(opponent == null) System.out.println(" [NOT CHOSEN!]");
            else
            {
                if(opponent.name.contentEquals("Cloubie")) difficultyLabel = "Easy";
                else if(opponent.name.contentEquals("Starm")) difficultyLabel = "Medium";
                else if(opponent.name.contentEquals("Regice")) difficultyLabel = "Hard";
                else if(opponent.name.contentEquals("Atmosphex")) difficultyLabel = "Extreme";
                else if(opponent.name.contentEquals("Cosmism")) difficultyLabel = "IMPOSSIBLE";
                System.out.println(" [Chosen: " + difficultyLabel + "]");
            }
            System.out.print("5) FIGHT!");
            if(chosenCharacter == null && opponent == null) System.out.println(" [NOT READY! Choose character and difficulty!]");
            else if(chosenCharacter == null) System.out.println(" [NOT READY! Choose character!]");
            else if(opponent == null) System.out.println(" [NOT READY! Choose difficulty!]");
            else System.out.println("");
            System.out.println("6) Quit");
            
            inputNum = scan.nextInt();
            
            if(inputNum == 1) chosenCharacter = selectCharacter(fighterList, newFighters);
            else if(inputNum == 2)
            {
                chosenCharacter = createCharacter();
                newFighters.add(chosenCharacter);
            }
            else if(inputNum == 3) chosenItems = chooseInventory(chosenItems);
            else if(inputNum == 4) opponent = generateEnemy();
            else if(inputNum == 5)
            {
                if(chosenCharacter == null && opponent == null) System.out.println("Select your character and difficulty first!");
                else if(chosenCharacter == null) System.out.println("Select your character first!");
                else if(opponent == null) System.out.println("Select your difficulty first!");
                else
                {
                    chosenCharacter.opponent = opponent;
                    opponent.opponent = chosenCharacter;
                    engine = new GameEngine(chosenItems, chosenCharacter, opponent);
                    engine.battle();
                    chosenItems.clear();
                }
            }
            else if(inputNum == 6)
            {
                FileOutputStream fs;
                try
                {
                    fs = new FileOutputStream("PlayerList.txt");
                    PrintWriter outFS = new PrintWriter(fs);
                    for(Fighter f : fighterList)
                    {
                        outFS.println(f.fighterToDataString());
                    }
                    if(!newFighters.isEmpty())
                    {
                        for(Fighter f : newFighters)
                        {
                            outFS.println(f.fighterToDataString());
                        }
                        System.out.println("Newly created characters successfully written to data file.");
                    }
                    outFS.close();
                }
                catch(FileNotFoundException exp)
                {
                    System.out.println("Caught FileNotFoundException for PlayerList.txt. Try again making sure the file name and path are correct.");
                }
                System.out.println("Thanks for playing!");
            }
        }
        }
        catch(InputMismatchException exp)
        {
            System.out.println("Error! Invalid input.");
            System.exit(0);
        }
    }
    
    public static Fighter selectCharacter(ArrayList<Fighter> currentList, ArrayList<Fighter> newList)
    {
        Fighter f;
        ArrayList<Fighter> list = new ArrayList<>();
        int num = 0;
        while(num < currentList.size())
        {
            list.add(currentList.get(num));
            num++;
        }
        num = 0;
        while(num < newList.size())
        {
            list.add(newList.get(num));
            num++;
        }
        System.out.println("Enter the number of the character you'd like!");
        for(int i = 0; i < list.size(); i++)
        {
            System.out.println((i + 1) + ") " + list.get(i).name);
        }
        
        int inputNum = scan.nextInt();
        if(inputNum > 0 && inputNum < list.size() + 1)
        {
            f = list.get(inputNum - 1);
            System.out.println("CHOSEN: " + f.name);
            System.out.println("What would you like to do?");
            System.out.println("1) Select character!");
            System.out.println("2) View character stats");
            System.out.println("3) Back");
            inputNum = scan.nextInt();
            if(inputNum > 0 && inputNum < 4)
            {
                if(inputNum == 1)
                {
                    return f;
                }
                else if(inputNum == 2)
                {
                    System.out.println("Character: " + f.name);
                    System.out.println("Max HP: " + f.maxHp);
                    System.out.println("Attack: " + f.atk);
                    System.out.println("Defense: " + f.def);
                    System.out.println("Speed: " + f.speed);
                    System.out.println("Evasion: " + f.eva);
                    System.out.println("Accuracy: " + f.acc);
                    System.out.println("Max Mana: " + f.maxMana);
                    System.out.print("Spells: ");
                    if(!f.spellList.isEmpty())
                    {
                        for(int i = 0; i < f.spellList.size(); i++)
                        {
                            System.out.print(f.spellList.get(i).name);
                            if(i != f.spellList.size() - 1)
                            {
                                System.out.print(", ");
                            }
                        }
                    }
                    else System.out.println("None");
                    System.out.println("\n");
                    return null;
                }
                else return null;
            }
            else
            {
                System.out.println("Invalid number, try again.");
                return null;
            }
        }
        else
        {
            System.out.println("Invalid number, try again.");
            return selectCharacter(currentList, newList);
        }
    }
    
    public static Fighter createCharacter()
    {
        int inputNum = 0;
        double doubleInputNum = 0;
        String[] doubleStats = {"Attack", "Defense", "Speed", "Evasion", "Accuracy", "Max Mana"};
        double[] doubleStatValues = {0, 0, 0, 0, 0, 0};
        double[][] statBoundaries = { {5.0, 20.0}, {1.0, 5.0}, {0.0, 1.0}, {0.0, 1.0}, {0.0, 1.0}, {0, 100} };
        Fighter tempCharacter = new Fighter("John Doe", 0, 0, 0, 0, 0, 0, 0, "NA", "NA", "NA");
        System.out.println("What is your character's name?");
        scan.nextLine();
        tempCharacter.name = scan.nextLine();
        while(inputNum < 50 || inputNum > 150)
        {
            System.out.println("Max HP (Choose a number from 50 - 150):");
            inputNum = scan.nextInt();
            if(inputNum < 50 || inputNum > 150)
            {
                System.out.println("Invalid number, try again.");
            }
            else
            {
                tempCharacter.maxHp = inputNum;
                tempCharacter.hp = inputNum;
            }
        }
        for(int i = 0; i < doubleStats.length; i++)
        {
            while(doubleInputNum < statBoundaries[i][0] || doubleInputNum > statBoundaries[i][1])
            {
                System.out.println(doubleStats[i] + " (Choose a number from " + statBoundaries[i][0] + " - " + statBoundaries[i][1] + "):");
                doubleInputNum = scan.nextDouble();
                if(doubleInputNum < statBoundaries[i][0] || doubleInputNum > statBoundaries[i][1])
                {
                    System.out.println("Invalid number, try again.");
                }
                else
                {
                    doubleStatValues[i] = doubleInputNum;
                }
            }
            doubleInputNum = -1;
        }
        tempCharacter.atk = doubleStatValues[0];
        tempCharacter.def = doubleStatValues[1];
        tempCharacter.speed = doubleStatValues[2];
        tempCharacter.eva = doubleStatValues[3];
        tempCharacter.acc = doubleStatValues[4];
        tempCharacter.maxMana = (int) doubleStatValues[5];
        tempCharacter.mana = tempCharacter.maxMana;
        
        ArrayList<String> spellList = new ArrayList<>();
        spellList.add("Water Spout");
        spellList.add("Focus");
        spellList.add("Frigid Blast");
        spellList.add("Ice Sheet");
        spellList.add("Bolt");
        spellList.add("Ultrashock");
        spellList.add("Intimidate");
        spellList.add("Cloak");
        spellList.add("Mend");
        spellList.add("Hurricane");
        ArrayList<String> chosenSpells = new ArrayList<>();
        System.out.println("Spells:");
        int numSpellsChosen = 0;
        inputNum = 0;
        
        while(numSpellsChosen < 3 && (inputNum < 1 || inputNum > spellList.size()))
        {
            System.out.println("(Choose up to " + (3 - numSpellsChosen) + " more)");
            for(int i = 0; i < spellList.size(); i++)
            {
                    System.out.println((i + 1) + ") " + spellList.get(i));
            }
            System.out.println((spellList.size() + 1) + ") DONE");
            inputNum = scan.nextInt();
            if(inputNum < 1 || inputNum > spellList.size() + 1)
            {
                System.out.println("Invalid number, try again.");
            }
            else if(inputNum == spellList.size() + 1)
            {
                inputNum = 1;
            }
            else
            {
                chosenSpells.add(spellList.get(inputNum - 1));
                spellList.remove(spellList.get(inputNum - 1));
                numSpellsChosen++;
                inputNum = 0;
            }
        }
        while(chosenSpells.size() < 3)
        {
            chosenSpells.add("NA");
        }
        Fighter newCharacter = new Fighter(tempCharacter.name, tempCharacter.maxHp, tempCharacter.atk, tempCharacter.def, tempCharacter.speed, tempCharacter.eva, tempCharacter.acc, tempCharacter.maxMana, chosenSpells.get(0), chosenSpells.get(1), chosenSpells.get(2));
        return newCharacter;
    }
    
    public static ArrayList<Item> chooseInventory(ArrayList<Item> chosen)
    {
        Item[] availableItems = {new Item("Cloudberry", 1), new Item("Bluepuff", 1), new Item("Stormbomb", 1), new Item("Starshard", 1), new Item("Windust", 1), new Item("Gustball", 1)};
        int inputNum = -1;
        
        while(inputNum != 8)
        {
            if(!chosen.isEmpty())
            {
                System.out.println("Current inventory:");
                for (Item item : chosen)
                {
                    System.out.println(item.name + " x" + item.quantity);
                }
            }
            System.out.println("Select an item, use '7' to clear inventory, or use '8' to finish!");
            for(int i = 0; i < availableItems.length; i++)
            {
                System.out.println((i + 1) + ") " + availableItems[i].name + " - " + availableItems[i].desc);
            }
            System.out.println("7) CLEAR INVENTORY");
            System.out.println("8) FINISH");
            inputNum = scan.nextInt();
            if(inputNum > 0 && inputNum < 7)
            {
                String chosenItemName = availableItems[inputNum - 1].name;
                boolean containsChosenItem = false;
                Item existingItem = null;
                for(Item i : chosen)
                {
                    if(i.name.contentEquals(chosenItemName))
                    {
                        containsChosenItem = true;
                        existingItem = i;
                    }
                }
                int itemNum = 0;
                System.out.println("How many would you like to add to your inventory? (Use a negative number to subtract)");
                itemNum = scan.nextInt();
                if(itemNum >= 0)
                {
                    if(containsChosenItem)
                    {
                        int currentNum = existingItem.quantity;
                        chosen.remove(existingItem);
                        chosen.add(new Item(chosenItemName, (itemNum + currentNum)));
                    }
                    else
                    {
                        chosen.add(new Item(chosenItemName, itemNum));
                    }
                }
                else
                {
                    if(containsChosenItem)
                    {
                        if(itemNum * -1 >= existingItem.quantity)
                        {
                            chosen.remove(existingItem);
                        }
                        else
                        {
                            existingItem.quantity += itemNum;
                        }
                    }
                }
            }
            else if (inputNum == 7)
            {
                chosen.clear();
            }
            else if(inputNum == 8)
            {
                return chosen;
            }
            else
            {
                System.out.println("Invalid number, try again.");
            }
        }
        
        return null;
    }
    
    public static Fighter generateEnemy()
    {
        String[] difficulties = { "Easy", "Medium", "Hard", "Extreme", "IMPOSSIBLE (no like, literally)" };
        String[] names = { "Cloubie", "Starm", "Regice", "Atmosphex", "Cosmism" };
        double[][] stats = { {60, 5, 1, 0, 0, 0.5, 30}, {80, 7, 2, 0.5, 0.1, 0.65, 40}, {120, 10, 3, 0.7, 0.2, 0.8, 50}, {160, 12, 5, 0.9, 0.4, 0.9, 70}, {999, 999, 999, 1, 0.9, 0.99, 200} };
        String[][] spells = { {"NA", "NA", "NA"}, {"Water Spout", "NA", "NA"}, {"Water Spout", "Focus", "NA"}, {"Frigid Blast", "Ice Sheet", "Bolt"}, {"Hurricane", "Ultrashock", "Frigid Blast"} };
        String enemyName = null;
        System.out.println("Choose your difficulty. This will affect the strenght of your opponent!");
        for(int i = 0; i < difficulties.length; i++)
        {
            System.out.println((i + 1) + ") " + difficulties[i]);
        }
        int inputNum = 0;
        int index = 0;
        while(inputNum < 1 || inputNum > 5)
        {
            inputNum = scan.nextInt();
            if(inputNum > 0 && inputNum < 6)
            {
                index = inputNum - 1;
                enemyName = names[index];
            }
            else
            {
                System.out.println("Invalid number, try again.");
            }
        }
        
        return new Fighter(enemyName, (int) stats[index][0], stats[index][1], stats[index][2], stats[index][3], stats[index][4], stats[index][5], (int) stats[index][6], spells[index][0], spells[index][1], spells[index][2]);
    }
}