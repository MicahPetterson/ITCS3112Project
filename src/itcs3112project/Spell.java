/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itcs3112project;

/**
 *
 * @author micah
 */
public class Spell
{
    public String name;
    public int damage;
    public int heal;
    public double atkChange;
    public double defChange;
    public double speedChange;
    public double accChange;
    public double evaChange;
    public int manaCost;
    public boolean selfTarget;
    public String message;
    
    public Spell(String name, int damage, int heal, double atkChange, double defChange, double speedChange, double accChange, double evaChange, int manaCost, boolean selfTarget, String message)
    {
        this.name = name;
        this.damage = damage;
        this.heal = heal;
        this.atkChange = atkChange;
        this.defChange = defChange;
        this.speedChange = speedChange;
        this.accChange = accChange;
        this.evaChange = evaChange;
        this.manaCost = manaCost;
        this.selfTarget = selfTarget;
        this.message = message;
    }
}
