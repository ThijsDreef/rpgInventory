package engine;

import java.awt.event.*;

public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener{
  private Engine en;

  private boolean[] keys = new boolean[255];
  private boolean[] keysLast = new boolean[255];
  private boolean[] keyUp = new boolean[255];
  private boolean[] reset = new boolean[255];

  private boolean[] buttons = new boolean[5];
  private boolean[] buttonsUp = new boolean[5];
  private boolean[] buttonsReset = new boolean[5];
  private boolean[] buttonsLast = new boolean[5];
  int mouseWheelMove = 0;
  private int mouseX, mouseY;

  public Input(Engine en)
  {
    this.en = en;
    en.getWindow().getCanvas().addKeyListener(this);
    en.getWindow().getCanvas().addMouseListener(this);
    en.getWindow().getCanvas().addMouseMotionListener(this);
    en.getWindow().getCanvas().addMouseWheelListener(this);
  }
  public void update()
  {
    keysLast = keys.clone();
    keyUp = reset.clone();
    buttonsUp = buttonsReset.clone();
    buttonsLast = buttonsLast.clone();
    mouseWheelMove = 0;
  }
  @Override
  public void keyTyped(KeyEvent e) {

  }
  public void keyPressed(KeyEvent e) {
    keys[e.getKeyCode()] = true ;
  }

  @Override
  public void keyReleased(KeyEvent e) {
    keys[e.getKeyCode()] = false;
    keyUp[e.getKeyCode()] = true;
  }

  @Override
  public void mouseWheelMoved(MouseWheelEvent e)
  {
    mouseWheelMove = e.getUnitsToScroll();
    System.out.println(e.getUnitsToScroll());
  }

  @Override
  public void mouseClicked(MouseEvent e) {

  }

  @Override
  public void mousePressed(MouseEvent e) {
    buttons[e.getButton()] = true;
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    buttons[e.getButton()] = false;
    buttonsUp[e.getButton()] = true;
  }

  @Override
  public void mouseEntered(MouseEvent e) {

  }

  @Override
  public void mouseExited(MouseEvent e) {

  }

  @Override
  public void mouseDragged(MouseEvent e) {

  }

  @Override
  public void mouseMoved(MouseEvent e)
  {
    mouseX = (int)(e.getX() / en.getScale()) - en.getCamera().getTransx();
    mouseY = (int)(e.getY() / en.getScale()) - en.getCamera().getTransy();
  }

  public int getMouseWheelMove()
  {
    return mouseWheelMove;
  }

  public boolean isKeyPressed(int keyCode)
  {
    return keys[keyCode];
  }
  public boolean isKeyReleased(int KeyCode)
  {
    return keyUp[KeyCode];
  }
  public boolean isButtonDown(int KeyCode){return  buttons[KeyCode];}
  public boolean isButtonReleased(int KeyCode){return  buttonsUp[KeyCode];}
  public int getMouseX()
  {
    return mouseX;
  }
  public int getMouseY()
  {
    return mouseY;
  }
}
