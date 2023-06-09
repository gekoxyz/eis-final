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
    System.out.print("Enter your choice: ");
  }


  private void callTheGuardianApi() {
    TheGuardianJsonAdapter theGuardianJsonAdapter = new TheGuardianJsonAdapter();
    // 1000/200 = 5 pages needed
    theGuardianJsonAdapter.callApi(5, "nuclear%20power");
  }

  private void serializerSubMenu() {
    File[] folders = getAllFoldersInPath("./assets/");
    ArrayList<File> selectedFiles = new ArrayList<>();

    TheGuardianJsonAdapter theGuardianJsonAdapter = new TheGuardianJsonAdapter();
    NyTimesCsvAdapter nyTimesCsvAdapter = new NyTimesCsvAdapter();

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
          Serializer serializer = new Serializer();
          // i can call whatever adapter, the articles array is shared
//          serializer.serialize(theGuardianJsonAdapter.getArticles());
          System.out.println("USER WANTS TO SERIALIZE: ");
          for (File f : selectedFiles) {
            System.out.println(f.getName());
          }
          chooseAdapterAndSerialize(selectedFiles.toArray(new File[0]));
          continue;
        }
        // selected folder is folderList[choice - 1]
        selectedFiles.addAll(Arrays.asList(selectFilesToSerialize(Objects.requireNonNull(folders[choice - 1].listFiles()))));
      } else {
        System.out.println("Invalid choice. Please try again.");
      }
    }
  }

  //TODO :CHANGE TO PRIVATE
  public void chooseAdapterAndSerialize(File[] filesToSerialize) {
    // TODO: we have an array of files to serialize. we want to know what adapter they correspond to and instantiate
    //  that adapter after that we want to parse the files to Articles, and when all are done serialize

    // all adapters classes
    String[] adaptersClassNames = getAllFilesInPath("./src/main/java/it/unipd/dei/eis/adapters/");
    // remove Adapter from array
    ArrayList<String> temp = new ArrayList<>(Arrays.asList(adaptersClassNames));
    temp.remove("Adapter");
    adaptersClassNames = temp.toArray(new String[0]);

    for (File fileToSerialize : filesToSerialize) {

      for (String adapterClassName : adaptersClassNames) {
        // se l'inizio del nome dell'adapter (TODO: + estensione) corrisponde al nome della cartella del file da serializzare ho fatto bingo
        if (adapterClassName.toLowerCase().startsWith(getFolderNameFromFileName(fileToSerialize.getName()))) {
          System.out.println("-------------");
          System.out.println(adapterClassName);
          System.out.println(fileToSerialize.getName());
          System.out.println("-------------");
          // call the appropriate classes with reflection
          try {
            // Load the class dynamically
            Class<?> clazz = Class.forName("it.unipd.dei.eis.adapters." + adapterClassName);
            // Create an instance of the class
            Object instance = clazz.newInstance();
            // Get the method with the desired name that takes a String parameter
            Method method = clazz.getMethod("loadArticleFromFileName", File.class);
            // Invoke the method, passing the filename as an argument
            // todo: extract to variable getFolderNameFromFIleName
            method.invoke(instance, new File("./assets/" + getFolderNameFromFileName(fileToSerialize.getName()) + "/" + fileToSerialize.getName()));
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    }

//    // based on the name of the file to serialize i extract the correct adapter and i call the loadArticlesFromList
//    for (File fileToSerialize : filesToSerialize) {
//      String folderNameFromFileName = getFolderNameFromFileName(fileToSerialize.getName());
//      // reflection
//      try {
//        // TODO: WARNING: THIS GETS ALSO ADAPTER CLASS WHICH IS ABSTRACT, REMOVE FROM LIST
//        String[] classNames = getAllFilesInPath("./src/main/java/it/unipd/dei/eis/adapters/");
//
//
//        // Get the class object of the abstract class
//        Class<Adapter> abstractClass = Adapter.class;
//        // Get the protected field using reflection
//        Field protectedField = abstractClass.getDeclaredField("folderName");
//
//        // Make the field accessible
//        protectedField.setAccessible(true);
//
////        // Get the value of the protected field
////        String value = (String) protectedField.get(instance);
//
//
//        for (String className : classNames) {
//          // Load the class dynamically (packagename+classname)
//          Class<?> clazz = Class.forName("it.unipd.dei.eis.adapters." + className);
//          // Get the string attribute field
//
//          String clazzFolderName = (String) protectedField.get(clazz);
//
//          // Check if the attribute has the desired value
//          if (clazzFolderName.equals(folderNameFromFileName)) {
//            // Create an instance of the class using the default constructor
//            Constructor<?> constructor = clazz.getDeclaredConstructor();
//            Object instance = constructor.newInstance();
//
////              // Do something with the initialized class instance
//            System.out.println("Initialized class: " + instance.getClass().getSimpleName());
////              // TODO: LOAD ADAPTERS
//
//            // Get the Method object for the method you want to call
//            Method targetMethod = clazz.getMethod("loadArticleFromFileName", File.class);
//
//            // Call the method on the instance
//            targetMethod.invoke(instance, fileToSerialize);
//
//          }
//        }
//
//
//      } catch (Exception e) {
//        System.out.println("[ERROR] - Error while trying to call the adapter for the file " + fileToSerialize.getName());
//        e.printStackTrace();
//      }
//    }

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
        String selected = (selectedFiles.contains(fileName)) ? "X" : "O";
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
        // selected file is fileNames[choice - 1]
        selectedFiles.add(fileNames[choice - 1]);
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
        for (File subfolder : subfolders) {
          folders.add(subfolder);
        }
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
            fileNames.add(file.getName().replace(".java", ""));
          }
        }
      }
    }
    return fileNames.toArray(new String[0]);
  }

  private void analyzeArticles() {
    String[] fileNames = getAllFilesInPath("./assets/");
    // TODO: CHOOSE FILES TO ANALYZE
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
        analyzer.analyze(fileNames[choice - 1], "output.txt");
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
