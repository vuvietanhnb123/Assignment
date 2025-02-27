package ui;

import data.Brand;
import data.BrandList;
import java.util.*;
import java.lang.*;
import util.MyUtils;

public class Menu {

    private String menuTitle;
    private ArrayList<String> optionList = new ArrayList();

    //khởi tạo tên của app., tên của menu
    public Menu(String menuTitle) {
        this.menuTitle = menuTitle;
    }

    public Menu() {
        
    }

    //bổ sung một lựa chọn vào danh sách, bắt đầu xây dựng thực đơn/
    //lựa chọn của chương trình
    //người dev: Vũ Việt Anh
    public void addNewOption(String newOption) {
        optionList.add(newOption);
    }

    //in ra danh sách các lựa chọn để người dùng chọn tính năng cần dùng
    //người dev: Vũ Việt Anh
    public void printMenu() {
        if (optionList.isEmpty()) {
            System.out.println("There is no item in the menu");
            return;
        }
        System.out.println("\n------------------------------------");
        System.out.println(menuTitle);
        for (String x : optionList) {
            System.out.println(x);
        }
    }

    //có menu mới có lựa chọn. Hàm trả về con số người dùng chọn
    //ứng với chức năng mà người dùng muốn app thực thi
    //người dev: Vũ Việt Anh
    public int int_getChoice() {
        int maxOption = optionList.size();
        //lựa chọn lớn nhất là số thứ tự ứng với số mục chọn
        String inputMsg = "Please choose an option [1.." + maxOption + "]: ";
        String errorMsg = "You must choose an option 1.." + maxOption;
        return MyUtils.getAnInteger(inputMsg, errorMsg, 1, maxOption);
    }
    
    //lấy lựa chọn của người dùng nhưng là object trong brandList
    //người dev: Vũ Việt Anh
    public Brand ref_getChoice(ArrayList<Brand> options) {
        int maxOption = options.size();
        int response;
        System.out.println("Brand ID list:");
        do {
            response = int_getChoice(options);
        } while (response < 0 || response > maxOption);
        return options.get(response - 1);
    }

    //người dev: Vũ Việt Anh
    private int int_getChoice(ArrayList<Brand> options) {
        int maxOption = options.size();
        int response;
        for (int i = 0; i < maxOption; i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
        String inputMsg = "Please choose an option [1.." + maxOption + "]: ";
        String errorMsg = "You must choose an option 1.." + maxOption;
        response = MyUtils.getAnInteger(inputMsg, errorMsg, 1, maxOption);
        return response;
    }
}
