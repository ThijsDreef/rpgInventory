package game.GameObjects.ItemSystem;

import engine.Image;

/**
 * Created by Thijs Dreef on 07/11/2017.
 */
public class EquipItem extends BaseItem
{
  public int [] stats = new int[5];
  public Image icon;
  public int slot;
  public EquipItem(String name, int value, Image icon, int[] stats, int slot)
  {
    super(name, value);
    this.slot = slot;
    equipable = true;
    for (int i = 0; i < stats.length; i++)
      this.stats[i] = stats[i];
    this.icon = icon;
  }
}
