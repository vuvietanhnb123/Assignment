package data;

import java.io.*;
import java.util.*;
import java.lang.*;
import ui.Menu;
import util.MyUtils;

public class CarList extends ArrayList<Car> {

    private String carID, color, frameID, engineID;
    private Brand brand;
    Scanner sc = new Scanner(System.in);
    BrandList brandList;
    Menu mnu = new Menu();

    public CarList(BrandList bList) {
        brandList = bList;
    }

    public boolean loadFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String[] arr;
            String line = br.readLine();
            while ((line != null)) {
                arr = line.split(",");
                carID = arr[0].trim();
                int index = brandList.searchID(arr[1].trim());
                if (index != - 1) {
                    brand = brandList.get(index);
                }
                color = arr[2].trim();
                frameID = arr[3].trim();
                engineID = arr[4].trim();
                this.add(new Car(carID, brand, color, frameID, engineID));
                line = br.readLine();
            }
            br.close();
            return true;
        } catch (IOException e) {
            System.out.println("File " + filename + " not found !");
        }
        return false;
    }

    public boolean saveToFile(String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (Car i : this) {
                pw.println(i);
            }
            pw.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int searchID(String carID) {
        if (this.isEmpty()) {
            return -1;
        }
        for (int i = 0; i < this.size(); i++) {
            if (carID.equals(this.get(i).getCarID())) {
                return i;
            }
        }
        return -1;
    }

    public int searchFrame(String fID) {
        if (this.isEmpty()) {
            return -1;
        }
        for (int i = 0; i < this.size(); i++) {
            if (fID.equals(this.get(i).getFrameID())) {
                return i;
            }
        }
        return -1;
    }

    public int searchEngine(String eID) {
        if (this.isEmpty()) {
            return -1;
        }
        for (int i = 0; i < this.size(); i++) {
            if (eID.equals(this.get(i).getEngineID())) {
                return i;
            }
        }
        return -1;
    }

    public void addCar() {
        int pos;
        do {
            carID = MyUtils.getID("Input car ID: ", "The carID must not be null. Try again !");
            pos = searchID(carID);
            if (pos >= 0) {
                System.out.println("This brand ID is existed. Try another one!");
            }
        } while (pos != -1);

        Brand brand = mnu.ref_getChoice(brandList);
        color = MyUtils.getString("Input color: ", "The color must not be null. Try again !");
        do {
            frameID = MyUtils.getID("Input frame ID: ", "The frame ID must be in F0000 format and not be duplicated. Try again !", "F[0-9][0-9][0-9][0-9]");
            pos = searchFrame(frameID);
            if (pos >= 0) {
                System.out.println("This frame ID is existed. Try another one!");
            }
        } while (pos != -1);

        do {
            engineID = MyUtils.getID("Input engine ID: ", "The engine ID must be in F0000 format and not be duplicated. Try again !", "E[0-9][0-9][0-9][0-9]");
            pos = searchEngine(engineID);
            if (pos >= 0) {
                System.out.println("This engine ID is existed. Try another one!");
            }
        } while (pos != -1);
        this.add(new Car(carID, brand, color, frameID, engineID));
        System.out.println("Car has been added successfully");
    }

    public void printBasedBrandName() {
        String aPartOfBrandName;
        int count = 0;
        aPartOfBrandName = MyUtils.getString("Input brand name: ", "The brand name must not be null. Try again !");
        for (int i = 0; i < this.size(); i++) {
            if ((this.get(i).brand.getBrandName()).contains(aPartOfBrandName)) {
                System.out.println(this.get(i).screenString());
                count++;
            }
            if (count == 0) {
                System.out.println("No car is detected !");
            }
        }
    }

    public boolean removeCar() {
        int pos;
        String removedID;
        removedID = MyUtils.getID("Input car ID to remove: ", "The carID must not be null. Try again !");
        pos = searchID(removedID);
        if (pos >= 0) {
            this.remove(pos);
            System.out.println("Car has been removed successfully");
            return true;
        }
        return false;
    }

    public boolean updateCar() {
        String updatedID = MyUtils.getID("Input car ID to update: ", "The carID must not be null. Try again !");
        int pos = searchID(updatedID);
        if (pos == -1) {
            System.out.println("Not found !");
            return false;
        } else {
            Car x = this.get(pos);
            System.out.println("Here is the Car before updating");
            x.toString();
            Brand brand = mnu.ref_getChoice(brandList);
            color = MyUtils.getString("Input color: ", "The color must not be null. Try again !");
            do {
                frameID = MyUtils.getID("Input frame ID: ", "The frame ID must be in F0000 format and not be duplicated. Try again !", "F[0-9][0-9][0-9][0-9]");
                pos = searchFrame(frameID);
                if (pos >= 0) {
                    System.out.println("This frame ID is existed. Try another one!");
                }
            } while (pos != -1);
            do {
                engineID = MyUtils.getID("Input engine ID: ", "The engine ID must be in F0000 format and not be duplicated. Try again !", "E[0-9][0-9][0-9][0-9]");
                pos = searchEngine(engineID);
                if (pos >= 0) {
                    System.out.println("This engine ID is existed. Try another one!");
                }
            } while (pos != -1);
            x.setBrand(brand);
            x.setColor(color);
            x.setEngineID(engineID);
            x.setFrameID(frameID);
            System.out.println("Car has been updated successfully !");
            return true;
        }
    }

    public void listCars() {
        Collections.sort(this);
        for (Car car : this) {
            System.out.println(car.screenString());
        }
    }
}
