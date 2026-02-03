import exceptions.AssignmentLimitExceededException;
import exceptions.EquipmentNotAvailableException;
import managers.InventoryManager;
import managers.InventoryReports;
import models.Equipment;
import models.InventoryItem;
import models.StaffMember;

import java.util.Scanner;

public class UniversityInventorySystem {

    private static final int MAX_EQUIPMENT = 50;
    private static final int MAX_STAFF = 50;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        InventoryManager inventoryManager = new InventoryManager();
        InventoryReports reports = new InventoryReports();

        Equipment[] equipmentArray = new Equipment[MAX_EQUIPMENT];
        StaffMember[] staffArray = new StaffMember[MAX_STAFF];
        int equipmentCount = 0;
        int staffCount = 0;

        boolean running = true;

        while (running) {
            System.out.println("\n=== University Inventory Management System ===");
            System.out.println("1. Add new equipment");
            System.out.println("2. Register new staff member");
            System.out.println("3. Assign equipment to staff");
            System.out.println("4. Return equipment");
            System.out.println("5. Search inventory");
            System.out.println("6. Generate reports");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input. Enter a number.");
                continue;
            }

            switch (choice) {

                case 1:
                    if (equipmentCount >= MAX_EQUIPMENT) {
                        System.out.println("Equipment storage full.");
                        break;
                    }

                    System.out.print("Enter assetId: ");
                    String assetId = scanner.nextLine();
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter brand: ");
                    String brand = scanner.nextLine();
                    System.out.print("Enter category: ");
                    String category = scanner.nextLine();
                    System.out.print("Enter warranty months: ");
                    int warranty = Integer.parseInt(scanner.nextLine());

                    Equipment eq = new Equipment(assetId, name, brand, true, category, warranty);
                    equipmentArray[equipmentCount++] = eq;

                    System.out.println("Equipment added: " + eq);
                    break;

                case 2:
                    if (staffCount >= MAX_STAFF) {
                        System.out.println("Staff storage full.");
                        break;
                    }

                    System.out.print("Enter staff ID: ");
                    int staffId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter staff name: ");
                    String staffName = scanner.nextLine();
                    System.out.print("Enter staff email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter department: ");
                    String department = scanner.nextLine();

                    StaffMember staff = new StaffMember(staffId, staffName, email, department);
                    staffArray[staffCount++] = staff;

                    System.out.println("Staff registered: " + staff);
                    break;

                case 3:
                    System.out.print("Enter staff ID: ");
                    int assignStaffId = Integer.parseInt(scanner.nextLine());
                    StaffMember assignStaff = findStaffById(staffArray, assignStaffId);

                    if (assignStaff == null) {
                        System.out.println("Staff not found.");
                        break;
                    }

                    System.out.print("Enter equipment assetId: ");
                    String assignAssetId = scanner.nextLine();
                    Equipment assignEq = findEquipmentByAssetId(equipmentArray, assignAssetId);

                    if (assignEq == null) {
                        System.out.println("Equipment not found.");
                        break;
                    }

                    try {
                        inventoryManager.assignEquipment(assignStaff, assignEq);
                        System.out.println("Equipment assigned successfully.");
                    } catch (EquipmentNotAvailableException | AssignmentLimitExceededException ex) {
                        System.out.println("Error: " + ex.getMessage());
                    }
                    break;

                case 4:
                    System.out.print("Enter staff ID: ");
                    int returnStaffId = Integer.parseInt(scanner.nextLine());
                    StaffMember returnStaff = findStaffById(staffArray, returnStaffId);

                    if (returnStaff == null) {
                        System.out.println("Staff not found.");
                        break;
                    }

                    System.out.print("Enter equipment assetId to return: ");
                    String returnAssetId = scanner.nextLine();
                    Equipment returnEq = findEquipmentByAssetId(equipmentArray, returnAssetId);

                    if (returnEq == null) {
                        System.out.println("Equipment not found.");
                        break;
                    }

                    inventoryManager.returnEquipment(returnStaff, returnAssetId);
                    returnEq.setAvailable(true);

                    System.out.println("Equipment returned successfully.");
                    break;

                case 5:
                    System.out.println("Search options:");
                    System.out.println("1. By name");
                    System.out.println("2. By category and availability");
                    System.out.println("3. By warranty range");
                    System.out.print("Choose: ");
                    int searchChoice = Integer.parseInt(scanner.nextLine());

                    switch (searchChoice) {
                        case 1:
                            System.out.print("Enter name: ");
                            String searchName = scanner.nextLine();
                            Equipment foundByName = inventoryManager.searchEquipment(equipmentArray, searchName);
                            System.out.println(foundByName != null ? foundByName : "No equipment found.");
                            break;

                        case 2:
                            System.out.print("Enter category: ");
                            String searchCategory = scanner.nextLine();
                            System.out.print("Available only? (true/false): ");
                            boolean availableOnly = Boolean.parseBoolean(scanner.nextLine());
                            Equipment[] foundByCategory = inventoryManager.searchEquipment(
                                    equipmentArray, searchCategory, availableOnly);
                            for (Equipment e : foundByCategory) {
                                if (e != null) System.out.println(e);
                            }
                            break;

                        case 3:
                            System.out.print("Enter min warranty: ");
                            int minW = Integer.parseInt(scanner.nextLine());
                            System.out.print("Enter max warranty: ");
                            int maxW = Integer.parseInt(scanner.nextLine());
                            Equipment[] foundByWarranty = inventoryManager.searchEquipment(
                                    equipmentArray, minW, maxW);
                            for (Equipment e : foundByWarranty) {
                                if (e != null) System.out.println(e);
                            }
                            break;

                        default:
                            System.out.println("Invalid search option.");
                    }
                    break;

                case 6:
                    InventoryItem[] allItems = new InventoryItem[equipmentCount];
                    for (int i = 0; i < equipmentCount; i++) {
                        allItems[i] = equipmentArray[i];
                    }

                    reports.generateInventoryReport(allItems);
                    reports.findExpiredWarranties(equipmentArray);
                    reports.displayAssignmentsByDepartment(staffArray);
                    reports.calculateUtilisationRate(staffArray, equipmentArray);
                    reports.generateMaintenanceSchedule(equipmentArray);
                    break;

                case 7:
                    running = false;
                    System.out.println("Exiting system. Goodbye.");
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }

        scanner.close();
    }

    private static StaffMember findStaffById(StaffMember[] staffArray, int staffId) {
        for (StaffMember s : staffArray) {
            if (s != null && s.getStaffId() == staffId) {
                return s;
            }
        }
        return null;
    }

    private static Equipment findEquipmentByAssetId(Equipment[] equipmentArray, String assetId) {
        for (Equipment e : equipmentArray) {
            if (e != null && e.getAssetId().equals(assetId)) {
                return e;
            }
        }
        return null;
    }
}