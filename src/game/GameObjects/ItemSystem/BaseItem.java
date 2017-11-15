package game.GameObjects.ItemSystem;

/**
 * Created by Thijs Dreef on 07/11/2017.
 */
public class BaseItem
{
  public String name;
  public int value;
  public boolean usable = false;
  public boolean equipable = false;
  public BaseItem(String name, int value)
  {
    this.name = name;
    this.value = value;
  }
}
