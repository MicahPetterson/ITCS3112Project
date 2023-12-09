package itcs3112project;

/**
 *
 * @author Micah Petterson
 */
public class Item
{
    public String name;
    public String desc;
    public String message;
    public int hpChange;
    public double atkChange;
    public double defChange;
    public double speedChange;
    public double accChange;
    public double evaChange;
    public int manaChange;
    public int quantity;
    public boolean selfTarget;
    
    public Item (String name, int quantity)
    {
        this.name = name;
        this.quantity = quantity;
        
        if(name.contentEquals("Cloudberry"))
        {
            message = " regained some health!";
            desc = "Heals a small amount of health.";
            hpChange = 10;
            atkChange = 0;
            defChange = 0;
            speedChange = 0;
            accChange = 0;
            evaChange = 0;
            manaChange = 0;
            selfTarget = true;
        }
        else if(name.contentEquals("Bluepuff"))
        {
            message = " regained some mana!";
            desc = "Restores a moderate amount of mana.";
            hpChange = 0;
            atkChange = 0;
            defChange = 0;
            speedChange = 0;
            accChange = 0;
            evaChange = 0;
            manaChange = 20;
            selfTarget = true;
        }
        else if(name.contentEquals("Stormbomb"))
        {
            message = "'s vision was obscured by clouds, and their accuracy decreased!";
            desc = "Casts a sheet of dark clouds over the battlefield, lowering your opponent's accuracy.";
            hpChange = 0;
            atkChange = 0;
            defChange = 0;
            speedChange = 0;
            accChange = 0.08;
            evaChange = 0;
            manaChange = 0;
            selfTarget = false;
        }
        else if(name.contentEquals("Starshard"))
        {
            message = " fully restored their health!";
            desc = "Fully restores your health!";
            hpChange = 999;
            atkChange = 0;
            defChange = 0;
            speedChange = 0;
            accChange = 0;
            evaChange = 0;
            manaChange = 0;
            selfTarget = true;
        }
        else if(name.contentEquals("Windust"))
        {
            message = "'s speed rose!";
            desc = "Moderately raises your speed.";
            hpChange = 0;
            atkChange = 0;
            defChange = 0;
            speedChange = 0.06;
            accChange = 0;
            evaChange = 0;
            manaChange = 0;
            selfTarget = true;
        }
        else if(name.contentEquals("Gustball"))
        {
            message = " was bombarded by winds, and their defense lowered!";
            desc = "Bombards your opponent with gusts of wind to lower their defenses.";
            hpChange = 0;
            atkChange = 0;
            defChange = 1;
            speedChange = 0;
            accChange = 0;
            evaChange = 0;
            manaChange = 0;
            selfTarget = false;
        }
    }
}
