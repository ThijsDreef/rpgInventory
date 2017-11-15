package game.GameObjects.ItemSystem;

/**
 * Created by Thijs Dreef on 08/11/2017.
 */
public class Buff
{
  public String name;
  public boolean potion = false;
  public int turns;
  public int stats[];
  public Buff(int turns, int[] stats, String name)
  {
    if (turns == 0)
      potion = true;
    this.name = name;
    this.turns = turns;
    this.stats = stats;
  }
}
