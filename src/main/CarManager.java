package main;

import data.BrandList;
import data.CarList;
import java.util.*;
import java.lang.*;
import ui.Menu;
import util.MyUtils;

//lớp main dùng để triển khai chương trình qua các lựa chọn của người dùng
//người dev: Vũ Việt Anh
public class CarManager {

    public static void main(String[] args) {
        boolean statusFlag = false;
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
        menu.addNewOption("7 - List all cars");
        menu.addNewOption("8 - List all cars in ascending order of brand names");
        menu.addNewOption("9 - List cars based on a part of an input brand name");
        menu.addNewOption("10 - Generate random cars");
        menu.addNewOption("11 - Add a car");
        menu.addNewOption("12 - Remove a car based on its ID");
        menu.addNewOption("13 - Remove a car based on its index");
        menu.addNewOption("14 - Update a car based on its ID");
        menu.addNewOption("15 - Save cars to file, named cars.txt");
        menu.addNewOption("16 - Exit");

        do {
            menu.printMenu();
            choice = menu.int_getChoice();
            switch (choice) {
                case 1:
                    brandList.listBrands();
                    break;
                case 2:
                    brandList.genRandomBrand();
                    statusFlag = true;
                    break;
                case 3:
                    brandList.addBrand();
                    statusFlag = true;
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
                    statusFlag = true;
                    break;
                case 6:
                    brandList.saveToFile(fileBrandsName);
                    statusFlag = false;
                    break;
                case 7: 
                    carList.listFullCars();
                    break;
                case 8:
                    carList.listCars();
                    break;
                case 9:
                    carList.printBasedBrandName();
                    break;
                case 10:
                    carList.genRandomCar();
                    statusFlag = true;
                    break;
                case 11:
                    carList.addCar();
                    statusFlag = true;
                    break;
                case 12:
                    boolean check = carList.removeCar();
                    if (!check) {
                        System.out.println("Car has been not found");
                    }
                    else statusFlag = true;
                    break;
                case 13:
                    check = carList.removeCarBaseOnIndex();
                    if (!check) {
                        System.out.println("Car has been not found");
                    }
                    else statusFlag = true;
                    break;
                case 14:
                    check = carList.updateCar();
                    if (!check) {
                        System.out.println("Car has been not found");
                    }
                    else statusFlag = true;
                    break;
                case 15:
                    carList.saveToFile(fileCarsName);
                    statusFlag = false;
                    break;     
                case 16:
                    if (statusFlag == true) {
                        System.out.println("Save your changes to file brands.txt and cars.txt");
                        Menu mnu1 = new Menu("Choose your option");
                        mnu1.addNewOption("1. Save");
                        mnu1.addNewOption("2. Don't save");
                        mnu1.printMenu();
                        int choice1 = mnu1.int_getChoice();
                        switch (choice1) {
                            case 1:
                                brandList.saveToFile(fileBrandsName);
                                carList.saveToFile(fileCarsName);
                                break;
                            case 2:                          
                                break;
                        }
                    }    
                    break;
            }
        } while ((choice > 0) && (choice <= 15));
    }
}
