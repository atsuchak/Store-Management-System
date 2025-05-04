import java.util.Scanner;

public class AdminPanel extends AllDetails{
    Scanner sc = new Scanner(System.in);

    private String AdminUsername, AdminPass;

    public void setAdminUsername(String adminUsername) {
        AdminUsername = adminUsername;
    }
    public String getAdminUsername() {
        return AdminUsername;
    }
    public void setAdminPass(String adminPass) {
        AdminPass = adminPass;
    }
    public String getAdminPass() {
        return AdminPass;
    }

    public void AdminLogin() {
        System.out.println("-----Admin Panel-----");
        System.out.print("Enter Username: ");
        setAdminUsername(sc.nextLine());
        System.out.print("Enter Password: ");
        setAdminPass(sc.nextLine());
        if(getAdminUsername().equals("Admin") && getAdminPass().equals("Admin123")) {
            System.out.println("-----Admin Login Successful-----");
            boolean exitAdmin = false;

            while(!exitAdmin) {
                System.out.println("\n-----Admin Menu-----");
                System.out.println("Press '1' - Add product");
                System.out.println("Press '2' - View product");
                System.out.println("Press '3' - Remove product");
                System.out.println("Press '4' - Clean up zero quantity products");
                System.out.println("Press '0' - Logout");
                System.out.print("-");
                choice = sc.next();

                switch(choice) {
                    case "1":
                        AddProduct();
                        break;
                    case "2":
                        ViewProduct();
                        break;
                    case "3":
                        RemoveProduct();
                        break;
                    case "4":
                        CleanupZeroQuantityProducts();
                        break;
                    case "0":
                        exitAdmin = true;
                        System.out.println("Logging out from Admin Panel...");
                        break;
                    default:
                        System.out.println("-----Invalid Choice!-----");
                        break;
                }
            }
        } else {
            System.out.println("-----Login failed-----");
        }
    }
}