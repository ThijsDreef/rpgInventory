package game.GameObjects;

import engine.Button;
import engine.Engine;
import engine.Renderer;
import game.GameObjects.ItemSystem.Stats;
import game.managers.GameObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by Thijs Dreef on 09/11/2017.
 */

public class BattleSystem extends GameObject
{
  Button buttons[] = new Button[3];
  String monsters[];
  double findRate = 60;
  private Console console;
  private Character character;
  private EnemyCharacter enemyCharacter;
  private InventoryInterFace inventory;
  boolean idle = false;
  int frameSkip = 0;
  public BattleSystem(int x, int y, int width, Console console, Character character, InventoryInterFace inventory)
  {
    try
    {
      monsters = new String(Files.readAllBytes(new File("monsters.txt").toPath())).split("\r\n\r\n");
    } catch (IOException e)
    {
      e.printStackTrace();
    }
    this.inventory = inventory;
    this.character = character;
    this.console = console;
    h = 16;
    this.x = x;
    this.y = y;
    this.w = width;
    for (int i = 0; i < buttons.length; i++)
      buttons[i] = new Button(x + 9 + (width / 4 + width / 12) * i,y, width / 4, (int)h, "null", () -> this.console.printLn(new ConsoleLine("help", 0xff000000)));
    buttons[0].text = "battle";
    buttons[0].setCallback(this::startBattle);
    buttons[1].text = "idle";
    buttons[1].setCallback(() -> idle = true);
    buttons[2].text = "!idle";
    buttons[2].setCallback(() -> idle = false);

  }
  @Override
  public void update(Engine en, float dt)
  {
    if (en.getInput().isButtonReleased(1))
      for (int i = 0; i < buttons.length; i++)
        if (buttons[i].checkCollision(en.getInput().getMouseX(), en.getInput().getMouseY()))
          buttons[i].activate();
    frameSkip++;
    if (idle && frameSkip > 60 - character.character.stats[Stats.SPEED])
    {
      buttons[0].activate();
      frameSkip = 0;
    }
  }

  @Override
  public void render(Engine en, Renderer r)
  {
    for (int i = 0; i < buttons.length; i ++)
      buttons[i].drawButton(r);
  }

  @Override
  public void componentEvent(String name, GameObject object, String axis)
  {

  }

  @Override
  public void dispose()
  {

  }
  public void attack()
  {
    character.takeTurn(enemyCharacter, console);
    enemyCharacter.takeTurn(character, console);
    printHealth();
    if (character.character.currentHealth <= 0)
    {
      console.printLn(new ConsoleLine("you lost", 0xff000000));
      buttons[0].text = "battle";
      buttons[0].setCallback(this::startBattle);
    }
    else if (enemyCharacter.character.currentHealth <= 0)
    {
      character.win(enemyCharacter, console);
      if (Math.random() > Math.random())
        inventory.dropItem();
      buttons[0].text = "battle";
      buttons[0].setCallback(this::startBattle);
    }
  }

  public void startBattle()
  {
    character.character.currentHealth = character.character.stats[Stats.HEALTH];
    enemyCharacter = new EnemyCharacter(monsters[(int)(Math.random() * monsters.length)], character.level);
    console.printLn(enemyCharacter.startCalls.get((int)(enemyCharacter.startCalls.size() * Math.random())));
    buttons[0].text = "attack";
    buttons[0].setCallback(this::attack);
    printHealth();
  }
  private void printHealth()
  {
    console.printLn(new ConsoleLine("currentHealth: " + character.character.currentHealth, 0xff000000));
    console.printLn(new ConsoleLine(enemyCharacter.character.name + ": health = " + enemyCharacter.character.currentHealth, 0xff000000));
  }
}
