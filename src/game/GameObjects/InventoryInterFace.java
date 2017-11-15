package game.GameObjects;

import engine.Button;
import engine.Engine;
import engine.Fx.ShadowType;
import engine.Image;
import engine.Renderer;
import game.GameObjects.ItemSystem.BaseItem;
import game.GameObjects.ItemSystem.EquipItem;
import game.GameObjects.ItemSystem.Inventory;
import game.GameObjects.ItemSystem.UseAbleItem;
import game.managers.GameObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

public class InventoryInterFace extends GameObject
{
  private String[] items;
  private String[] sequence = {"hp:", "att:", "def:", "dex:", "spe:"};
  private int gold = 0;
  private int scrollOffset = 0;
  private Image noIcon = new Image("resources/noIcon.png");
  private Inventory inventory;
  private int yLog[][] = new int[10][1];
  private int indexLog[][] = new int[10][1];
  private int selectedItem = -1;
  private Button[] buttons = new Button[3];
  private Character character;
  private Console console;
  private int drawn;
  public InventoryInterFace(int x, int y, int h, Character character, Console console)
  {
    inventory = new Inventory(-1);

    try
    {
      items = new String(Files.readAllBytes(new File("items.txt").toPath())).split("\r\n\r\n");
    } catch (IOException e)
    {
      e.printStackTrace();
    }
    this.console = console;
    this.character = character;
    w = 250;
    buttons[0] = new Button(x, y + h + 28, 64, 32, "sell", this::sellItem);
    buttons[1] = new Button(x + (int)w / 3, y + h + 28, 64, 32, "use", this::useItem);
    buttons[2] = new Button(x + (int)w /  3 * 2, y + h + 28, 64, 32, "equip", this::equipItem);

    this.h = h;
    //fill the inventory with standard items
    inventory.addItem(new EquipItem("old buckler", 500, new Image("resources/buckler.png"), new int[] {2, 0, 0, 0, 0}, 5));
    inventory.addItem(new EquipItem("old sword", 500, new Image("resources/sword.png"), new int[] {-1, 2, 0, 0, 0}, 4));
    inventory.addItem(new EquipItem("old crown", 500, new Image("resources/crown.png"), new int[] {2, 0, 0, 0, 0}, 0));
    inventory.addItem(new EquipItem("old leather armor", 500, new Image("resources/leather_armor.png"), new int[] {2, 0, 0, 0, 0}, 1));
    inventory.addItem(new EquipItem("old leather pants", 500, new Image("resources/leather_pants.png"), new int[] {2, 0, 0, 0, 0}, 2));
    inventory.addItem(new EquipItem("old leather boots", 500, new Image("resources/leather_boots.png"), new int[] {2, 0, 0, 0, 0}, 3));
    this.x = x;
    this.y = y;

  }
  @Override
  public void update(Engine en, float dt)
  {
    if (en.getInput().getMouseX() > x && en.getInput().getMouseX() < x + w && en.getInput().getMouseY() > y && en.getInput().getMouseY() < y + h)
      scrollOffset += en.getInput().getMouseWheelMove() * 3;
    if (scrollOffset > 0)
      scrollOffset = 0;
    if (en.getInput().isButtonReleased(1))
    {
      buttonCheck(en);
      selectItem(en);
    }
  }

  @Override
  public void render(Engine en, Renderer r)
  {
    drawn = 0;
    int index = -scrollOffset / 64;
    int i = index * 64 + scrollOffset;
    BaseItem item = inventory.peekItem(index);
    while (item != null)
    {
      if (i > h)
        break;
      if (selectedItem == index)
        r.drawRect((int)x - 1, (int)y + i, (int)w, 64,0xff555555, ShadowType.FADE);

      drawItem(r, (int)x, (int)y, (int)w, i, item);
      yLog[drawn][0] = (int)y + i;
      indexLog[drawn][0] = index;
      i += 64;
      index++;
      drawn++;
      item = inventory.peekItem(index);
    }
    r.drawRect((int)x - 2, (int)y + (int)h, (int)w + 2, (int)(y + i - h) + 3, r.backgroundColor, ShadowType.FADE);
    r.drawNonFilledRect((int)x - 1, (int)y, (int)w, (int)h, 0xff404040, ShadowType.FADE);
    r.drawLargeString("gold: " + gold, 0xff101010, (int)x, (int)y + 8 + (int)h, ShadowType.FADE);
    for (Button button : buttons) button.drawButton(r);

  }

  @Override
  public void componentEvent(String name, GameObject object, String axis)
  {

  }

  @Override
  public void dispose()
  {

  }
  private void buttonCheck(Engine en)
  {
    if (selectedItem == -1)
      return;
    for (int i = 0; i < buttons.length; i ++)
    {
      if (buttons[i].checkCollision(en.getInput().getMouseX(), en.getInput().getMouseY()))
        buttons[i].activate();
    }
  }
  private void selectItem(Engine en)
  {
    if (en.getInput().isButtonReleased(1) && !(en.getInput().getMouseX() > x))
    {
      selectedItem = -1;
      return;
    }
    if (en.getInput().isButtonReleased(1) && en.getInput().getMouseX() > x)
    {
      for (int i = 0; i < drawn; i++)
      {
        if (en.getInput().getMouseY() > yLog[i][0] && en.getInput().getMouseY() < yLog[i][0] + 64 && yLog[i][0] + 32 < y + h)
        {
          selectedItem = indexLog[i][0];
          return;
        }
      }
    }
  }
  private void drawItem(Renderer r, int x, int y, int w, int i, BaseItem item)
  {
    r.drawNonFilledRect(x - 1, y + i, w, 64,0xff000000, ShadowType.FADE);
    if (item.equipable)
    {
      EquipItem eitem = (EquipItem)item;
      if (eitem.icon != null)
        r.drawImage(eitem.icon, x + 8, y + 8 + i);
      else
        r.drawImage(noIcon, x + 8, y + 8 + i);
      String stats = Arrays.toString(eitem.stats);
      stats = " " + stats;
      for (int t = 0; t < 5; t++)
        stats = stats.replaceFirst(" ", sequence[t]);
      stats = stats.replaceAll(",", " ");
      r.drawLargeString(stats, 0xff000000, x + 12 + noIcon.width, y + 22 + i, ShadowType.FADE);
    }
    else if (item.usable)
    {
      UseAbleItem aItem = (UseAbleItem)item;
      if (aItem.icon != null)
        r.drawImage(aItem.icon, x + 8, y + 8 + i);
      else
        r.drawImage(noIcon, x + 8, y + 8 + i);
    }
    else
      r.drawImage(noIcon, x + 8, y + 8 + i);
    r.drawLargeString(item.name, 0xff000000, x + 12 + noIcon.width, y + 8 + i, ShadowType.FADE);
    r.drawLargeString(item.value + " gold", 0xff000000, x + 8, y + 16 + i  + noIcon.height, ShadowType.FADE);

  }
  private void useItem()
  {
    if (!inventory.peekItem(selectedItem).usable)
    {
      this.console.printLn(new ConsoleLine(inventory.peekItem(selectedItem).name + " is not usable", 0xff000000));
      return;
    }
    UseAbleItem item = (UseAbleItem) inventory.getItem(selectedItem);
    this.console.printLn(new ConsoleLine("used: " + item.name, 0xff000000));

    this.character.addBuf(item.buff);
    selectedItem = -1;
  }
  private void equipItem()
  {

    if (!inventory.peekItem(selectedItem).equipable)
    {
      console.printLn(new ConsoleLine(inventory.peekItem(selectedItem).name + " is not equipable", 0xff000000));
      return;
    }
    EquipItem item = (EquipItem)inventory.getItem(selectedItem);
    console.printLn(new ConsoleLine("equipped: " + item.name, 0xff000000));

    inventory.addItem(character.equip(item, item.slot));
    selectedItem = -1;
  }
  private void sellItem()
  {
    BaseItem item = inventory.getItem(selectedItem);
    this.console.printLn(new ConsoleLine(item.name + " sold for " + item.value, 0xff000000));
    selectedItem = -1;
    gold += item.value;
  }
  public void dropItem()
  {
    BaseItem baseItem = ItemParser.parseItem(items[(int)(Math.random() * items.length)]);
    if (baseItem.equipable)
    {
      EquipItem item = (EquipItem)baseItem;
      for (int i = 0; i < item.stats.length; i++)
        item.stats[i] += item.stats[i] * (character.level / 10);
      inventory.addItem(item);
    }
    else if (baseItem.usable)
    {
      UseAbleItem item = (UseAbleItem)baseItem;
      for (int i = 0; i < item.buff.stats.length; i++)
        item.buff.stats[i] += item.buff.stats[i] * (character.level / 10);
      inventory.addItem(item);
    }
    else
      inventory.addItem(baseItem);
  }
}
