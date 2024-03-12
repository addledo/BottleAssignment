import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Main {

    public static void main(String[] args) {
        String saveLocation = "bottles.dat";
        ArrayList<Bottle> bottles = loadBottles(saveLocation);

        while (true) {
            printMenu();
            int menuChoice = getMenuChoice();
            switch (menuChoice) {
                case 1:
                    // View collection
                    printBottles(bottles);
                    System.out.printf("%nPress enter to return to the menu.");
                    Utils.waitForUser();
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
                    modifyItems(bottles);
                    // IDEAS:
                    // ADD CONTENTS
                    // sort
                    // calculations
                    // TODO   Implement these
                    break;
                case 5:
                    //Remove bottles
                    removeItems(bottles);
                    break;
                case 6:
                    //Exit
                    saveBottles(bottles, saveLocation);
                    return;
            }
        }

    }


    public static void printMenu() {
        System.out.printf("%nMENU %n");
        System.out.println("1. View items");
        System.out.println("2. Add a bottle");
        System.out.println("3. Add a flask");
        System.out.println("4. Modify items");
        System.out.println("5. Remove items");
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

    public static int getMenuChoice() {
        int menuChoice;
        menuChoice = Utils.scanBoundedInt(1, 6, "#: ");
        return menuChoice;
    }

    public static void removeItems(ArrayList<Bottle> bottles) {
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

    public static void modifyItems(ArrayList<Bottle> bottles) {
        // TODO             FINISH THIS
        printModifyMenu();
        int menuChoice = Utils.scanBoundedInt(0, 3, "#: ");
        switch (menuChoice) {
            case 0:
                return;
            case 1:
                //Change contents
                Bottle bottle = bottles.get(chooseBottle(bottles));
                String contents = getContents();
                bottle.setContents(contents);
                return;
            case 2:
                //
        }
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

    public static Bottle createBottle() {
        System.out.printf("%nPlease enter the details of the bottle you'd like to add. %n");
        String brand = Utils.getBoundedString("Brand: ", 35, false);
        int volumeInML = getVolumeInML();
        System.out.println();
        Material material = chooseMaterial();
        return new Bottle(brand, volumeInML, material);
    }

    public static Bottle createFlask() {
        System.out.printf("%nPlease enter the details of the flask you'd like to add. %n");
        String brand = Utils.getBoundedString("Brand: ", 35, false);
        int volumeInML = getVolumeInML();
        Material material = chooseMaterial();
        int keepWarmHours = getKeepWarmHours();
        return new Flask(brand, volumeInML, material, keepWarmHours);
    }

    private static int getKeepWarmHours() {
        // TODO        Can I name the function this? (same as flask getter)
        // TODO        Is it better to separate code like below or to just return the result?
        System.out.printf("%nPlease enter the keep warm time of your flask.");
        int keepWarmHours;
        keepWarmHours = Utils.scanInt("Hours: ");
        return keepWarmHours;
    }

    private static String getContents() {
        System.out.println();
        System.out.println("What does it contain?");
        System.out.println("If empty, just press enter.");
        System.out.println();
        return Utils.getBoundedString("Contains: ", 45, true);
    }

    private static int getVolumeInML() {
        int volumeInML;
        volumeInML = Utils.scanBoundedInt(0, 10000, "Volume (ml): ");
        System.out.println();
        return volumeInML;
    }


    public static Material chooseMaterial() {
        printMaterials();
        int selection;
        int minSelection = 1;
        int maxSelection = Material.values().length;
        selection = Utils.scanBoundedInt(minSelection, maxSelection, "Material: ");
        selection -= 1; // Because list displayed to user starts from 1
        return Material.values()[selection];
    }

    public static void printMaterials() {
        for (int i = 0; i < Material.values().length; i++) {
            String material = Material.values()[i].displayName;
            System.out.printf("[%d] %s%n", i + 1, material);
        }
    }

    public static void printBottles(ArrayList<Bottle> bottles) {
        System.out.printf("%nBOTTLES: %n");
        for (int i = 0; i < bottles.size(); i++) {
            String bottleInfo = formatBottle(i + 1, bottles.get(i));
            System.out.println(bottleInfo);
        }
        if (bottles.isEmpty()) {
            System.out.println("No bottles found.");
        }

    }

    public static String formatBottle(int num, Bottle bottle) {
        String brand = bottle.getBrand();
        int volume = bottle.getVolumeML();
        String material = bottle.getMaterial().displayName;
        String contents = "Contains: " + bottle.getContents();
        return String.format("[%d] %s, %dml, %s, %s", num, brand, volume, material, contents);
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