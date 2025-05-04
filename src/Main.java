import java.io.File;
import java.util.Scanner;

public class Main extends AllDetails{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean exitProgram = false;

        File AdminDetails = new File("D:/progamming/OOP/Project/SuperShop/Files/AdminDetails.txt");
        File ProductDetails = new File("D:/progamming/OOP/Project/SuperShop/Files/ProductDetails.txt");
        File TempFile = new File("D:/progamming/OOP/Project/SuperShop/Files/Temp.txt");
        try {
            AdminDetails.createNewFile();
            ProductDetails.createNewFile();
            TempFile.createNewFile();
        } catch (Exception e) {
            System.out.println(e);
        }

        while (!exitProgram) {
            Main obj = new Main();

            System.out.println("\n-----HELLO SUPERSHOP-----");
            System.out.println("Press 'A' - Admin Login");
            System.out.println("Press '1' - Shopping");
            System.out.println("Press '2' - View Product List");
            System.out.println("Press '0' - Exit Program");
            System.out.print("-");
            obj.choice = sc.next();

            switch (obj.choice) {
                case "A":
                case "a":
                    AdminPanel admin = new AdminPanel();
                    admin.AdminLogin();
                    break;
                case "1":
                    AllDetails customer = new AllDetails();
                    customer.BuyProduct();
                    break;
                case "2":
                    AllDetails productList = new AllDetails();
                    productList.ViewProduct();
                    break;
                case "0":
                    exitProgram = true;
                    System.out.println("Thank you for using SuperShop Management System!");
                    break;
                default:
                    System.out.println("-----Invalid Choice!-----");
                    break;
            }
        }
    }
}