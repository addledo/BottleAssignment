import java.io.*;
import java.util.*;

public class Main {
    private static final String SAVE_LOCATION = "bottles.dat";   //TODO Where to put this?

    public static void main(String[] args) {
        ArrayList<Bottle> bottles = loadBottles(SAVE_LOCATION);
        while (true) {
            printMainMenu();
            int menuChoice = Utils.scanBoundedInt(1, 6, "#: ");
            switch (menuChoice) {
                case 1:
                    viewCollection(bottles);
                    break;
                case 2:
                    bottles.add(newBottle());
                    break;
                case 3:
                    bottles.add(newFlask());
                    break;
                case 4:
                    changeContentsOfABottle(bottles);
                    break;
                case 5:
                    removeBottles(bottles);
                    break;
                case 6:
                    //Exit
                    saveBottles(bottles, SAVE_LOCATION);
                    return;
            }
        }

    }

    public static void printMainMenu() {
        System.out.println();
        System.out.println("-- MENU --");
        System.out.println("1. View bottles");
        System.out.println("2. Add a bottle");
        System.out.println("3. Add a flask");
        System.out.println("4. Modify contents");
        System.out.println("5. Remove bottles");
        System.out.println("6. Exit");
    }

    public static void viewCollection(ArrayList<Bottle> bottles) {    //TODO Is this method size ok?
        while (true) {
            printBottles(bottles);
            if (bottles.isEmpty()) {
                Utils.waitForUser();
                return;
            }
            printViewMenuMiniOptions();
            int firstChoice = Utils.scanBoundedInt(0, 1, "#: ");
            if (firstChoice == 0) {
                return;
            }
            printViewMenuFullOptions();
            int menuChoice = Utils.scanBoundedInt(0, 6, "#: ");
            switch (menuChoice) {
                case 0:
                    return;
                case 1:
                    viewFlasks(bottles);
                    return;
                case 2:
                    //Sort by volume
                    Collections.sort(bottles);
                    break;
                case 3:
                    sortByBrand(bottles);
                    break;
                case 4:
                    filterBottles(bottles);
                    break;
                case 5:
                    displayTotalVolume(bottles, false);
                    break;
                case 6:
                    displayAverageVolume(bottles, false);
                    break;
            }
        }
    }

    private static void printViewMenuMiniOptions() {
        System.out.println();
        System.out.println("0. Return");
        System.out.println("1. Show more options");
    }

    private static void printViewMenuFullOptions() {
        System.out.println();
        System.out.println("0. Return");
        System.out.println("1. View flasks only");
        System.out.println("2. Sort by volume");
        System.out.println("3. Sort by brand");
        System.out.println("4. Filter by brand");
        System.out.println("5. Calculate total volume");
        System.out.println("6. Calculate average volume");
    }

    public static void viewFlasks(ArrayList<Bottle> bottles) {
        ArrayList<Flask> flasks = getFlasks(bottles);
        while (true) {
            printFlasks(flasks);
            printFlaskMenuOptions();
            int menuChoice = Utils.scanBoundedInt(0, 2, "#: ");
            switch (menuChoice) {
                case 0:
                    return;
                case 1:
                    Collections.sort(flasks);
                    break;
                case 2:
                    sortFlasksByWarmTime(flasks);
                    break;

                    // TODO    Should I add a default clause?
                    // TODO    What should it do? Print message / throw exception?
            }
        }
    }

    private static void printFlaskMenuOptions() {
        System.out.println();
        System.out.println("0. Return");
        System.out.println("1. Sort by volume");
        System.out.println("2. Sort by keep warm time");
    }

    public static void printFlasks(ArrayList<Flask> flasks) {
        System.out.println();
        if (flasks.isEmpty()) {
            System.out.println("No flasks found");
            return;
        }
        System.out.println("Flasks: ");
        Utils.printNumberedListFrom1(flasks);
    }

    private static ArrayList<Flask> getFlasks(ArrayList<Bottle> bottles) {
        ArrayList<Flask> flasks = new ArrayList<>();
        for (Bottle bottle : bottles) {
            if (bottle instanceof Flask) {
                flasks.add((Flask) bottle);
            }
        }
        return flasks;
    }

    private static void sortFlasksByWarmTime(ArrayList<Flask> flasks) {
        flasks.sort((Flask o1, Flask o2) -> o1.getKeepWarmHours() - o2.getKeepWarmHours());
    }

    public static Bottle newBottle() {
        System.out.println();
        System.out.println("Please enter the details of the bottle you'd like to add.");
        // TODO   Should I extract this method if it's used twice:
        String brand = Utils.scanBoundedString("Brand: ", 20, false);
        int volumeInML = askVolume();
        System.out.println();
        Material material = askMaterial();
        return new Bottle(brand, volumeInML, material);
    }

    public static Flask newFlask() {
        System.out.println();
        System.out.println("Please enter the details of the flask you'd like to add.");
        String brand = Utils.scanBoundedString("Brand: ", 20, false);
        int volumeInML = askVolume();
        Material material = askMaterial();
        int keepWarmHours = askKeepWarmHours();
        return new Flask(brand, volumeInML, material, keepWarmHours);
    }

    public static int askVolume() {
        // TODO    Layout like this or single return statement?
        int volumeInML;
        volumeInML = Utils.scanBoundedInt(1, 10000, "Volume (ml): ");
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

    public static int askKeepWarmHours() {
        System.out.println();
        System.out.println("Please enter the keep warm time of your flask.");
        return Utils.scanInt("Hours: ");
    }

    public static String askContents() {
        System.out.println();
        System.out.println("What does it contain?");
        System.out.println("If empty, just press enter.");
        System.out.println();
        return Utils.scanBoundedString("Contains: ", 40, true);
    }

    public static void printBottles(ArrayList<Bottle> bottles) {
        System.out.println();
        System.out.println("-- BOTTLES --");
        Utils.printNumberedListFrom1(bottles);
        if (bottles.isEmpty()) {
            System.out.println("No bottles found.");
        }
    }

    public static void filterBottles(ArrayList<Bottle> bottles) {
        ArrayList<Bottle> filteredBottles = getFilteredList(bottles);
        while (true) {
            printFilteredBottles(filteredBottles);
            // TODO Should I extract this? :
            System.out.println("0. Return");
            System.out.println("1. Calculate total volume for this brand");
            System.out.println("2. Calculate average volume for this brand");
            int menuChoice = Utils.scanBoundedInt(0, 2, "#: ");
            switch (menuChoice) {
                case 0:
                    return;
                case 1:
                    displayTotalVolume(filteredBottles, true);
                    break;
                case 2:
                    displayAverageVolume(filteredBottles, true);
                    break;
            }
        }
    }

    public static void printFilteredBottles(ArrayList<Bottle> filteredBottles) {
        System.out.println();
        String brand = filteredBottles.getFirst().getBrand();
        System.out.println(brand + " bottles:");
        Utils.printNumberedListFrom1(filteredBottles);
        System.out.println();
    }

    public static ArrayList<Bottle> getFilteredList(ArrayList<Bottle> bottles) {
        ArrayList<String> existingBrands = getBrands(bottles);
        String chosenBrandName = chooseBrandFrom(existingBrands);
        return getBottlesMatchingBrand(bottles, chosenBrandName);
    }

    public static ArrayList<String> getBrands(ArrayList<Bottle> bottles) {
        HashSet<String> brands = new HashSet<>();
        for (Bottle bottle : bottles) {
            String brand = bottle.getBrand();
            brands.add(brand);
        }
        ArrayList<String> brandsList = new ArrayList<>(brands);
        Collections.sort(brandsList);
        return brandsList;
    }

    public static String chooseBrandFrom(ArrayList<String> brands) {
        System.out.println();
        System.out.println("Choose a brand: ");
        Utils.printNumberedListFrom1(brands);
        int numOfBrands = brands.size();
        int brandChoiceNumber = Utils.scanBoundedInt(1, numOfBrands, "#: ");
        brandChoiceNumber--;     //Because printed list starts at 1
        return brands.get(brandChoiceNumber);
    }

    public static ArrayList<Bottle> getBottlesMatchingBrand(ArrayList<Bottle> bottles, String chosenBrandName) {
        ArrayList<Bottle> matchingBottles = new ArrayList<>();
        for (Bottle bottle : bottles) {
            String brand = bottle.getBrand();
            boolean brandMatches = brand.equals(chosenBrandName);
            if (brandMatches) {
                matchingBottles.add(bottle);
            }
        }
        return matchingBottles;
    }

    public static void printMaterials() {
        for (int i = 0; i < Material.values().length; i++) {
            String material = Material.values()[i].displayName;
            System.out.printf("[%d] %s%n", i + 1, material);
        }
    }

    public static void removeBottles(ArrayList<Bottle> bottles) {
        if (bottles.isEmpty()) {
            System.out.println();
            System.out.print("There are no bottles to remove.");
            Utils.waitForUser();
            return;
        }
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

    public static void printRemoveMenu() {
        System.out.println();
        System.out.println("-- REMOVE MENU --");
        System.out.println("0. Return");
        System.out.println("1. Delete a single bottle");
        System.out.println("2. Delete bottles not in volume range");
    }

    public static void removeSingleBottle(ArrayList<Bottle> bottles) {
        //Optional bottle allows for cancelling of the deletion process
        //if the user changes their mind or started in error.
        Optional<Bottle> optionalBottle = chooseBottleFrom(bottles);
        if (optionalBottle.isEmpty()) {
            return;
        }
        Bottle chosenBottle = optionalBottle.get();
        bottles.remove(chosenBottle);
    }

    public static void deleteByVolume(ArrayList<Bottle> bottles) {
        //Deletes bottles NOT in range
        //The same value can be entered for both bounds to preserve only a single volume size
        int lowerBound = Utils.scanBoundedInt(0, 10000, "Lower bound: ");
        int upperBound = Utils.scanBoundedInt(lowerBound, 10000, "Upper bound: ");
        Iterator<Bottle> bottleIterator = bottles.iterator();
        while (bottleIterator.hasNext()) {
            Bottle bottle = bottleIterator.next();
            int volume = bottle.getVolumeInML();
            if (upperBound < volume || volume < lowerBound) {
                bottleIterator.remove();
            }
        }
    }

    public static void changeContentsOfABottle(ArrayList<Bottle> bottles) {
        if (bottles.isEmpty()) {
            System.out.println();
            System.out.print("There are no bottles to modify.");
            Utils.waitForUser();
            return;
        }
        //Optional bottle allows for cancelling of the edit process
        //if the user changes their mind or started in error.
        Optional<Bottle> optionalBottle = chooseBottleFrom(bottles);
        if (optionalBottle.isEmpty()) {
            return;
        }
        Bottle bottle = optionalBottle.get();
        String newContents = askContents();
        bottle.setContents(newContents);
    }

    public static void sortByBrand(ArrayList<Bottle> bottles) {
        BottleBrandComparator brandComparator = new BottleBrandComparator();
        bottles.sort(brandComparator);
    }

    public static int calculateTotalVolume(ArrayList<Bottle> bottles) {
        int totalVolume = 0;
        for (Bottle bottle : bottles) {
            totalVolume += bottle.getVolumeInML();
        }
        return totalVolume;
    }

    public static void displayTotalVolume(ArrayList<Bottle> bottles, boolean isBrand) {
        int totalVolume = calculateTotalVolume(bottles);
        System.out.println();
        String volumeUnit = "ml";
        String brand = isBrand ? " for " + bottles.getFirst().getBrand() : "";
        System.out.printf("Total volume%s: %d%s", brand, totalVolume, volumeUnit);
        Utils.waitForUser();
    }

    public static int calculateAverageVolume(ArrayList<Bottle> bottles) {
        int totalVolume = calculateTotalVolume(bottles);
        int n = bottles.size();
        float averageVolume = (float) totalVolume / n;
        return Math.round(averageVolume);
    }

    public static void displayAverageVolume(ArrayList<Bottle> bottles, boolean isBrand) {
        System.out.println();
        int averageVolume = calculateAverageVolume(bottles);
        String volumeUnit = "ml";
        String brand = isBrand ? " for " + bottles.getFirst().getBrand() : "";
        System.out.printf("Average volume%s: %d%s", brand, averageVolume, volumeUnit);
        Utils.waitForUser();
    }

    public static Optional<Bottle> chooseBottleFrom(ArrayList<Bottle> bottles) {
        printBottles(bottles);
        System.out.println();
        System.out.println("Enter 0 to cancel");
        int numOfBottles = bottles.size();
        int bottleNumber = Utils.scanBoundedInt(0, numOfBottles, "Enter the number of the bottle: ");
        if (bottleNumber == 0) {
            return Optional.empty();
        }
        Bottle bottle = bottles.get(bottleNumber - 1); //Printed list counts from 1
        return Optional.of(bottle);
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