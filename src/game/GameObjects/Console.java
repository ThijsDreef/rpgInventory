package game.GameObjects;

import engine.Engine;
import engine.Fx.ShadowType;
import engine.Renderer;
import game.managers.GameObject;

import java.util.ArrayList;

/**
 * Created by Thijs Dreef on 09/11/2017.
 */
public class Console extends GameObject
{
  ArrayList<ConsoleLine> lines = new ArrayList<>();
  int scrollOffset = 0;
  public Console(int x, int y, int w, int h)
  {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    printLn(new ConsoleLine("welcome to the rpg inventory", 0xff000000));
    printLn(new ConsoleLine("you can use the editor in the left corner to add ", 0xff000000));
    printLn(new ConsoleLine("items/weapons/monsters", 0xff000000));
    printLn(new ConsoleLine("format the stats in [10, 2, 2, 2, 2]", 0xff000000));
    printLn(new ConsoleLine("health, attack, defense, dexteretiy, speed", 0xff000000));
    printLn(new ConsoleLine("for monster cries type the sentence then enter", 0xff000000));
    printLn(new ConsoleLine("and format the color in 0 - 1 rgb color values", 0xff000000));
    printLn(new ConsoleLine("for example: 1.0, 1.0, 1.0", 0xff000000));

  }
  @Override
  public void update(Engine en, float dt)
  {
    //scrolls the console if the mouse is within it
    if (en.getInput().getMouseX() > x && en.getInput().getMouseX() < x + w && en.getInput().getMouseY() > y && en.getInput().getMouseY() < y + h)
      scrollOffset += en.getInput().getMouseWheelMove() * 3;
    if (scrollOffset < 0)
      scrollOffset = 0;
  }

  @Override
  public void render(Engine en, Renderer r)
  {
    drawLines(r, (int)x, (int)y + scrollOffset+ (int)h - 12, (int)y);
    //draws the border
    r.drawNonFilledRect((int)x, (int)y, (int)w, (int)h, 0xff505050, ShadowType.FADE);
  }

  @Override
  public void componentEvent(String name, GameObject object, String axis)
  {

  }

  @Override
  public void dispose()
  {

  }
  public void drawLines(Renderer r, int x, int highestY, int lowestY)
  {
    //count how many are drawn because i counts down instead of up
    int drawn = 0;
    for (int i = lines.size() - 1; i >= 0; i--)
    {
      //if were beyond the border stop drawing
      if (highestY - 12 * drawn < lowestY - 12)
        break;
      //draw the line
      r.drawLargeString(lines.get(i).text, lines.get(i).color, x, highestY - 12 * drawn, ShadowType.FADE);

      drawn++;
    }
    //clear the top part without impacting anything else
    r.drawRect(x, (int)y - 9, (int)w, 9, r.backgroundColor, ShadowType.FADE);
  }
  public void printLn(ConsoleLine line)
  {
    lines.add(line);
  }
}
