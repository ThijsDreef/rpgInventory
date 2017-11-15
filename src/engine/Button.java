package engine;

import engine.Fx.ShadowType;
import game.GameObjects.ItemSystem.Callback;

public class Button
{
  protected int x, y;
  protected int width, height;
  private int vOffsetText, hOffsetText;
  private Callback callback;
  public String text;
  public Button(int x, int y, int width, int height, String text, Callback callback)
  {
    this.callback = callback;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.text = text;
    vOffsetText = (height - 12) / 2;
    hOffsetText = 0;
  }
  public void activate()
  {
    callback.callback();
  }
  public boolean checkCollision(int x, int y)
  {
    return (x > this.x && x < this.x + width) && y > this.y && y < this.y + height;
  }
  public void drawButton(Renderer r)
  {
    r.drawNonFilledRect(x, y, width, height, 0xff000000, ShadowType.FADE);
    hOffsetText = (width - r.drawLargeString(text, 0xff000000, x + hOffsetText, y + vOffsetText, ShadowType.FADE)) / 2;
  }
  public void setCallback(Callback callback)
  {
    this.callback = callback;
  }
}
