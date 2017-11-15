package game.GameObjects;

import engine.Image;
import game.GameObjects.ItemSystem.BaseItem;
import game.GameObjects.ItemSystem.EquipItem;
import game.GameObjects.ItemSystem.UseAbleItem;

import java.util.Scanner;

public class ItemParser
{
  static public BaseItem parseItem(String item)
  {
    Scanner input = new Scanner(item);
    String line = input.nextLine();
    if (line.startsWith("c"))
    {
      String name = input.nextLine();
      int value = Integer.parseInt(input.nextLine());
      int[] stats = getStats(input.nextLine());
      int turns = Integer.parseInt(input.nextLine());
      String icon = input.nextLine();
      return new UseAbleItem(name, value, stats, turns, new Image("resources/" + icon));
    }
    else if (line.startsWith("e"))
    {
      String name = input.nextLine();
      int value = Integer.parseInt(input.nextLine());
      int[] stats = getStats(input.nextLine());
      int slot = Integer.parseInt(input.nextLine());
      String icon = input.nextLine();
      return new EquipItem(name, value, new Image("resources/" + icon), stats, slot);
    }
    else if (line.startsWith("b"))
    {
      String name = input.nextLine();
      int value = Integer.parseInt(input.nextLine());
      return new BaseItem(name, value);
    }
    return null;
  }
  static private int[] getStats(String temp)
  {
    temp = temp.replace("[", "");
    temp = temp.replace("]", "");
    String[] sStats = temp.split(", ");
    int[] stats = new int[5];
    for (int i = 0; i < 5; i++)
      stats[i] = Integer.parseInt(sStats[i]);
    return stats;
  }
}
