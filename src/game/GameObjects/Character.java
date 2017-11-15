package game.GameObjects;

import engine.Engine;
import engine.Fx.ShadowType;
import engine.Image;
import engine.Renderer;
import game.GameObjects.ItemSystem.*;
import game.managers.GameObject;

import java.util.ArrayList;

public class Character extends GameObject
{
  BaseCharacter character;
  ArrayList<Buff> buffs = new ArrayList<>();
  EquipItem[] equipped = new EquipItem[6];
  int level = 1;
  int exp = 0;
  int expToNextLevel = 10;
  int skillPoints = 0;
  float skillSelector = 0;
  int hOffset, vOffset;
  public Character(BaseCharacter character, int x, int y)
  {
    //fill the equip slots to avoid null errors
    for (int i = 0; i < 6; i++)
      equipped[i] = new EquipItem("equippable Air", 0, null, new int[] {0, 0, 0, 0, 0}, i);
    this.character = character;

    this.x = x;
    this.y = y;

    w = 250;
    h = 325;

    hOffset = (int)w / 6;
    vOffset = (int)h / 12;
  }
  @Override
  public void update(Engine en, float dt)
  {
    checkSkillPointUsed(en);
  }

  @Override
  public void render(Engine en, Renderer r)
  {
    //using constansts to avoid collapsing of the equipslots
    drawEquipSlot(r, (int)x + 48 + hOffset, (int)y + vOffset, 32, 32, equipped[0].icon);
    drawEquipSlot(r, (int)x + 48 + hOffset, (int)y + vOffset + 48, 32, 32, equipped[1].icon);
    drawEquipSlot(r, (int)x + 48 + hOffset, (int)y + vOffset + 48 * 2, 32, 32, equipped[2].icon);
    drawEquipSlot(r, (int)x + 48 + hOffset, (int)y + vOffset + 48 * 3, 32, 32, equipped[3].icon);
    drawEquipSlot(r, (int)x + 96 + hOffset, (int)y + vOffset + 48, 32, 32, equipped[4].icon);
    drawEquipSlot(r, (int)x + hOffset, (int)y + vOffset + 48, 32, 32, equipped[5].icon);
    drawStats(r, (int)x, + vOffset + 48 * 4 - 12, character);
  }

  @Override
  public void componentEvent(String name, GameObject object, String axis)
  {

  }

  @Override
  public void dispose()
  {

  }

  private void drawStats(Renderer r, int x, int y, BaseCharacter character)
  {
    r.drawLargeString("name: " + character.name, 0xff000000, x, y, ShadowType.FADE);
    r.drawLargeString("health: " + character.stats[Stats.HEALTH], 0xff000000, x, y + 12, ShadowType.FADE);
    r.drawLargeString("attack: " + character.stats[Stats.ATTACK], 0xff000000, x, y + 12 * 2, ShadowType.FADE);
    r.drawLargeString("defense: " + character.stats[Stats.DEFENSE], 0xff000000, x, y + 12 * 3, ShadowType.FADE);
    r.drawLargeString("dexterity: " + character.stats[Stats.DEXTERITY], 0xff000000, x, y + 12 * 4, ShadowType.FADE);
    r.drawLargeString("speed: " + character.stats[Stats.SPEED], 0xff000000, x, y + 12 * 5, ShadowType.FADE);
    r.drawLargeString("level: " + level, 0xff000000, x + (int)(w / 2), y, ShadowType.FADE);
    r.drawLargeString("exp: " + exp, 0xff000000, x + ((int)w / 2), y + 12, ShadowType.FADE);
    r.drawLargeString("to next lvl\n" + expToNextLevel, 0xff000000, x + (int)(w / 2), y + 12 * 2, ShadowType.FADE);

    if (skillPoints > 0)
      r.drawLargeString("<-", 0xff000000, x + (int) w / 2 - 24, y + 12 * (1 + (int)skillSelector),ShadowType.FADE);
    drawBuffs(r, x, y + 12 * 6);
  }
  public void drawBuffs(Renderer r, int x, int y)
  {
    for (int i = 0; i < ((buffs.size() < 4) ? buffs.size() : 4); i++)
    {
      r.drawLargeString(buffs.get(i).name + " turns: " + buffs.get(i).turns + ",", 0xff000000, x, y + i * 12, ShadowType.FADE);
    }
  }
  public void drawEquipSlot(Renderer r, int x, int y, int width, int height, Image item)
  {
    r.drawNonFilledRect(x, y, width, height, 0xff000000, ShadowType.FADE);
    if (item != null)
      r.drawImage(item, x + 1, y + 1);
    else
    {
      r.drawLine(x, y, x + width, y + height, 0xff000000);
      r.drawLine(x + width, y, x, y + height, 0xff000000);
    }
  }
  public BaseItem equip(EquipItem item, int slot)
  {
    BaseItem handle = equipped[slot];
    for (int i = 0; i < equipped[slot].stats.length; i++)
      character.stats[i] += item.stats[i] - equipped[slot].stats[i];
    equipped[slot] = item;
    return handle;
  }
  public void addBuf(Buff buf)
  {
    if (!buf.potion)
      for (int i = 0; i < buf.stats.length; i++)
        character.stats[i] += buf.stats[i];
    buffs.add(buf);
  }
  private void useBuffs()
  {
    for (int i = 0; i < buffs.size();i ++)
    {
      if (buffs.get(i).potion)
      {
        character.currentHealth += buffs.get(i).stats[Stats.HEALTH];
        buffs.remove(i);
      }
      else
      {
        buffs.get(i).turns--;
        if (buffs.get(i).turns <= 0)
        {
          for (int j = 0; j < character.stats.length; j++)
            character.stats[j] -=  buffs.get(i).stats[j];
          buffs.remove(i);
        }
      }
    }
  }
  public void takeTurn(EnemyCharacter character, Console console)
  {
    int damage = (int)(Math.round(this.character.stats[Stats.ATTACK] / ((character.character.stats[Stats.DEFENSE] < 0) ? 1 : character.character.stats[Stats.DEFENSE])) + (this.character.stats[Stats.DEXTERITY] * Math.random()));
    character.character.currentHealth -= damage;
    useBuffs();
    console.printLn(new ConsoleLine("you used your " + equipped[4].name + " it dealt "+ damage + " damage", 0xffffffff));
  }
  public void win(EnemyCharacter character, Console console)
  {
    int exp = 0;
    for (int i = 0; i < character.character.stats.length; i++)
      exp += character.character.stats[i]  * level;
    console.printLn(new ConsoleLine("you won, you gained: " + exp + " exp", 0xff000000));
    levelUp(exp);
  }
  private void levelUp(int exp)
  {
    this.exp += exp;
    if (this.exp > expToNextLevel)
    {
      level++;
      skillPoints++;
      this.exp -= expToNextLevel;
      expToNextLevel *= 2;
    }
  }
  private void checkSkillPointUsed(Engine en)
  {
    // if mouse is in the object scroll to move the arrow to select which skill point is used
    if (en.getInput().getMouseX() > x && en.getInput().getMouseX() < x + w && en.getInput().getMouseY() > y && en.getInput().getMouseY() < y + h)
    {
      skillSelector += en.getInput().getMouseWheelMove() / 10.0;
      if (skillSelector > 4)
        skillSelector = 0;
      else if (skillSelector < 0)
        skillSelector = 4;
      if (en.getInput().isButtonReleased(1) && skillPoints > 0)
      {
        character.stats[(int)skillSelector]++;
        skillPoints --;
      }
    }
  }
}
