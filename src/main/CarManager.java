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
        menu.addNewOption("2 - Generate random brands");
        menu.addNewOption("3 - Add a new brand");
        menu.addNewOption("4 - Search a brand based on its ID");
        menu.addNewOption("5 - Update a brand");
        menu.addNewOption("6 - Save brands to the file, named brands.txt");
        menu.addNewOption("7 - List all cars in ascending order of brand names");
        menu.addNewOption("8 - List cars based on a part of an input brand name");
        menu.addNewOption("9 - Generate random cars");
        menu.addNewOption("10 - Add a car");
        menu.addNewOption("11 - Remove a car based on its ID");
        menu.addNewOption("12 - Update a car based on its ID");
        menu.addNewOption("13 - Save cars to file, named cars.txt");

        do {
            menu.printMenu();
            choice = menu.int_getChoice();
            switch (choice) {
                case 1:
                    brandList.listBrands();
                    break;
                case 2:
                    brandList.genRandomBrand();
                    break;
                case 3:
                    brandList.addBrand();
                    break;
                case 4:
                    brandID = MyUtils.getID("Input brand ID: ", "The brandID must not be null. Try again !");
                    if (brandList.searchID(brandID) == -1) {
                        System.out.print("Brand ID not found !");
                    } else {
                        System.out.println(brandList.get(brandList.searchID(brandID)).toString());
                    }
                    break;
                case 5:
                    brandList.updateBrand();
                    break;
                case 6:
                    brandList.saveToFile(fileBrandsName);
                    break;
                case 7:
                    carList.listCars();
                    break;
                case 8:
                    carList.printBasedBrandName();
                    break;
                case 9:
                    carList.genRandomCar();
                    break;
                case 10:
                    carList.addCar();
                    break;
                case 11:
                    boolean check = carList.removeCar();
                    if (!check) {
                        System.out.println("Car has been not found");
                    }
                    break;
                case 12:
                    check = carList.updateCar();
                    if (!check) {
                        System.out.println("Car has been not found");
                    }
                    break;
                case 13:
                    carList.saveToFile(fileCarsName);
                    break;                    
            }
        } while ((choice > 0) && (choice <= 13));
    }
}
