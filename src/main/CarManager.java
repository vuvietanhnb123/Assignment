package main;

import data.BrandList;
import data.CarList;
import java.util.*;
import java.lang.*;
import ui.Menu;
import util.MyUtils;

public class CarManager {

    public static void main(String[] args) {
        int choice;
        String brandID, brandCarID;
        Menu menu = new Menu("CAR MANAGER APPLICATION");
        String fileCarsName = "F:\\Semester 2\\PRO192_Official\\Assignment\\src\\data\\cars.txt";
        String fileBrandsName = "F:\\Semester 2\\PRO192_Official\\Assignment\\src\\data\\brands.txt";
        BrandList brandList = new BrandList();//test github
        CarList carList = new CarList(brandList);
        brandList.loadFromFile(fileBrandsName);
        carList.loadFromFile(fileCarsName);
        menu.addNewOption("1 - List all brands");
        menu.addNewOption("2 - Add a new brand");
        menu.addNewOption("3 - Search a brand based on its ID");
        menu.addNewOption("4 - Update a brand");
        menu.addNewOption("5 - Save brands to the file, named brands.txt");
        menu.addNewOption("6 - List all cars in ascending order of brand names");
        menu.addNewOption("7 - List cars based on a part of an input brand name");
        menu.addNewOption("8 - Add a car");
        menu.addNewOption("9 - Remove a car based on its ID");
        menu.addNewOption("10 - Update a car based on its ID");
        menu.addNewOption("11 - Save cars to file, named cars.txt");

        do {
            menu.printMenu();
            choice = menu.int_getChoice();
            switch (choice) {
                case 1:
                    brandList.listBrands();
                    break;
                case 2:
                    brandList.addBrand();
                    break;
                case 3:
                    brandID = MyUtils.getID("Input brand ID: ", "The brandID must not be null. Try again !");
                    if (brandList.searchID(brandID) == -1) {
                        System.out.print("Brand ID not found !");
                    } else {
                        System.out.println(brandList.get(brandList.searchID(brandID)).toString());
                    }
                    break;
                case 4:
                    brandList.updateBrand();
                    break;
                case 5:
                    brandList.saveToFile(fileBrandsName);
                    break;
                case 6:
                    carList.listCars();
                    break;
                case 7:
                    brandCarID = MyUtils.getString("Input brand (you can input a part of it): ", "The brand must not be null. Try again !");
                    if (carList.searchID(brandCarID) != -1) {
                        System.out.println(carList.get(carList.searchID(brandCarID)));
                    } else {
                        System.out.println("No result");
                    }
                    break;
                case 8:
                    carList.addCar();
                    break;
                case 9:
                    boolean check = carList.removeCar();
                    if (!check) {
                        System.out.println("Car has been not found");
                    }
                    break;

                case 10:
                    check = carList.updateCar();
                    if (!check) {
                        System.out.println("Car has been not found");
                    }
                    break;

                case 11:
                    carList.saveToFile(fileCarsName);
                    break;
            }
        } while ((choice > 0) && (choice <= 11));
    }
}
