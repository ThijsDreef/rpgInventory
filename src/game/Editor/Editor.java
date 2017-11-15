package game.Editor;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Created by Thijs Dreef on 14/11/2017.
 */
public class Editor
{
  // this is just a mess but i wanted the functionality
  JFrame editor;
  String[] textLabel = new String[] {"name", "value", "stats", "turns", "icon", "name", "value", "stats", "slot", "icon", "name", "stats", "start calls", "battle cries"};
  public Editor()
  {
    editor = new JFrame("editor");

    JLabel[] usableItemsLabels = new JLabel[5];
    JTextField[] usableItemFields = new JTextField[5];

    JLabel[] equipableItemsLabels = new JLabel[5];
    JTextField equipableItemFields[] = new JTextField[5];

    JLabel[] monsterLabels = new JLabel[4];
    JTextArea[] monsterFields = new JTextArea[4];

    for (int i = 0; i < 4; i++)
    {
      monsterLabels[i] = new JLabel(textLabel[i + 10]);
      monsterFields[i] = new JTextArea();
    }
    for (int i = 0; i < usableItemsLabels.length; i++)
    {

      usableItemsLabels[i] = new JLabel(textLabel[i]);
      equipableItemsLabels[i] = new JLabel(textLabel[i + 5]);
    }
    for (int i = 0; i < usableItemFields.length; i++)
    {
      usableItemFields[i] = new JTextField();
      equipableItemFields[i] = new JTextField();
    }
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(6, 3));
    for (int i = 0; i < usableItemFields.length; i++)
    {
      panel.add(usableItemsLabels[i]);
      panel.add(usableItemFields[i]);
      panel.add(equipableItemsLabels[i]);
      panel.add(equipableItemFields[i]);
      if (i != 4)
      {
        panel.add(monsterLabels[i]);
        panel.add(monsterFields[i]);
      }
//      panel.add(xPanel);
    }
    JButton[] buttons = new JButton[3];
    for (int i = 0; i < 3; i++)
      buttons[i] = new JButton("save");

    buttons[0].addActionListener(e -> writeOutput("items.txt", getText(usableItemFields), 0));
    buttons[1].addActionListener(e -> writeOutput("items.txt", getText(equipableItemFields), 1));
    buttons[2].addActionListener(e -> writeOutput("monsters.txt", getText(monsterFields), 2));

    panel.add(new Panel());
    panel.add(new Panel());


    for (int i = 0; i < buttons.length; i++)
    {
      panel.add(new Panel());
      panel.add(buttons[i]);
    }
    editor.add(panel);
    editor.pack();
    editor.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    editor.setVisible(true);
    editor.pack();


  }
  public boolean writeOutput(String file, String[] textFields, int item)
  {
    if (textFields[2].length() < 15)
    {
      System.out.println("error in: " + textFields[2]);
      return false;
    }
    if (item == 2)
    {
      String output = "\n";
      output += textFields[0] + "\n";
      output += textFields[1] + "\n";
      output += textFields[2] + "\n!\n";
      output += textFields[3] + "\n!\nend\n";
      try
      {
        Files.write(Paths.get(file), output.getBytes(), StandardOpenOption.APPEND);
      }
      catch (IOException e)
      {
        return false;
      }
      return true;
    }
    else
    {
      String output = (item == 1) ? "\nequipable\n" : "\nconsumable\n";
      for (int i = 0; i < textFields.length; i++)
        output += textFields[i] + "\n";
      try
      {
        Files.write(Paths.get(file), output.getBytes(), StandardOpenOption.APPEND);
      }
      catch (IOException e)
      {
        return false;
      }
      return true;
    }
  }
  private String[] getText(JTextField[] components)
  {
    String[] output = new String[components.length];
    for (int i = 0; i < components.length; i++)
    {
      output[i] += components[i].getText();
      components[i].setText("");
    }
    return output;
  }
  private String[] getText(JTextArea[] components)
  {
    String[] output = new String[components.length];
    for (int i = 0; i < components.length; i++)
    {
      output[i] += components[i].getText();
      output[i] = output[i].replace("null", "");
      components[i].setText("");
    }
    return output;
  }

}
