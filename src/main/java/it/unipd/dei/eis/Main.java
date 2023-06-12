package it.unipd.dei.eis;

/**
 * The Main class serves as the entry point for the program.
 * It initializes and runs the interactive menu.
 */
public class Main {

  /**
   * The main method creates an instance of the InteractiveMenu class and calls its runMenu() method.
   *
   * @param args command line arguments (unused)
   */
  public static void main(String[] args) {
    new InteractiveMenu().runMenu();
  }
}