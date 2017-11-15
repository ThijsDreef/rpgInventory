package game.GameObjects.ItemSystem;

import java.util.ArrayList;

public class Inventory
{
  private ArrayList<BaseItem> items = new ArrayList<>();
  int maxItems;
  boolean limited;
  public Inventory(int maxItems)
  {
    limited = maxItems >= 0;
    if (limited)
      this.maxItems = maxItems;
  }
  public boolean addItem(BaseItem item)
  {
    if (!limited)
    {
      items.add(item);
      return true;
    }
    if (maxItems < items.size())
    {
      items.add(item);
      return true;
    }
    return false;
  }
  public BaseItem peekItem(int index)
  {
    if (index >= items.size() || index < 0)
      return null;
    return items.get(index);
  }
  public BaseItem getItem(int index)
  {
    if (index > items.size() || index < 0 || items.size() == 0)
      return null;
    BaseItem item = items.get(index);
    items.remove(index);
    return item;
  }
}
