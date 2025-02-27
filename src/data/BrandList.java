package data;

import java.io.*;
import java.util.*;
import java.lang.*;
import ui.Menu;
import util.MyUtils;

public class BrandList extends ArrayList<Brand> {

    private String brandID, brandName, soundBrand;
    private double price;
    private Scanner sc = new Scanner(System.in);
    private ArrayList<String> brandIDList = new ArrayList<>();

    public BrandList() {
    }

    // hàm dùng để tạo 1 brand ngẫu nhiên 
    //cách xử lí bằng cách sinh ngẫu nhiên từng đặc tính của 1 brand thông qua MyUltil
    //người dev: Vũ Việt Anh
    public void genRandomBrand() {
        System.out.print("Enter number of brands you want to generate: ");
        int numberOfGenerations = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < numberOfGenerations; i++) {
            brandID = MyUtils.genRandomBrandID(brandIDList);
            brandName = MyUtils.genRandomBrandName();
            soundBrand = MyUtils.genRandomSoundBrand();
            price = MyUtils.getRound(MyUtils.getRandomDoubleInRange(1.0, 10.0), "#.###");
            this.add(new Brand(brandID, brandName, soundBrand, price));
        }
        System.out.println(numberOfGenerations + " random brand(s) has been generated");
    }

    //hàm để load data từ file brands.txt. 
    //Người dev: Vũ Việt Anh
    public boolean loadFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String[] arr;
            String line = br.readLine();
            while ((line != null)) {
                arr = line.split(",");
                brandID = arr[0].trim();
                brandName = arr[1].trim();
                soundBrand = arr[2].split(":")[0].trim();
                price = Double.parseDouble(arr[2].split(":")[1].trim());
                this.add(new Brand(brandID, brandName, soundBrand, price));
                brandIDList.add(brandID);
                line = br.readLine();
            }
            br.close();
            return true;
        } catch (IOException e) {
            System.out.println("File " + filename + " not found !");
        }
        return false;
    }

    //hàm để lưu data từ chương trình vào file brands.txt. 
    //Người dev: Vũ Việt Anh
    public boolean saveToFile(String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (Brand brand : this) {
                String line = brand.getBrandID() + ", " + brand.getBrandName() + ", " + brand.getSoundBrand() + ": " + brand.getPrice();
                pw.println(line);
            }
            pw.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    //hàm để search 1 brand thông qua ID.
    //cách xử lý: duyệt qua list các brand rồi tìm brand trùng với id được nhập
    //người dev: Nguyễn Thanh Tùng
    public int searchID(String bID) {
        if (this.isEmpty()) {
            return -1;
        }
        for (int i = 0; i < this.size(); i++) {
            if (bID.equals(this.get(i).getBrandID())) {
                return i;
            }
        }
        return -1;
    }

    //chuyển list thành 1 menu cho người dùng chọn 1 brand từ đây
    //người dev: Nguyễn Tuấn Anh
    public Brand getUserChoice() {
        Menu mnu = new Menu();
        return (Brand) mnu.ref_getChoice(this);
    }

    //hàm dùng để add 1 brand mới
    //cách xử lý: brandID mới không được trùng, và mỗi đặc tính được nhập thỏa mãn yêu cầu
    //người dev: Nguyễn Tuấn Anh
    public void addBrand() {
        int pos;
        do {
            brandID = MyUtils.getID("Input brand ID: ", "The brandID must not be null. Try again !");
            pos = searchID(brandID);
            if (pos >= 0) {
                System.out.println("This brand ID is existed. Try another one!");
            }
        } while (pos != -1);

        brandName = MyUtils.getString("Input brand name: ", "The brand name must not be null. Try again !");
        soundBrand = MyUtils.getString("Input sound brand: ", "The sound brand must not be null. Try again !");
        price = MyUtils.getADouble("Input price: ", "The price must be a number and greater than 0. Try again !", 0);
        this.add(new Brand(brandID, brandName, soundBrand, price));
        brandIDList.add(brandID);
        System.out.println("Brand has been added successfully");
    }

    //hàm dùng để update một brand thông qua id
    //cách xử lý: dùng hàm search id để tìm brand cần update. Rồi tạo 1 menu update cho user
    //người dev: Nguyễn Thanh Tùng
    public void updateBrand() {
        Menu mnu = new Menu("Please choose the characteristics of brand that you want to update");
        mnu.addNewOption("1. Update brand name");
        mnu.addNewOption("2. Update sound brand");
        mnu.addNewOption("3. Update price");
        mnu.addNewOption("4. Update all");
        mnu.addNewOption("5. Exit");
        int choice;
        String exitPoint = "Y";
        brandID = MyUtils.getID("Input brand ID: ", "The brandID must not be null. Try again !");
        int pos = searchID(brandID);
        if (pos == -1) {
            System.out.println("Not found !");
        } else {
            do {
                Brand x = this.get(pos);
                System.out.println("Here is the Brand before updating");
                System.out.println(x);
                mnu.printMenu();
                choice = mnu.int_getChoice();

                switch (choice) {
                    case 1:
                        brandName = MyUtils.getString("Input brand name: ", "The brand name must not be null. Try again !");
                        x.setBrandName(brandName);
                        System.out.println("Brand has been updated successfully !");
                        System.out.println("Do you want to continue updating the brand with ID " + x.getBrandID());
                        System.out.print("Choose(Y/N): ");
                        exitPoint = sc.nextLine().toUpperCase();
                        if (!exitPoint.equals("Y")) {
                            exitPoint = "N";
                        }
                        break;
                    case 2:
                        soundBrand = MyUtils.getString("Input sound brand: ", "The sound brand must not be null. Try again !");
                        x.setSoundBrand(soundBrand);
                        System.out.println("Brand has been updated successfully !");
                        System.out.println("Do you want to continue updating the brand with ID " + x.getBrandID());
                        System.out.print("Choose(Y/N): ");
                        exitPoint = sc.nextLine().toUpperCase();
                        if (!exitPoint.equals("Y")) {
                            exitPoint = "N";
                        }
                        break;
                    case 3:
                        price = MyUtils.getADouble("Input price: ", "The price must be a number and greater than 0. Try again !", 0);
                        x.setPrice(price);
                        System.out.println("Brand has been updated successfully !");
                        System.out.println("Do you want to continue updating the brand with ID " + x.getBrandID());
                        System.out.print("Choose(Y/N): ");
                        exitPoint = sc.nextLine().toUpperCase();
                        if (!exitPoint.equals("Y")) {
                            exitPoint = "N";
                        }
                        break;
                    case 4:
                        brandName = MyUtils.getString("Input brand name: ", "The brand name must not be null. Try again !");
                        soundBrand = MyUtils.getString("Input sound brand: ", "The sound brand must not be null. Try again !");
                        price = MyUtils.getADouble("Input price: ", "The price must be a number and greater than 0. Try again !", 0);
                        x.setBrandName(brandName);
                        x.setSoundBrand(soundBrand);
                        x.setPrice(price);
                        System.out.println("Brand has been updated successfully !");
                        System.out.println("Do you want to continue updating the brand with ID " + x.getBrandID());
                        System.out.print("Choose(Y/N): ");
                        exitPoint = sc.nextLine().toUpperCase();
                        if (!exitPoint.equals("Y")) {
                            exitPoint = "N";
                        }
                        break;
                }
            } while (choice >= 1 && choice <= 4 && exitPoint.equals("Y"));
        }
    }

//    public void listBrands() {
//        for (int i = 0; i < this.size(); i++) {
//            System.out.println(this.get(i));
//        }
//        System.out.println("There are " + this.size() + " brand(s) in the list.");
//    }
    
    //hàm để hiển thị toàn bộ brand
    //cách xử lí: dùng String format để tạo format như ý muốn
    //người dev: Lê Trần Quang Anh
    public void listBrands() {
        String format = "| %-5s | %-8s | %-29s | %-16s | %-5s |%n";
        System.out.println("Brand list");
        System.out.format("+-------+----------+-------------------------------+------------------+-------+%n");
        System.out.format("| Index | Brand ID | Brand Name                    | Sound Brand      | Price |%n");
        System.out.format("+-------+----------+-------------------------------+------------------+-------+%n");

        for (Brand brand : this) {
            String index = String.format("%d", this.indexOf(brand) + 1);
            brandID = brand.getBrandID();
            brandName = brand.getBrandName();
            soundBrand = brand.getSoundBrand();
            String price = String.format("%.3f", brand.getPrice()); // Làm tròn 3 chữ số thập phân
            System.out.format(format, index, brandID, brandName, soundBrand, price);
        }

        System.out.format("+-------+----------+-------------------------------+------------------+-------+%n");
        System.out.println("There are " + this.size() + " brand(s) in the list.");
    }

}
