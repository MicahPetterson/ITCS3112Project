package itcs3112project;

/**
 *
 * @author Micah Petterson
 */

import java.util.*;

public class GameEngine
{
    public int turnCount = 1;
    public ArrayList<Item> inv;
    public Fighter player;
    public Fighter monster;
    public static Scanner scan = new Scanner(System.in);
    
    public GameEngine(ArrayList<Item> inv, Fighter player, Fighter monster)
    {
        this.inv = inv;
        String[] spells = {"NA", "NA", "NA"};
        for(int i = 0; i < player.spellList.size(); i++)
        {
            spells[i] = player.spellList.get(i).name;
        }
        this.player = new Fighter(player.name, player.maxHp, player.atk, player.def, player.speed, player.eva, player.acc, player.maxMana, spells[0], spells[1], spells[2]);
        String[] monsterSpells = {"NA", "NA", "NA"};
        for(int i = 0; i < monster.spellList.size(); i++)
        {
            monsterSpells[i] = monster.spellList.get(i).name;
        }
        this.monster = new Fighter(monster.name, monster.maxHp, monster.atk, monster.def, monster.speed, monster.eva, monster.acc, monster.maxMana, monsterSpells[0], monsterSpells[1], monsterSpells[2]);
        this.player.opponent = this.monster;
        this.monster.opponent = this.player;
    }
    
    public void battle()
    {
        System.out.println("BATTLE START!");
        System.out.println(player.name + " HP: " + player.hp + " / " + player.maxHp + " | Mana: " + player.mana + " / " + player.maxMana);
        System.out.println(monster.name + " HP: " + monster.hp + " / " + monster.maxHp);
        while(player.hp > 0 && monster.hp > 0)
        {
            System.out.println("TURN " + turnCount);
            if(player.speed > monster.speed)
            {
                playerTurn();
                System.out.println(player.name + " HP: " + player.hp + " / " + player.maxHp + " | Mana: " + player.mana + " / " + player.maxMana);
                System.out.println(monster.name + " HP: " + monster.hp + " / " + monster.maxHp);
                monsterTurn();
                System.out.println(player.name + " HP: " + player.hp + " / " + player.maxHp + " | Mana: " + player.mana + " / " + player.maxMana);
                System.out.println(monster.name + " HP: " + monster.hp + " / " + monster.maxHp);
            }
            else
            {
                monsterTurn();
                System.out.println(player.name + " HP: " + player.hp + " / " + player.maxHp + " | Mana: " + player.mana + " / " + player.maxMana);
                System.out.println(monster.name + " HP: " + monster.hp + " / " + monster.maxHp);
                playerTurn();
                System.out.println(player.name + " HP: " + player.hp + " / " + player.maxHp + " | Mana: " + player.mana + " / " + player.maxMana);
                System.out.println(monster.name + " HP: " + monster.hp + " / " + monster.maxHp);
            }
            turnCount++;
        }
        if(player.hp <= 0) System.out.println("DEFEAT\n You died!");
        else if(monster.hp <= 0) System.out.println("VICTORY\n You won!!!");
    }
    
    private static void attack(Fighter attacker)
    {
        Random random = new Random();
        System.out.println(attacker.name + " attacked!");
        double missChance = ((double) random.nextInt(101)) / 100;
        if(missChance > ((attacker.acc + attacker.opponent.eva) / 2))
        {
            double damageDealt = attacker.atk - attacker.opponent.def;
            if(damageDealt >= 0)
            {
                attacker.opponent.hp -= damageDealt;
                System.out.println(attacker.name + " dealt " + damageDealt + " damage!");
            }
            else
            {
                System.out.println(attacker.name + " dealt 0 damage!");
            }
        }
        else
        {
            System.out.println("...but missed!");
        }
    }
    
    private static void castSpell(Fighter attacker, Spell spell)
    {
        System.out.println(attacker.name + " used " + spell.name + "!");
        attacker.mana -= spell.manaCost;
        if(spell.selfTarget)
        {
            if(attacker.hp + spell.heal > attacker.maxHp) attacker.hp = attacker.maxHp;
            else attacker.hp += spell.heal;
            attacker.atk += spell.atkChange;
            attacker.def += spell.defChange;
            attacker.speed += spell.speedChange;
            attacker.eva += spell.evaChange;
            attacker.acc += spell.accChange;
            System.out.println(attacker.name + spell.message);
            if(spell.heal != 0) System.out.println(attacker.name + " regenerated " + spell.heal + "health!");
        }
        else
        {
            attacker.opponent.hp -= spell.damage;
            attacker.opponent.atk = ((attacker.opponent.atk - spell.atkChange) < 0) ? (attacker.opponent.atk - spell.atkChange): 0;
            attacker.opponent.def = ((attacker.opponent.def - spell.defChange) < 0) ? (attacker.opponent.def - spell.defChange): 0;
            attacker.opponent.speed = ((attacker.opponent.speed - spell.speedChange) < 0) ? (attacker.opponent.speed - spell.speedChange): 0;
            attacker.opponent.eva = ((attacker.opponent.eva - spell.evaChange) < 0) ? (attacker.opponent.eva - spell.evaChange): 0;
            attacker.opponent.acc = ((attacker.opponent.acc - spell.accChange) < 0) ? (attacker.opponent.acc - spell.accChange): 0;
            System.out.println(attacker.opponent.name + spell.message);
            if(spell.damage != 0)
            {
                System.out.println(attacker.opponent.name + " took " + spell.damage + " damage!");
            }
        }
    }
    
    private void playerTurn()
    {
        int actionNum = 0, spellNum = 0, itemNum = 0;
        while(actionNum < 1 || actionNum > 3)
        {
            System.out.println("What would you like to do?");
            System.out.println("1) Attack");
            System.out.println("2) Cast spell");
            System.out.println("3) Use item");
            actionNum = scan.nextInt();
            if(actionNum == 1) attack(player);
            else if(actionNum == 2)
            {
                while(spellNum < 1 || spellNum > player.spellList.size() + 1)
                {
                    System.out.println("Which spell would you like to cast?");
                    int i = 0;
                    for(i = 0; i < player.spellList.size(); i++)
                    {
                        System.out.println((i + 1) + ") " + player.spellList.get(i).name + " | Mana Cost: " + player.spellList.get(i).manaCost);
                    }
                    System.out.println((i + 1) + ") Back");
                    spellNum = scan.nextInt();
                    if(spellNum > 0 && spellNum <= player.spellList.size())
                        if(player.spellList.get(spellNum - 1).manaCost <= player.mana) castSpell(player, player.spellList.get(spellNum - 1));
                        else
                        {
                            System.out.println("You don't have enough mana to cast that spell!");
                            actionNum = 0;
                        }
                    else if(spellNum == (i + 1)) actionNum = 0;
                    else System.out.println("Invalid number, try again.");
                }
            }
            else if(actionNum == 3)
            {
                if(inv.isEmpty())
                {
                    System.out.println("You have no items!");
                    actionNum = 0;
                }
                else
                {
                    for(Item i : inv){if(i.quantity == 0) inv.remove(i);}
                    while(itemNum < 1 || itemNum > inv.size())
                    {
                        System.out.println("What item would you like to use?");
                        for(int i = 0; i < inv.size(); i++)
                        {
                            System.out.println((i + 1) + ") " + inv.get(i).name + " x" + inv.get(i).quantity);
                        }
                        itemNum = scan.nextInt();
                        if(itemNum > 0 && itemNum < inv.size())
                        {
                            Item usedItem = inv.get(itemNum);
                            System.out.println(player.name + " used " + usedItem.name + "!");
                            if(usedItem.selfTarget)
                            {
                                if(player.hp + usedItem.hpChange > player.maxHp) player.hp = player.maxHp;
                                else player.hp += usedItem.hpChange;
                                player.atk += usedItem.atkChange;
                                player.def += usedItem.defChange;
                                player.speed += usedItem.speedChange;
                                player.eva += usedItem.evaChange;
                                player.acc += usedItem.accChange;
                                System.out.println(player.name + usedItem.message);
                                if(usedItem.hpChange != 0) System.out.println(player.name + " regenerated " + usedItem.hpChange + " health!");
                            }
                            else
                            {
                                player.opponent.hp -= usedItem.hpChange;
                                player.opponent.atk = ((player.opponent.atk - usedItem.atkChange) < 0) ? (player.opponent.atk - usedItem.atkChange): 0;
                                player.opponent.def = ((player.opponent.def - usedItem.defChange) < 0) ? (player.opponent.def - usedItem.defChange): 0;
                                player.opponent.speed = ((player.opponent.speed - usedItem.speedChange) < 0) ? (player.opponent.speed - usedItem.speedChange): 0;
                                player.opponent.eva = ((player.opponent.eva - usedItem.evaChange) < 0) ? (player.opponent.eva - usedItem.evaChange): 0;
                                player.opponent.acc = ((player.opponent.acc - usedItem.accChange) < 0) ? (player.opponent.acc - usedItem.accChange): 0;
                                System.out.println(player.opponent.name + usedItem.message);
                                if(usedItem.hpChange != 0)
                                {
                                    System.out.println(player.opponent.name + " took " + usedItem.hpChange + " damage!");
                                }
                            }
                        }
                        else
                        {
                            System.out.println("Invalid number, try again.");
                        }
                    }
                }
            }
        }
    }
    
    private void monsterTurn()
    {
        Random random = new Random();
        int move = random.nextInt(monster.spellList.size() + 1);
        if(move == 0)
        {
            attack(monster);
        }
        else
        {
            if(monster.spellList.get(move - 1).manaCost <= monster.mana)
            {
                castSpell(monster, monster.spellList.get(move - 1));
            }
            else attack(monster);
        }
    }
}
