package game.States;

import engine.Engine;
import engine.Renderer;
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
