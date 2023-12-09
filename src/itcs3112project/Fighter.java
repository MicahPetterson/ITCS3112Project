package itcs3112project;

/**
 *
 * @author Micah Petterson
 */

import java.util.*;

public class Fighter implements Cloneable
{
    public String name;
    public int maxHp;
    public int hp;
    public double atk;
    public double def;
    public double speed;
    public double eva;
    public double acc;
    public int maxMana;
    public int mana;
    public ArrayList<Spell> spellList;
    public Fighter opponent;
    //public int status;
    
    public Fighter(String name, int maxHp, double atk, double def, double speed, double eva, double acc, int maxMana, String spell1, String spell2, String spell3)
    {
        this.name = name;
        this.maxHp = maxHp;
        hp = maxHp;
        this.atk = atk;
        this.def = def;
        this.speed = speed;
        this.eva = eva;
        this.acc = acc;
        this.maxMana = maxMana;
        mana = maxMana;
        spellList = new ArrayList<>();
        opponent = null;
        String[] spells = {spell1, spell2, spell3};
        for(int i = 0; i < 3; i++)
        {
            if(!spells[i].contentEquals("NA"))
            {
                if(spells[i].contentEquals("Water Spout"))
                {
                    Spell spell = new Spell("Water Spout", 10, 0, 0, 0, 0, 0, 0, 10, false, " was shot by a water spout!");
                    spellList.add(spell);
                }
                else if(spells[i].contentEquals("Focus"))
                {
                    Spell spell = new Spell("Focus", 0, 0, 0, 1, 0.05, 0, 0, 5, true, "'s defense and speed rose!");
                    spellList.add(spell);
                }
                else if(spells[i].contentEquals("Frigid Blast"))
                {
                    Spell spell = new Spell("Frigid Blast", 15, 0, 0, 1, 0, 0, 0, 10, false, "'s defense fell!");
                    spellList.add(spell);
                }
                else if(spells[i].contentEquals("Ice Sheet"))
                {
                    Spell spell = new Spell("Ice Sheet", 0, 0, 0, 0, 0.5, 0, 0.5, 10, false, "'s speed and evasion fell!");
                    spellList.add(spell);
                }
                else if(spells[i].contentEquals("Bolt"))
                {
                    Spell spell = new Spell("Bolt", 20, 0, 0, 0, 0, 0, 0, 10, false, " was hit by a bolt!");
                    spellList.add(spell);
                }
                else if(spells[i].contentEquals("Ultrashock"))
                {
                    Spell spell = new Spell("Ultrashock", 35, 0, 0, 0, 0, 0, 0, 20, false, " was ultrashocked!");
                    spellList.add(spell);
                }
                else if(spells[i].contentEquals("Intimidate"))
                {
                    Spell spell = new Spell("Intimidate", 0, 0, 2, 0, 0, 0, 0, 10, false, "'s attack fell!");
                    spellList.add(spell);
                }
                else if(spells[i].contentEquals("Cloak"))
                {
                    Spell spell = new Spell("Cloak", 0, 0, 0, 0, 0, 0, 0.5, 10, true, " cloaked, and their evasion rose!");
                    spellList.add(spell);
                }
                else if(spells[i].contentEquals("Mend"))
                {
                    Spell spell = new Spell("Mend", 0, 15, 0, 0, 0, 0, 0, 15, true, " mended and regained health!");
                    spellList.add(spell);
                }
                else if(spells[i].contentEquals("Hurricane"))
                {
                    Spell spell = new Spell("Hurricane", 50, 0, 0, 0, 0, 0, 0, 40, false, " was hit by a hurricane!");
                    spellList.add(spell);
                }
            }
        }
    }
    
    public String fighterToDataString()
    {
        String dataString = name + "," + maxHp + "," + atk + "," + def + "," + speed + "," + eva + "," + acc + "," + maxMana + ",";
        if(spellList.size() == 3) dataString += spellList.get(0).name + "," + spellList.get(1).name + "," + spellList.get(2).name;
        else if(spellList.size() == 2) dataString += spellList.get(0).name + "," + spellList.get(1).name + ",NA";
        else if(spellList.size() == 1) dataString += spellList.get(0).name + ",NA,NA";
        else dataString += "NA,NA,NA";
        return dataString;
    }
    
    public Object clone() throws CloneNotSupportedException 
    { 
        return super.clone(); 
    }
}
