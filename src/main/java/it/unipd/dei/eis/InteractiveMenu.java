package it.unipd.dei.eis;

import it.unipd.dei.eis.adapters.TheGuardianJsonAdapter;
import it.unipd.dei.eis.serialization.Serializer;

import java.util.Scanner;

/**
 * Graphic menu for the command line
 */
public class InteractiveMenu {
  private Scanner scanner;

  public InteractiveMenu() {
    scanner = new Scanner(System.in);
  }

  public void runMenu() {
    int choice;

    do {
      displayMenu();
      choice = readChoice();

      switch (choice) {
        case 1:
          callTheGuardianApi();
          break;
        case 2:
          serializeArticlesToXml();
          break;
        case 3:
          analyzeArticles();
          break;
        case 4:
          System.out.println("Exiting...");
          break;
        default:
          System.out.println("Invalid choice. Please try again.");
      }

      System.out.println();
    } while (choice != 4);

    scanner.close();
  }

  private void displayMenu() {
    System.out.println("Menu:");
    System.out.println("1. Download articles from The Guardian");
    System.out.println("2. Serialize articles to xml file");
    System.out.println("3. Analyze articles");
    System.out.println("4. Exit");
    System.out.print("Enter your choice: ");
  }

  private int readChoice() {
    int choice;
    while (true) {
      System.out.print("Enter your choice: ");
      String input = scanner.nextLine();
      try {
        choice = Integer.parseInt(input);
        break;
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a valid integer.");
      }
    }
    return choice;
  }

  private void callTheGuardianApi() {
    System.out.println("How many articles do you want to download?");
    int choice = readChoice();
    TheGuardianJsonAdapter theGuardianJsonAdapter = new TheGuardianJsonAdapter();
    theGuardianJsonAdapter.callApi(choice);
  }

  private void serializeArticlesToXml() {
    Serializer serializer = new Serializer();
    System.out.println("From which source do you want to serialize?");
    // TODO: CREATE MENU FOR THIS
    // THE GUARDIAN
    // NYTIMES
    // ALL POSSIBLE
  }

  private void analyzeArticles() {
    // TODO: FROM WHAT DO I ANALYZE?
  }
}
