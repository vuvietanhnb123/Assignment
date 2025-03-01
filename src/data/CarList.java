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
    private ArrayList<String> carIDList = new ArrayList<>();
    private ArrayList<String> frameIDList = new ArrayList<>();

    public CarList(BrandList bList) {
        brandList = bList;
    }

    ////hàm để load data từ file cars.txt. 
    //Người dev: Vũ Việt Anh
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
                carIDList.add(carID);
                frameIDList.add(frameID);
                line = br.readLine();
            }
            br.close();
            return true;
        } catch (IOException e) {
            System.out.println("File " + filename + " not found !");
        }
        return false;
    }

    //hàm để lưu data từ chương trình vào file cars.txt. 
    //Người dev: Vũ Việt Anh
    public boolean saveToFile(String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (Car car : this) {
                String line = car.getCarID() + ", " + car.getBrand().getBrandID() + ", " + car.getColor() + ", "
                        + car.getFrameID() + ", " + car.getEngineID();
                pw.println(line);
            }
            pw.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // hàm dùng để tạo 1 car ngẫu nhiên 
    //cách xử lí bằng cách sinh ngẫu nhiên từng đặc tính của 1 car thông qua MyUltil
    //người dev: Vũ Việt Anh
    public void genRandomCar() {
        System.out.print("Enter number of cars you want to generate: ");
        int numberOfGenerations = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < numberOfGenerations; i++) {
            carID = MyUtils.genRandomCarID(carIDList);
            brand = brandList.get(MyUtils.getRandomIntInRange(0, brandList.size() - 1));
            color = MyUtils.genRandomColor();
            frameID = MyUtils.genRandomFrameID(frameIDList);
            engineID = "E" + frameID.substring(1);
            this.add(new Car(carID, brand, color, frameID, engineID));
        }
        System.out.println(numberOfGenerations + " random car(s) has been generated");
    }

    //hàm để search 1 car thông qua ID.
    //cách xử lý: duyệt qua list các car rồi tìm brand trùng với id được nhập
    //người dev: Nguyễn Thanh Tùng
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

    //hàm để search 1 Frame thông qua ID.
    //cách xử lý: duyệt qua list các car rồi tìm car có frameID trùng với id được nhập
    //lưu ý frameID phải đúng format và không được trùng
    //người dev: Nguyễn Thanh Tùng
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

    //hàm để search 1 engine thông qua ID.
    //cách xử lý: duyệt qua list các car rồi tìm car có eID trùng với id được nhập
    //lưu ý eID phải đúng format và không được trùng
    //người dev: Nguyễn Thanh Tùng
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

    //hàm dùng để add 1 car mới
    //cách xử lý: carID mới không được trùng, và mỗi đặc tính được nhập thỏa mãn yêu cầu
    //người dev: Nguyễn Tuấn Anh
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
            frameID = MyUtils.getID("Input frame ID: ", "The frame ID must be in F00000 format and not be duplicated. Try again !", "F[0-9][0-9][0-9][0-9][0-9]");
            pos = searchFrame(frameID);
            if (pos >= 0) {
                System.out.println("This frame ID is existed. Try another one!");
            }
        } while (pos != -1);

        do {
            engineID = MyUtils.getID("Input engine ID: ", "The engine ID must be in E00000 format and not be duplicated. Try again !", "E[0-9][0-9][0-9][0-9][0-9]");
            pos = searchEngine(engineID);
            if (pos >= 0) {
                System.out.println("This engine ID is existed. Try another one!");
            }
        } while (pos != -1);
        this.add(new Car(carID, brand, color, frameID, engineID));
        System.out.println("Car has been added successfully");
    }

    //hàm để in car dựa vào một phần của brand name
    //cách xử lý: duyệt qua list các car rồi xem car nào có chứa 1 phần brand name đã nhập
    //sử dụng contain
    //người dev: Lê Trần Quang Anh
    public void printBasedBrandName() {
        String aPartOfBrandName;
        int count = 0;
        aPartOfBrandName = MyUtils.getString("Input brand name: ", "The brand name must not be null. Try again !");
        for (int i = 0; i < this.size(); i++) {
            if ((this.get(i).brand.getBrandName()).contains(aPartOfBrandName)) {
                System.out.println(this.get(i).screenString());
                count++;
            }
        }
        if (count == 0) {
            System.out.println("No car is detected !");
        }
    }

    //hàm để xóa 1 car dựa vào id
    //cách xử lý: duyệt qua list các car rồi xóa car trùng với id đã nhập
    //người dev: Nguyễn Tuấn Anh
    public boolean removeCar() {
        //remove base on ID
        this.listFullCars();
        int pos;
        String removedID;
        removedID = MyUtils.getID("Input car ID to remove: ", "The carID must not be null. Try again !");
        pos = searchID(removedID);
        if (pos >= 0) {
            System.out.println("Are you sure to remove car with id " + this.get(pos).getCarID());
            System.out.print("Please choose Y/N: ");
            String choice = sc.nextLine();
            if (choice.equalsIgnoreCase("Y")) {
                this.remove(pos);
                System.out.println("Car has been removed successfully");
                return true;
            } else {
                System.out.println("The car with id " + this.get(pos).getCarID() + " has been retained");
                return true;
            }
        }
        return false;
    }

    //hàm để xóa 1 car dựa vào index
    //cách xử lý: duyệt qua list các car rồi xóa car trùng với index đã nhập
    //người dev: Vũ Việt Anh
    public boolean removeCarBaseOnIndex() {
        this.listFullCars();
        int index;
        index = MyUtils.getAnInteger("Input index of car to remove: ", "The index must be a number. Try again !");
        if (index - 1 >= 0 && index - 1 < this.size()) {
            System.out.println("Are you sure to remove car with id " + this.get(index - 1).getCarID());
            System.out.print("Please choose Y/N: ");
            String choice = sc.nextLine();
            if (choice.equalsIgnoreCase("Y")) {
                this.remove(index - 1);
                System.out.println("Car has been removed successfully");
                return true;
            } else {
                System.out.println("The car with id " + this.get(index - 1).getCarID() + " has been retained");
                return true;
            }
        }
        return false;
    }

    //hàm dùng để update một car thông qua id
    //cách xử lý: dùng hàm search id để tìm car cần update. Rồi tạo 1 menu update cho user
    //người dev: Nguyễn Thanh Tùng
    public boolean updateCar() {
        Menu mnu = new Menu("Please choose the characteristics of car that you want to update");
        mnu.addNewOption("1. Update brand");
        mnu.addNewOption("2. Update color");
        mnu.addNewOption("3. Update frameID");
        mnu.addNewOption("4. Update engineID");
        mnu.addNewOption("5. Update all");
        mnu.addNewOption("6. Exit");
        int choice;
        String exitPoint = "Y";
        String updatedID = MyUtils.getID("Input car ID to update: ", "The carID must not be null. Try again !");
        int posCar = searchID(updatedID);
        int pos;
        if (posCar == -1) {
            System.out.println("Not found !");
            return false;
        } else {
            do {
                Car x = this.get(posCar);
                System.out.println("Here is the Car before updating");
                System.out.println(x);
                mnu.printMenu();
                choice = mnu.int_getChoice();
                switch (choice) {
                    case 1:
                        Brand brand = mnu.ref_getChoice(brandList);
                        x.setBrand(brand);
                        System.out.println("Car has been updated successfully !");
                        System.out.println("Do you want to continue updating the car with ID " + x.getCarID());
                        System.out.print("Choose(Y/N): ");
                        exitPoint = sc.nextLine().toUpperCase();
                        if (!exitPoint.equals("Y")) {
                            exitPoint = "N";
                        }
                        break;
                    case 2:
                        color = MyUtils.getString("Input color: ", "The color must not be null. Try again !");
                        x.setColor(color);
                        System.out.println("Car has been updated successfully !");
                        System.out.println("Do you want to continue updating the car with ID " + x.getCarID());
                        System.out.print("Choose(Y/N): ");
                        exitPoint = sc.nextLine().toUpperCase();
                        if (!exitPoint.equals("Y")) {
                            exitPoint = "N";
                        }
                        break;
                    case 3:
                        do {
                            frameID = MyUtils.getID("Input frame ID: ", "The frame ID must be in F00000 format and not be duplicated. Try again !", "F[0-9][0-9][0-9][0-9][0-9]");
                            pos = searchFrame(frameID);
                            if (pos >= 0 && !frameID.equals(x.getFrameID())) {
                                System.out.println("This frame ID is existed. Try another one!");
                            }
                        } while (pos != -1 && !frameID.equals(x.getFrameID()));
                        x.setFrameID(frameID);
                        System.out.println("Car has been updated successfully !");
                        System.out.println("Do you want to continue updating the car with ID " + x.getCarID());
                        System.out.print("Choose(Y/N): ");
                        exitPoint = sc.nextLine().toUpperCase();
                        if (!exitPoint.equals("Y")) {
                            exitPoint = "N";
                        }
                        break;
                    case 4:
                        do {
                            engineID = MyUtils.getID("Input engine ID: ", "The engine ID must be in E00000 format and not be duplicated. Try again !", "E[0-9][0-9][0-9][0-9][0-9]");
                            pos = searchEngine(engineID);
                            if (pos >= 0 && !engineID.equals(x.getEngineID())) {
                                System.out.println("This engine ID is existed. Try another one!");
                            }
                        } while (pos != -1 && !engineID.equals(x.getEngineID()));
                        x.setEngineID(engineID);
                        System.out.println("Car has been updated successfully !");
                        System.out.println("Do you want to continue updating the car with ID " + x.getCarID());
                        System.out.print("Choose(Y/N): ");
                        exitPoint = sc.nextLine().toUpperCase();
                        if (!exitPoint.equals("Y")) {
                            exitPoint = "N";
                        }
                        break;
                    case 5:
                        brand = mnu.ref_getChoice(brandList);
                        color = MyUtils.getString("Input color: ", "The color must not be null. Try again !");
                        do {
                            frameID = MyUtils.getID("Input frame ID: ", "The frame ID must be in F00000 format and not be duplicated. Try again !", "F[0-9][0-9][0-9][0-9][0-9]");
                            pos = searchFrame(frameID);
                            if (pos >= 0 && !frameID.equals(x.getFrameID())) {
                                System.out.println("This frame ID is existed. Try another one!");
                            }
                        } while (pos != -1 && !frameID.equals(x.getFrameID()));
                        do {
                            engineID = MyUtils.getID("Input engine ID: ", "The engine ID must be in E00000 format and not be duplicated. Try again !", "E[0-9][0-9][0-9][0-9][0-9]");
                            pos = searchEngine(engineID);
                            if (pos >= 0 && !engineID.equals(x.getEngineID())) {
                                System.out.println("This engine ID is existed. Try another one!");
                            }
                        } while (pos != -1 && !engineID.equals(x.getEngineID()));
                        x.setBrand(brand);
                        x.setColor(color);
                        x.setEngineID(engineID);
                        x.setFrameID(frameID);
                        System.out.println("Car has been updated successfully !");
                        System.out.println("Do you want to continue updating the car with ID " + x.getCarID());
                        System.out.print("Choose(Y/N): ");
                        exitPoint = sc.nextLine().toUpperCase();
                        if (!exitPoint.equals("Y")) {
                            exitPoint = "N";
                        }
                        break;
                }
            } while (choice >= 1 && choice <= 5 && exitPoint.equals("Y"));
            return true;
        }
    }

    //hàm dùng để in danh sách các car đã sắp xếp bằng brand name
    //cách xử lý: dùng Collections và comparable đã thêm ở lớp car
    //người dev: Lê Trần Quang Anh
    public void listCars() {
        Collections.sort(this);
        for (Car car : this) {
            System.out.println(car.screenString());
        }
    }

    //hàm để hiển thị toàn bộ car
    //cách xử lí: dùng String format để tạo format như ý muốn
    //người dev: Lê Trần Quang Anh
    public void listFullCars() {
        String format = "| %-5s | %-6s | %-8s | %-6s | %-7s | %-8s |%n";
        System.out.println("Car list");
        System.out.format("+-------+--------+----------+--------+---------+----------+%n");
        System.out.format("| Index | Car ID | Brand ID | Color  | FrameID | EngineID |%n");
        System.out.format("+-------+--------+----------+--------+---------+----------+%n");

        for (Car car : this) {
            String index = String.format("%d", this.indexOf(car) + 1);
            carID = car.getCarID();
            String brandID = car.getBrand().getBrandID();
            color = car.getColor();
            frameID = car.getFrameID();
            engineID = car.getEngineID();
            System.out.format(format, index, carID, brandID, color, frameID, engineID);
        }

        System.out.format("+-------+--------+----------+--------+--------+-----------+%n");
        System.out.println("There are " + this.size() + " car(s) in the list.");
    }
}
