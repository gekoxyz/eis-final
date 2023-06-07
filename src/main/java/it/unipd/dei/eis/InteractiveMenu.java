package it.unipd.dei.eis;

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
    File[] folderList = getAllFoldersInPath("./assets/");
    // Select folder to serialize from
    int choice;
    int i;
    do {
      System.out.println("From which source do you want to serialize?");
      i = 0;
      for (File folder : folderList) {
        System.out.println((++i) + ". " + folder.getName());
      }
      System.out.println((++i) + ". Go back");
      // get choice
      choice = readChoice();
      if (choice == i) return;
    } while (choice - 1 <= 0 || choice - 1 >= folderList.length);
    int selectedFolderIndex = choice - 1;
    System.out.println("You selected the folder: " + folderList[selectedFolderIndex].getName());

    if (folderList[selectedFolderIndex].getName() == "the_guardian") {
      // instantiate the guardian adapter
    } else if (folderList[selectedFolderIndex].getName() == "nytimes") {
      // instantiate nytimes adapter
    }

    // Get an array of all files in the folder
    File[] fileNames = new File(folderList[selectedFolderIndex].toString()).listFiles();
    // Sorting alphabetically so Winzzoz and Linux/OSX have the same ordering
    Arrays.sort(fileNames, Comparator.comparing(File::getName));
    // Get the files to serialize
    File[] selectedFiles = getFilesToSerialize(fileNames);
    for (File f : selectedFiles) {
      System.out.println(f.getName());
    }

    Serializer serializer = new Serializer();
  }

  private void analyzeArticles() {
    // TODO: CHOOSE FILES TO ANALYZE
  }

  private File[] getFilesToSerialize(File[] fileNames) {
    Set<File> selectedFiles = new HashSet<>();
    int choice;
    int i;
    do {
      System.out.println("What file/s do you want to serialize?");
      i = 0;
      for (File fileName : fileNames) {
        System.out.println((++i) + ". " + fileName.getName());
      }
      System.out.println((++i) + ". Stop selecting and serialize the selected files");
      // get choice
      choice = readChoice();
      // add selected file to a set of files if in range
      if (choice - 1 >= 0 && choice - 1 < fileNames.length) {
        selectedFiles.add(fileNames[choice - 1]);
      } else if (choice == fileNames.length) {
        System.out.println("Serializing...");
      } else {
        System.out.println("The file is not in range!");
      }
    } while (choice != i);
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

}
