package game.States;

import engine.Engine;
import engine.Renderer;
import game.GameObjects.*;
import game.GameObjects.Character;
import game.GameObjects.ItemSystem.BaseCharacter;
import game.managers.ObjectManager;
import game.managers.State;

/**
 * Created by Thijs Dreef on 10/05/2017.
 */
public class Play extends State
{
  public Play()
  {
    manager = new ObjectManager();
    Console console = new Console(1, 370, 497, 128);
    Character character = new Character(new BaseCharacter("Thijs", new int[]{10, 2, 2, 2, 2}), 0, 0);
    InventoryInterFace inventory = new InventoryInterFace(250, 0, 300, character, console);
    manager.addObject(character);
    manager.addObject(inventory);
    manager.addObject(console);
    manager.addObject(new BattleSystem(0, 340, 250,console, character, inventory));
  }
  @Override
  public void update(Engine en, float dt)
  {
    manager.updateObjects(en, dt);
  }

  @Override
  public void render(Engine en, Renderer r)
  {
    manager.renderObjects(en, r);
  }

  @Override
  public void dispose()
  {

  }
}
