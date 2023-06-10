package it.unipd.dei.eis;

import it.unipd.dei.eis.adapters.NyTimesCsvAdapter;
import it.unipd.dei.eis.adapters.Adapter;
import it.unipd.dei.eis.adapters.TheGuardianJsonAdapter;
import it.unipd.dei.eis.serialization.Serializer;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

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
      choice = readIntChoice();
      switch (choice) {
        case 1:
          callTheGuardianApi();
          break;
        case 2:
          serializerSubMenu();
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
    System.out.println("1. Download 1000 articles from The Guardian for the query nuclear power");
    System.out.println("2. Serialize articles to xml file");
    System.out.println("3. Analyze articles");
    System.out.println("4. Exit");
  }


  private void callTheGuardianApi() {
    TheGuardianJsonAdapter theGuardianJsonAdapter = new TheGuardianJsonAdapter();
    // 1000/200 = 5 pages needed
    theGuardianJsonAdapter.callApi(5, "nuclear%20power");
  }

  private void serializerSubMenu() {
    File[] folders = getAllFoldersInPath("./assets/");
    ArrayList<File> selectedFiles = new ArrayList<>();

    boolean exit = false;

    while (!exit) {
      int i = 0;
      System.out.println("From which source do you want to select?");
      for (File folder : folders) {
        System.out.println((++i) + ". " + folder.getName());
      }
      System.out.println((++i) + ". Serialize the selected files");
      System.out.println((++i) + ". Go back");

      int choice = readIntChoice();

      // folderList.length+1 because i have the folders and 2 more options
      if (choice >= 1 && choice <= folders.length + 2) {
        if (choice == i) {
          exit = true;
          continue;
        }
        if (choice == (i - 1)) {
          // serialize files
          System.out.println("The selected files will be serialized:");
          for (File f : selectedFiles) {
            System.out.println("- " + f.getName());
          }
          chooseAdapterAndSerialize(selectedFiles.toArray(new File[0]));
          exit = true;
          continue;
        }
        // selected folder is folderList[choice - 1]
        File[] allFilesInFolder = folders[choice - 1].listFiles();
        assert allFilesInFolder != null;
        Arrays.sort(allFilesInFolder, Comparator.comparing(File::getName));
        selectedFiles.addAll(Arrays.asList(selectFilesToSerialize(allFilesInFolder)));
      } else {
        System.out.println("Invalid choice. Please try again.");
      }
    }
  }

  private void chooseAdapterAndSerialize(File[] filesToSerialize) {
    // we have an array of files to serialize. we want to know what adapter they correspond to and instantiate
    //  that adapter after that we want to parse the files to Articles, and when all are done serialize

    if (new File("./assets/articles.xml").exists()) {
      boolean exit = false;

      while (!exit) {
        System.out.println("There's already an articles file. If you don't delete it the articles you choose will be appended to the current file");
        System.out.println("Do you want to delete it? y/n");
        String choice = scanner.nextLine();
        if (choice.equalsIgnoreCase("y")) {
          if (new File("./assets/articles.xml").delete())
            System.out.println("The file has been deleted");
          else
            System.out.println("[ERROR] - The file has not been deleted");
          // delete file
          exit = true;
        } else if (choice.equalsIgnoreCase("n"))
          // don't delete
          exit = true;
        else
          System.out.println("Bad input, please insert again");
      }
    }

    // all adapters classes
    String[] adaptersClassNames = getAllFilesInPath("./src/main/java/it/unipd/dei/eis/adapters/");
    // remove the .java file extension
    for (int i = 0; i < adaptersClassNames.length - 1; i++) {
      adaptersClassNames[i] = adaptersClassNames[i].replace(".java", "");
    }

    // remove Adapter from array
    ArrayList<String> temp = new ArrayList<>(Arrays.asList(adaptersClassNames));
    temp.remove("Adapter");
    adaptersClassNames = temp.toArray(new String[0]);
    Serializer serializer = new Serializer();

    for (File fileToSerialize : filesToSerialize) {

      for (String adapterClassName : adaptersClassNames) {
        // se l'inizio del nome dell'adapter (TODO: + estensione) corrisponde al nome della cartella del file da serializzare ho fatto bingo
        String folderName = getFolderNameFromFileName(fileToSerialize.getName());
        if (adapterClassName.toLowerCase().startsWith(folderName)) {
          // call the appropriate classes with reflection
          try {
            // Load the class dynamically
            Class<?> clazz = Class.forName("it.unipd.dei.eis.adapters." + adapterClassName);
            // Create an instance of the class
            Object instance = clazz.newInstance();
            // Get the method with the desired name that takes a String parameter
            Method method = clazz.getMethod("loadArticleFromFileName", File.class);
            // Invoke the method, passing the filename as an argument
            method.invoke(instance, new File("./assets/" + folderName + "/" + fileToSerialize.getName()));
            // Invoke the method getArticles to serialize to xml
            method = clazz.getMethod("getArticles");
            serializer.serialize((Article[]) method.invoke(instance), "./assets/articles.xml");
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    }
  }

  private String getFolderNameFromFileName(String fileName) {
    int underscoreIndex = fileName.indexOf("_");
    String truncatedString = "";
    if (underscoreIndex != -1) {
      truncatedString = fileName.substring(0, underscoreIndex);
    } else {
      System.out.println("[ERROR] - No underscore in file name. Check that all folders in the assets folder are formatted correctly");
    }
    return truncatedString;
  }

  private File[] selectFilesToSerialize(File[] fileNames) {
    Set<File> selectedFiles = new HashSet<>();

    boolean exit = false;

    while (!exit) {
      int i = 0;
      System.out.println("What file/s do you want to serialize?");
      for (File fileName : fileNames) {
        String selected = (selectedFiles.contains(fileName)) ? "X" : " ";
        System.out.println((++i) + ". [" + selected + "] " + fileName.getName());
      }
      System.out.println((++i) + ". Go back");

      int choice = readIntChoice();

      // fileNames.length because i have the filenames and one more options
      if (choice >= 1 && choice <= fileNames.length + 1) {
        if (choice == i) {
          exit = true;
          continue;
        }
        File selectedFile = fileNames[choice - 1];
        if (selectedFiles.contains(selectedFile)) {
          selectedFiles.remove(selectedFile);
        } else {
          selectedFiles.add(selectedFile);
        }
      } else {
        System.out.println("Invalid choice. Please try again.");
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
        folders.addAll(Arrays.asList(subfolders));
      }
    }
    Collections.sort(folders);
    return folders.toArray(new File[0]);
  }

  private String[] getAllFilesInPath(String folderPath) {
    ArrayList<String> fileNames = new ArrayList<>();
    File folder = new File(folderPath);
    if (folder.isDirectory()) {
      File[] files = folder.listFiles();
      if (files != null) {
        for (File file : files) {
          if (file.isFile() && !file.getName().equals("Adapter")) {
            fileNames.add(file.getName());
          }
        }
      }
    }
    return fileNames.toArray(new String[0]);
  }

  private void analyzeArticles() {
    String[] fileNames = getAllFilesInPath("./assets/");
    // remove strings that don't end with .xml
    List<String> filteredList = new ArrayList<>();
    for (String str : fileNames) {
      if (str.endsWith(".xml")) {
        filteredList.add(str);
      }
    }
    fileNames = filteredList.toArray(new String[0]);

    boolean exit = false;
    while (!exit) {
      int i = 0;
      System.out.println("What file do you want to analyze?");
      for (String fileName : fileNames) {
        System.out.println((++i) + ". " + fileName);
      }
      System.out.println((++i) + ". Go back");

      int choice = readIntChoice();

      // fileNames.length because i have the filenames and one more options
      if (choice >= 1 && choice <= fileNames.length + 1) {
        if (choice == i) {
          exit = true;
          continue;
        }
        // selected file is fileNames[choice - 1]
        Analyzer analyzer = new Analyzer();
        analyzer.analyze("./assets/" + fileNames[choice - 1], "./assets/output.txt");
        System.out.println("The analyzed file output has been saved in ./assets/output.txt");
        exit = true;
      } else {
        System.out.println("Invalid choice. Please try again.");
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
