import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;

public class AllDetails {
    Scanner sc = new Scanner(System.in);
    private String ProductName, ProductId;
    private int ProductQuantity;
    int ProductPrice;
    int totalAmount, quantity, itemRate = 1;
    String itemSearch;
    String choice;

    public void AddProduct() {
        boolean addMore = true;

        while(addMore) {
            System.out.print("Enter Product ID: ");
            ProductId = sc.next();
            System.out.print("Enter Product Name: ");
            ProductName = sc.next();
            System.out.print("Enter Product Quantity: ");
            ProductQuantity = sc.nextInt();
            System.out.print("Enter Product Price: ");
            ProductPrice = sc.nextInt();

            try {
                FileWriter write = new FileWriter("D:/progamming/OOP/Project/SuperShop/Files/ProductDetails.txt", true);
                write.write(ProductId + "\t" + ProductName + "\t" + ProductQuantity + "\t" + ProductPrice + "\n");
                write.close();
                System.out.println("Product added successfully!");
            } catch (IOException e) {
                System.out.println(e);
            }

            System.out.print("Do you want to add more products? (Y/N): ");
            String response = sc.next();
            addMore = response.equalsIgnoreCase("Y");
        }
    }

    void ViewProduct() {
        try {
            File reader = new File("D:/progamming/OOP/Project/SuperShop/Files/ProductDetails.txt");
            Scanner flSc = new Scanner(reader);

            System.out.println("-------------------------");
            System.out.println("Product ID\t\tProduct Name\t\tQuantity\t\tProduct Price");

            while (flSc.hasNext()) {
                String data = flSc.nextLine();
                String[] parts = data.split("\t");

                if (parts.length == 4) {
                    ProductId = parts[0];
                    ProductName = parts[1];
                    ProductQuantity = Integer.parseInt(parts[2]);
                    ProductPrice = Integer.parseInt(parts[3]);
                    System.out.println(ProductId + "\t\t\t\t" + ProductName + "\t\t\t\t" + ProductQuantity + "\t\t\t\t" + ProductPrice);
                }
            }

            System.out.println("-------------------------");
            flSc.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    private StringBuilder purchaseReceipt = new StringBuilder();


    public void RemoveProduct() {
        boolean continueRemoving = true;

        while(continueRemoving) {
            try {
                ViewProduct();

                System.out.print("Enter Product ID to remove: ");
                String productIdToRemove = sc.next();

                File originalFile = new File("D:/progamming/OOP/Project/SuperShop/Files/ProductDetails.txt");
                File tempFile = new File("D:/progamming/OOP/Project/SuperShop/Files/Temp.txt");

                Scanner reader = new Scanner(originalFile);
                FileWriter writer = new FileWriter(tempFile);

                boolean productFound = false;

                while(reader.hasNextLine()) {
                    String line = reader.nextLine();
                    String[] parts = line.split("\t");

                    if(parts.length == 4) {
                        if(parts[0].equals(productIdToRemove)) {
                            productFound = true;
                            continue;
                        }
                        writer.write(line + "\n");
                    }
                }

                reader.close();
                writer.close();

                if(productFound) {
                    originalFile.delete();
                    tempFile.renameTo(originalFile);
                    System.out.println("Product removed successfully!");
                } else {
                    tempFile.delete(); // Delete temp file as we don't need it
                    System.out.println("Product not found!");
                }

                System.out.print("Do you want to remove another product? (Y/N): ");
                String response = sc.next();
                continueRemoving = response.equalsIgnoreCase("Y");

            } catch(Exception e) {
                System.out.println("Error: " + e);
                continueRemoving = false;
            }
        }
    }

    public void CleanupZeroQuantityProducts() {
        try {
            File originalFile = new File("D:/progamming/OOP/Project/SuperShop/Files/ProductDetails.txt");
            File tempFile = new File("D:/progamming/OOP/Project/SuperShop/Files/Temp.txt");

            Scanner reader = new Scanner(originalFile);
            FileWriter writer = new FileWriter(tempFile);

            boolean removedAny = false;

            while(reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] parts = line.split("\t");

                if(parts.length == 4) {
                    int quantity = Integer.parseInt(parts[2]);

                    if(quantity > 0) {
                        writer.write(line + "\n");
                    } else {
                        removedAny = true;
                        System.out.println("Auto-removed zero-quantity product: " + parts[1] + " (ID: " + parts[0] + ")");
                    }
                }
            }
            reader.close();
            writer.close();

            originalFile.delete();
            tempFile.renameTo(originalFile);

            if(removedAny) {
                System.out.println("Zero-quantity products have been removed.");
            }

        } catch(Exception e) {
            System.out.println("Error during cleanup: " + e);
        }
    }

    public void BuyProduct() {
        boolean continueShopping = true;
        totalAmount = 0;
        purchaseReceipt = new StringBuilder();

        purchaseReceipt.append("-----YOUR PURCHASE RECEIPT-----\n");
        purchaseReceipt.append("Product ID\t\tProduct Name\t\tQuantity\t\tUnit Price\t\tTotal\n");

        while(continueShopping) {
            try {
                File read = new File("D:/progamming/OOP/Project/SuperShop/Files/ProductDetails.txt");
                File temp = new File("D:/progamming/OOP/Project/SuperShop/Files/Temp.txt");

                Scanner reader = new Scanner(read);
                FileWriter writer = new FileWriter(temp);

                System.out.println("-------------------------");
                System.out.println("Product ID\t\tProduct Name\t\tQuantity\t\tProduct Price");

                while (reader.hasNextLine()) {
                    String data = reader.nextLine();
                    String[] parts = data.split("\t");

                    if (parts.length == 4) {
                        String id = parts[0];
                        String name = parts[1];
                        int qty = Integer.parseInt(parts[2]);
                        int price = Integer.parseInt(parts[3]);
                        System.out.println(id + "\t\t\t\t" + name + "\t\t\t\t" + qty + "\t\t\t\t" + price);
                    }
                }
                reader.close();
                reader = new Scanner(read);

                System.out.println("-------------------------");
                System.out.print("Enter Product ID: ");
                itemSearch = sc.next();
                System.out.print("Enter Quantity: ");
                quantity = sc.nextInt();

                boolean productFound = false;
                int currentItemRate = 0;
                String purchasedProductName = "";
                int purchasedProductPrice = 0;

                while (reader.hasNextLine()) {
                    String line = reader.nextLine();
                    String[] parts = line.split("\t");

                    if (parts.length == 4) {
                        String id = parts[0];
                        String name = parts[1];
                        int qty = Integer.parseInt(parts[2]);
                        int price = Integer.parseInt(parts[3]);

                        if (id.equals(itemSearch)) {
                            productFound = true;
                            purchasedProductName = name;
                            purchasedProductPrice = price;

                            if (qty >= quantity) {
                                qty -= quantity;
                                currentItemRate = quantity * price;
                                totalAmount += currentItemRate;

                                purchaseReceipt.append(id + "\t\t\t\t" + name + "\t\t\t\t" + quantity + "\t\t\t\t" +
                                        price + "\t\t\t\t" + currentItemRate + "\n");

                                System.out.println("-----Purchase successful!-----");
                            } else {
                                System.out.println("Insufficient quantity available. Available: " + qty);
                            }
                        }

                        if (qty > 0 || !id.equals(itemSearch)) {
                            writer.write(id + "\t" + name + "\t" + qty + "\t" + price + "\n");
                        } else if (id.equals(itemSearch) && qty == 0) {
                            System.out.println("Product " + name + " is now out of stock and has been removed.");
                        }
                    }
                }
                if (!productFound) {
                    System.out.println("Product not found!");
                } else if (currentItemRate > 0) {
                    System.out.println("-------------------------");
                    System.out.println("Added to cart: " + purchasedProductName);
                    System.out.println("Quantity: " + quantity);
                    System.out.println("Price per unit: " + purchasedProductPrice);
                    System.out.println("Item Total: " + currentItemRate);
                    System.out.println("Total Amount So Far: " + totalAmount);
                }

                reader.close();
                writer.close();

                File original = new File("D:/progamming/OOP/Project/SuperShop/Files/ProductDetails.txt");
                original.delete();
                temp.renameTo(original);

                System.out.println("-------------------------");
                System.out.print("Do you want to continue shopping? (Y/N): ");
                String response = sc.next();
                continueShopping = response.equalsIgnoreCase("Y");

            } catch (Exception e) {
                System.out.println(e);
                continueShopping = false;
            }
        }
        System.out.println("\n-----FINAL BILL-----");
        System.out.println(purchaseReceipt.toString());
        System.out.println("-------------------------");
        System.out.println("Total Amount: " + totalAmount);
        System.out.println("Thank you for shopping with us!");
    }
}






