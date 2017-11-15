package game.GameObjects;

import engine.Fx.Pixel;
import game.GameObjects.ItemSystem.BaseCharacter;
import game.GameObjects.ItemSystem.Stats;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Thijs Dreef on 10/11/2017.
 */
public class EnemyCharacter
{
  public ArrayList<ConsoleLine> startCalls;
  public ArrayList<ConsoleLine> battleCalls;
  BaseCharacter character;
  public EnemyCharacter(String file, int level)
  {
    String temp;
    startCalls = new ArrayList<>();
    battleCalls = new ArrayList<>();
    Scanner input = new Scanner(file);
    String name = input.nextLine();
    String line = input.nextLine();
    line = line.replace("[", "");
    line = line.replace("]", "");
    String[] sStats = line.split(", ");
    int[] stats = new int[sStats.length];
    for (int i = 0; i < stats.length; i++)
      stats[i] = Integer.parseInt(sStats[i]) * (1 + level / 10);
    // load the first line to avoid ! misses
    line = input.nextLine();
    temp = input.nextLine();
    //parse the start calls
    while (!line.startsWith("!"))
    {
      String[] colors = temp.split(", ");
      float[] fColors = new float[3];
      for (int i = 0; i < 3; i++)
        fColors[i] = Float.parseFloat(colors[i]);
      startCalls.add(new ConsoleLine(name + ": " + line, Pixel.getColor(1, fColors[0], fColors[1], fColors[2])));
      line = input.nextLine();
      temp = input.nextLine();

    }
    //parse the next set
    line = temp;
    temp = input.nextLine();
    while (!line.startsWith("!"))
    {
      String[] colors = temp.split(", ");
      float[] fColors = new float[3];
      for (int i = 0; i < 3; i++)
        fColors[i] = Float.parseFloat(colors[i]);
      battleCalls.add(new ConsoleLine(name + ": " + line, Pixel.getColor(1, fColors[0], fColors[1], fColors[2])));
      line = input.nextLine();
      temp = input.nextLine();

    }
    character = new BaseCharacter(name, stats);
    character.currentHealth = stats[Stats.HEALTH];


  }
  public void takeTurn(Character character, Console console)
  {
    character.character.currentHealth -= Math.round(this.character.stats[Stats.ATTACK] / ((character.character.stats[Stats.DEFENSE] > 0) ? character.character.stats[Stats.DEFENSE] : 1)) + (this.character.stats[Stats.DEXTERITY] * Math.random());
    console.printLn(battleCalls.get((int)(Math.random() * battleCalls.size())));
  }
}
