package game.GameObjects.ItemSystem;

/**
 * Created by Thijs Dreef on 07/11/2017.
 */
public class BaseCharacter
{

  public String name;
  public int[] stats = new int[5];
  public int currentHealth;
  public BaseCharacter(String name, int [] stats)
  {
    this.name = name;
    for (int i = 0; i < stats.length; i++)
      this.stats[i] = stats[i];
    currentHealth = stats[Stats.HEALTH];
  }
}
