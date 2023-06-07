package it.unipd.dei.eis;

import it.unipd.dei.eis.adapters.NyTimesCsvAdapter;
import it.unipd.dei.eis.adapters.TheGuardianJsonAdapter;
import it.unipd.dei.eis.serialization.Serializer;

import java.io.File;
import java.util.*;

public class InteractiveMenu {
  private Scanner scanner;

  public InteractiveMenu() {
    scanner = new Scanner(System.in);
  }

  public void runMenu() {
    int choice;
    do {
      displayMenu();
      choice = readIntChoice();
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


  private void callTheGuardianApi() {
    System.out.println("How many articles do you want to download?");
    int choice = readIntChoice();
    TheGuardianJsonAdapter theGuardianJsonAdapter = new TheGuardianJsonAdapter();
    theGuardianJsonAdapter.callApi(choice);
  }

  private void serializeArticlesToXml() {
    File[] folderList = getAllFoldersInPath("./assets/");
    ArrayList<File> selectedFiles = new ArrayList<>();
    // Select folder to serialize from
    int choice;
    int i;
    String input;
    NyTimesCsvAdapter nyTimesCsvAdapter = new NyTimesCsvAdapter();
    TheGuardianJsonAdapter theGuardianJsonAdapter = new TheGuardianJsonAdapter();
    do {
      System.out.println("From which source do you want to select?");
      i = 0;
      for (File folder : folderList) {
        System.out.println((++i) + ". " + folder.getName());
      }
      System.out.println((++i) + ". Serialize the selected files");
      System.out.println((++i) + "b. Go back");
      // get choice
      input = readStringChoice();
      // TODO: CHECK WHY IT GOES TO MAIN
      if (input.equalsIgnoreCase("b")) break;
      else choice = Integer.parseInt(input);
      // Serialize files
      if (choice == i - 1) {
        Serializer serializer = new Serializer();
        serializer.serialize(theGuardianJsonAdapter.getArticles());
      }
      int selectedFolderIndex = choice - 1;
      System.out.println("You selected the folder: " + folderList[selectedFolderIndex].getName());

      // Get an array of all files in the folder
      File[] fileNames = new File(folderList[selectedFolderIndex].toString()).listFiles();
      // Sorting alphabetically so Winzzoz and Linux/OSX have the same ordering
      assert fileNames != null;
      Arrays.sort(fileNames, Comparator.comparing(File::getName));
      // Get the files to load
      File[] toLoad = selectFilesToSerialize(fileNames);
      if (folderList[selectedFolderIndex].toString().equals("nytimes")) {
        nyTimesCsvAdapter.loadArticlesFromList(toLoad);
      } else if (folderList[selectedFolderIndex].toString().equals("theguardian")) {
        theGuardianJsonAdapter.loadArticlesFromList(toLoad);
      }
    } while (choice - 1 <= 0 || choice - 1 >= folderList.length);

  }

  private File[] selectFilesToSerialize(File[] fileNames) {
    Set<File> selectedFiles = new HashSet<>();
    int choice;
    int i;
    String input;
    while (true) {
      System.out.println("What file/s do you want to serialize?");
      i = 0;
      // TODO: CHECK IF IT'S IN SELECTEDFILES AND PRINT [X] if selected
      for (File fileName : fileNames) {
        System.out.println((++i) + ". " + fileName.getName());
      }
      System.out.println("b. Go back");
      // get choice
      input = readStringChoice();
      if (input.equalsIgnoreCase("b")) break;
      else choice = Integer.parseInt(input);
      // add selected file to a set of files if in range
      if (choice - 1 >= 0 && choice - 1 <= fileNames.length) {
        selectedFiles.add(fileNames[choice - 1]);
      }
    }
    return selectedFiles.toArray(new File[0]);
  }

  private File[] getAllFoldersInPath(String folderPath) {
    ArrayList<File> folders = new ArrayList<>();
    File folder = new File(folderPath);

    if (folder.exists() && folder.isDirectory()) {
      File[] subfolders = folder.listFiles(File::isDirectory);

      if (subfolders != null) {
        for (File subfolder : subfolders) {
          folders.add(subfolder);
        }
      }
    }
    Collections.sort(folders);
    return folders.toArray(new File[0]);
  }

  private void analyzeArticles() {
    // TODO: CHOOSE FILES TO ANALYZE
  }

  private String readStringChoice() {
    while (true) {
      System.out.print("Enter your choice: ");
      String input = scanner.nextLine();
      if (input.equalsIgnoreCase("b")) {
        return "b";
      }
      try {
        int choice = Integer.parseInt(input);
        return Integer.toString(choice);
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a valid integer or 'b' to go back.");
      }
    }
  }

  private int readIntChoice() {
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
}
