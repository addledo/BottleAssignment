import jdk.jshell.execution.Util;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String saveLocation = "bottles.dat";
        ArrayList<Bottle> bottles = loadBottles(saveLocation);

        while (true) {
            printMainMenu();
            int menuChoice = Utils.scanBoundedInt(1, 6, "#: ");
            switch (menuChoice) {
                case 1:
                    // View collection
                    printBottles(bottles);
                    System.out.println();
                    System.out.println("Enter 1 to filter by brand, or anything else to return.");
                    if (userWantsToFilter()) {
                        viewFilteredBottles(bottles);
                    }

                    break;
                case 2:
                    //Add bottle
                    bottles.add(createBottle());
                    break;
                case 3:
                    //Add flask
                    bottles.add(createFlask());
                    break;
                case 4:
                    //Modify bottles
                    modifyBottles(bottles);
                    break;
                case 5:
                    //Remove bottles
                    removeBottles(bottles);
                    break;
                case 6:
                    //Exit
                    saveBottles(bottles, saveLocation);
                    return;
            }
        }

    }


    public static void printMainMenu() {
        System.out.printf("%nMENU %n");
        System.out.println("1. View bottles");
        System.out.println("2. Add a bottle");
        System.out.println("3. Add a flask");
        System.out.println("4. Modify bottles");
        System.out.println("5. Remove bottles");
        System.out.println("6. Exit");
    }

    public static void printModifyMenu() {
        System.out.println();
        System.out.println("0. Return");
        System.out.println("1. Change the contents of a bottle");
        System.out.println("2. Sort bottles by brand");
        System.out.println("3. Sort bottles by volume");
    }

    public static void printRemoveMenu() {
        System.out.println();
        System.out.println("0. Return");
        System.out.println("1. Delete a single bottle");
        System.out.println("2. Delete bottles not in volume range");
    }


    public static Bottle createBottle() {
        System.out.printf("%nPlease enter the details of the bottle you'd like to add. %n");
        String brand = Utils.getBoundedString("Brand: ", 35, false);
        int volumeInML = askVolume();
        System.out.println();
        Material material = askMaterial();
        return new Bottle(brand, volumeInML, material);
    }

    public static Flask createFlask() {
        System.out.printf("%nPlease enter the details of the flask you'd like to add. %n");
        String brand = Utils.getBoundedString("Brand: ", 35, false);
        int volumeInML = askVolume();
        Material material = askMaterial();
        int keepWarmHours = askKeepWarmHours();
        return new Flask(brand, volumeInML, material, keepWarmHours);
    }

    private static int askVolume() {
        int volumeInML;
        volumeInML = Utils.scanBoundedInt(0, 10000, "Volume (ml): ");
        System.out.println();
        return volumeInML;
    }

    public static Material askMaterial() {
        printMaterials();
        int selection;
        int minSelection = 1;
        int maxSelection = Material.values().length;
        selection = Utils.scanBoundedInt(minSelection, maxSelection, "Material: ");
        selection -= 1; // Because list displayed to user starts from 1
        return Material.values()[selection];
    }

    private static int askKeepWarmHours() {
        // TODO        Is it better to separate code like below or to just return the result?
        System.out.printf("%nPlease enter the keep warm time of your flask.");
        int keepWarmHours;
        keepWarmHours = Utils.scanInt("Hours: ");
        return keepWarmHours;
    }

    private static String askContents() {
        System.out.println();
        System.out.println("What does it contain?");
        System.out.println("If empty, just press enter.");
        System.out.println();
        return Utils.getBoundedString("Contains: ", 45, true);
    }

    public static void printBottles(ArrayList<Bottle> bottles) {
        System.out.println();
        System.out.println("BOTTLES: ");
        Utils.printNumberedListFrom1(bottles);
        if (bottles.isEmpty()) {
            System.out.println("No bottles found.");
        }
    }

    public static boolean userWantsToFilter() {
        boolean userWantsToFilter = false;
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim();
        if (input.equals("1")) {
            userWantsToFilter = true;
        }
        return userWantsToFilter;
    }

    private static void viewFilteredBottles(ArrayList<Bottle> bottles) {
        ArrayList<String> brands = getBrands(bottles);
        String chosenBrandName = chooseBrand(brands);
        System.out.println();
        printBottlesMatchingBrand(bottles, chosenBrandName);
        System.out.println();
        System.out.println("Press enter to return");
        Utils.waitForUser();
    }

    private static ArrayList<String> getBrands(ArrayList<Bottle> bottles) {
        HashSet<String> brands = new HashSet<>();
        for (Bottle bottle : bottles) {
            String brand = bottle.getBrand();
            brands.add(brand);
        }
        return new ArrayList<>(brands);
    }

    private static String chooseBrand(ArrayList<String> brands) {
        System.out.println("Choose a brand: ");
        Utils.printNumberedListFrom1(brands);
        int len = brands.size();
        int brandChoiceNumber = Utils.scanBoundedInt(1, len, "#: ");
        brandChoiceNumber--;     //Adjusts to align with displayed list starting from 1
        return brands.get(brandChoiceNumber);
    }

    private static void printBottlesMatchingBrand(ArrayList<Bottle> bottles, String chosenBrandName) {
        System.out.println(chosenBrandName + " bottles:");
        int counter = 1;
        for (Bottle bottle : bottles) {
            String brand = bottle.getBrand();
            boolean brandMatches = brand.equals(chosenBrandName);
            if (brandMatches) {
                System.out.printf("[%d] %s %n", counter, bottle);
                counter++;
            }
        }
    }

    public static void printMaterials() {
        for (int i = 0; i < Material.values().length; i++) {
            String material = Material.values()[i].displayName;
            System.out.printf("[%d] %s%n", i + 1, material);
        }
    }

    public static void removeBottles(ArrayList<Bottle> bottles) {
        printRemoveMenu();
        int menuChoice = Utils.scanBoundedInt(0, 2, "#: ");
        switch (menuChoice) {
            case 0:
                return;
            case 1:
                removeSingleBottle(bottles);
                break;
            case 2:
                deleteByVolume(bottles);
        }
    }

    public static void removeSingleBottle(ArrayList<Bottle> bottles) {
        if (bottles.isEmpty()) {
            System.out.println("There are no bottles to remove. Press enter to go back");
            Utils.waitForUser();
            return;
        }
        int selection = chooseBottle(bottles);
        if (selection == -1) {
            return;
        }
        bottles.remove(selection);
    }

    public static void deleteByVolume(ArrayList<Bottle> bottles) {
        int lowerBound = Utils.scanInt("Lower bound: ");
        int upperBound = Utils.scanInt("Upper bound: ");
        Iterator<Bottle> bottleIterator = bottles.iterator();
        while (bottleIterator.hasNext()) {
            Bottle bottle = bottleIterator.next();
            int volume = bottle.getVolumeML();
            if (upperBound < volume || volume < lowerBound) {
                bottleIterator.remove();
            }
        }
    }

    public static void modifyBottles(ArrayList<Bottle> bottles) {
        printModifyMenu();
        int menuChoice = Utils.scanBoundedInt(0, 3, "#: ");
        switch (menuChoice) {
            case 0:
                return;
            case 1:
                //Change contents
                Bottle bottle = bottles.get(chooseBottle(bottles));
                String contents = askContents();
                bottle.setContents(contents);
                return;
            case 2:
                //TODO   Sort by brand
            case 3:
                //TODO   Sort by volume
        }
    }

    private static Integer chooseBottle(ArrayList<Bottle> bottles) {
        printBottles(bottles);
        int selection = Utils.scanInt("Enter the number of the bottle: ");
        while (selection < 0 || selection > bottles.size()) {
            System.out.println("That bottle does not exist.");
            System.out.println("Try again or enter 0 to go back.");
            selection = Utils.scanInt("#: ");
        }
        selection -= 1; //User list counts from 1
        return selection; //Returns -1 for no bottle selection
    }

    public static void saveBottles(ArrayList<Bottle> bottles, String location) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(location);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(bottles);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Problem converting file: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<Bottle> loadBottles(String location) {
        ArrayList<Bottle> bottles = new ArrayList<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(location);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            bottles = (ArrayList<Bottle>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            System.out.println("Could not find file: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Problem converting file data to object: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Could not find class representing saved object: " + e.getMessage());
        }
        return bottles;
    }
}