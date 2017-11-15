package game.GameObjects.ItemSystem;

import engine.Image;

/**
 * Created by Thijs Dreef on 08/11/2017.
 */
public class UseAbleItem extends BaseItem
{
  public Image icon;
  public Buff buff;
  public UseAbleItem(String name, int value, int[] stats, int turns, Image icon)
  {
    super(name, value);
    usable = true;
    buff = new Buff(turns, stats, name);
    this.icon = icon;
  }
}
