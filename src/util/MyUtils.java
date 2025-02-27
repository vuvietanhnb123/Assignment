package util;

//class chứa các hàm dùng cho việc nhập xuất dữ liệu và sinh random các thuộc tính
//người dev: Vũ Việt Anh
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.*;

public class MyUtils {

    private static Scanner sc = new Scanner(System.in);

    //hàm nhập vào số nguyên đích thực
    //mọi sự nhập không đúng đều được cảnh báo, ví dụ nhập chuỗi thay vì nhập số
    //chống trôi lệnh, tức là ko để lại rác Enter hay kí tự nào đó
    //trong buffer cho thằng sau hốt 
    //có thể kiểm tra số nguyên trong 1 range/khoảng cho trước
    //có câu thông báo động, tùy ngữ cảnh 
    public static int getAnInteger(String inputMsg, String errorMsg) {
        int n;
        while (true) {
            try {
                System.out.print(inputMsg);
                n = Integer.parseInt(sc.nextLine());
                return n;
            } catch (Exception e) {
                System.out.println(errorMsg);
            }
        }
    }

    public static int getAnInteger(String inputMsg, String errorMsg, int lowerBound, int upperBound) {
        int n, tmp;
        //nếu đầu vào lower > upper thì đổi chỗ
        if (lowerBound > upperBound) {
            tmp = lowerBound;
            lowerBound = upperBound;
            upperBound = tmp;
        }
        while (true) {
            try {
                System.out.print(inputMsg);
                n = Integer.parseInt(sc.nextLine());
                if (n < lowerBound || n > upperBound) {
                    throw new Exception();
                }
                return n;
            } catch (Exception e) {
                System.out.println(errorMsg);
            }
        }
    }

    //nhập vào 1 số thực, chặn hết các trường hợp cà chớn 
    public static double getADouble(String inputMsg, String errorMsg) {
        double n;
        while (true) {
            try {
                System.out.print(inputMsg);
                n = Double.parseDouble(sc.nextLine());
                return n;
            } catch (Exception e) {
                System.out.println(errorMsg);
            }
        }
    }

    public static double getADouble(String inputMsg, String errorMsg, double lowerBound, double upperBound) {
        double n, tmp;
        //nếu đầu vào lower > upper thì đổi chỗ
        if (lowerBound > upperBound) {
            tmp = lowerBound;
            lowerBound = upperBound;
            upperBound = tmp;
        }
        while (true) {
            try {
                System.out.print(inputMsg);
                n = Double.parseDouble(sc.nextLine());
                if (n < lowerBound || n > upperBound) {
                    throw new Exception();
                }
                return n;
            } catch (Exception e) {
                System.out.println(errorMsg);
            }
        }
    }
    
    public static double getADouble(String inputMsg, String errorMsg, double lowerBound) {
        double n;
        while (true) {
            try {
                System.out.print(inputMsg);
                n = Double.parseDouble(sc.nextLine());
                if (n < lowerBound) {
                    throw new Exception();
                }
                return n;
            } catch (Exception e) {
                System.out.println(errorMsg);
            }
        }
    }

    //nhập vào một chuỗi kí tự, theo định dạng đc đưa vào
    //định dạng xài Regular Expression
    public static String getID(String inputMsg, String errorMsg, String format) {
        String id;
        boolean match;
        while (true) {
            System.out.print(inputMsg);
            id = sc.nextLine().trim().toUpperCase();
            match = id.matches(format);
            if (id.length() == 0 || id.isEmpty() || match == false) {
                System.out.println(errorMsg);
            } else {
                return id;
            }
        }
    }
    
    public static String getID(String inputMsg, String errorMsg) {
        String id;
        while (true) {
            System.out.print(inputMsg);
            id = sc.nextLine().trim().toUpperCase();
            if (id.length() == 0 || id.isEmpty()) {
                System.out.println(errorMsg);
            } else {
                return id;
            }
        }
    }
    
    //nhập vào một chuỗi kí tự, khác rỗng
    public static String getString(String inputMsg, String errorMsg) {
        String str;
        while (true) {
            System.out.print(inputMsg);
            str = sc.nextLine().trim();
            if (str.length() == 0 || str.isEmpty()) {
                System.out.println(errorMsg);
            } else {
                return str;
            }
        }
    }
    
    //hàm làm tròn
    public static double getRound(double k, String pattern) {
        DecimalFormat df = new DecimalFormat(pattern);
        String s = df.format(k);
        double result = Double.parseDouble(s);
        return result;
    }
    
    //hàm sinh ngẫu nhiên 1 số nguyên
    public static int getRandomInt() {
        Random rand = new Random();
        return rand.nextInt();
    }

    //hàm sinh ngẫu nhiên 1 số thực trong range
    public static double getRandomDoubleInRange(double min, double max) {
        if (min >= max) {
            throw new IllegalArgumentException("min must be less than max");
        }

        Random rand = new Random();
        return min + (max - min) * rand.nextDouble();
    }
    
    //hàm sinh ngẫu nhiên 1 số nguyên trong range
    public static int getRandomIntInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("min must be less than max");
        }

        Random rand = new Random();
        // Use the nextInt method to generate a random integer within the range [min, max)
        return rand.nextInt(max - min + 1) + min;
    }
    
    //hàm sinh ngẫu nhiên 1 brandID
    //dùng StringBuilder
    //tạo 1 mảng chứa brandID để chống việc sinh trùng
    public static String genRandomBrandID(ArrayList<String> usedIDs) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder brandID = new StringBuilder();

        do {
            int x = getRandomIntInRange(0, 9); // Số từ 0-9
            char y = alphabet.charAt(getRandomIntInRange(0, alphabet.length() - 1)); // Một chữ cái
            char z = alphabet.charAt(getRandomIntInRange(0, alphabet.length() - 1)); // Một chữ cái
            int a = getRandomIntInRange(0, 9); 
            int b = getRandomIntInRange(0, 9);

            brandID.setLength(0); // Xóa bỏ nội dung cũ
            brandID.append("B"); // Chữ "B" đứng đầu
            brandID.append(x);
            brandID.append("-");
            brandID.append(y);
            brandID.append(z);
            brandID.append(a);
            brandID.append(b);
        } while (usedIDs.contains(brandID.toString()));

        return brandID.toString();
    }
    
    //hàm sinh ngẫu nhiên brand name
    public static String genRandomBrandName() {
        String[] bn = {"BMW X5", "BMW M3", "BMW 7 Series", "BMW i8", "BMW Z4", "BMW 3 Series", "BMW X3", "BMW 5 Series", "BMW X7", "BMW X1", "BMW X6", "BMW M4", "BMW 8 Series", "BMW i3", "BMW 1 Series", "BMW X2", "BMW M5", "BMW 6 Series", "BMW M2", "BMW X4"};
        return bn[getRandomIntInRange(0, bn.length - 1)];      
    }
    
    //hàm sinh ngẫu nhiên sound brand
    public static String genRandomSoundBrand() {
        String[] sb = {"SoundXperience", "SonicWave", "AudioZen", "HarmonicBeats", "SoundSculpt", "AcousticPulse", "SonicHarbor", "MeloGroove", "HarmonyWave", "EchoVibes", "SoniBlend", "AcousticPulse", "ResoTune", "SonicSphere", "VibeWave", "HarmonixRhythm", "PulseFusion", "EchoDynamics", "SoundWavesX", "AcousticMomentum"};
        return sb[getRandomIntInRange(0, sb.length - 1)];
    }
    
    //hàm sinh ngẫu nhiên color
    public static String genRandomColor() {
        String[] colors = {"red", "green", "blue", "yellow", "black", "white", "brown", "cyan",
            "magenta", "pink", "gray", "orange", "purple", "violet", "tomato"};
        return colors[getRandomIntInRange(0, colors.length - 1)];
    }
    
    //hàm sinh ngẫu nhiên frameID
    //tương tự sinh brandID
    public static String genRandomFrameID(ArrayList<String> usedIDs) {   
        StringBuilder frameID = new StringBuilder();

        do {
            int num1 = getRandomIntInRange(0, 9); // Số từ 0-9
            int num2 = getRandomIntInRange(0, 9); 
            int num3 = getRandomIntInRange(0, 9);
            int num4 = getRandomIntInRange(0, 9);
            int num5 = getRandomIntInRange(0, 9);

            frameID.setLength(0); // Xóa bỏ nội dung cũ
            frameID.append("F"); // Chữ "F" đứng đầu
            frameID.append(num1);
            frameID.append(num2);
            frameID.append(num3); 
            frameID.append(num4);
            frameID.append(num5);
        } while (usedIDs.contains(frameID.toString()));
        return frameID.toString();
    }
    
    //hàm sinh ngẫu nhiên carID
    //tương tự brandID
    public static String genRandomCarID(ArrayList<String> usedIDs) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder carID = new StringBuilder();

        do {
            int x = getRandomIntInRange(0, 9); // Số từ 0-9
            char y = alphabet.charAt(getRandomIntInRange(0, alphabet.length() - 1)); // Một chữ cái
            char z = alphabet.charAt(getRandomIntInRange(0, alphabet.length() - 1)); // Một chữ cái
            int a = getRandomIntInRange(0, 9); 
            int b = getRandomIntInRange(0, 9);

            carID.setLength(0); // Xóa bỏ nội dung cũ
            carID.append("C"); // Chữ "C" đứng đầu
            carID.append(x);
            carID.append(y);
            carID.append(z);
            carID.append(a);
            carID.append(b);
        } while (usedIDs.contains(carID.toString()));

        return carID.toString();
    }
}
