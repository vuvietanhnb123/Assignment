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

    public BrandList() {
    }

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
            for (Brand i : this) {
                pw.println(i);
            }
            pw.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

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
    public Brand getUserChoice() {
        Menu mnu = new Menu();
        return (Brand) mnu.ref_getChoice(this);
    }

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
        System.out.println("Brand has been added successfully");
    }

    public void updateBrand() {
        brandID = MyUtils.getID("Input brand ID: ", "The brandID must not be null. Try again !");
        int pos = searchID(brandID);
        if (pos == -1) {
            System.out.println("Not found !");
        } else {
            Brand x = this.get(pos);
            System.out.println("Here is the Brand before updating");
            x.toString();
            brandName = MyUtils.getString("Input brand name: ", "The brand name must not be null. Try again !");
            soundBrand = MyUtils.getString("Input sound brand: ", "The sound brand must not be null. Try again !");
            price = MyUtils.getADouble("Input price: ", "The price must be a number and greater than 0. Try again !", 0);
            x.setBrandName(brandName);
            x.setSoundBrand(soundBrand);
            x.setPrice(price);
            System.out.println("Brand has been updated successfully !");
        }
    }

    public void listBrands() {
        for (int i = 0; i < this.size(); i++) {
            System.out.println(this.get(i));
        }
    }
}
