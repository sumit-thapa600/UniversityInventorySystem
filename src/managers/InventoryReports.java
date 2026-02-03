package managers;

import models.Equipment;
import models.InventoryItem;
import models.StaffMember;

public class InventoryReports {

    public void generateInventoryReport(InventoryItem[] items) {
        System.out.println("=== Inventory Report ===");
        for (int i = 0; i < items.length; i++) {
            InventoryItem item = items[i];
            if (item != null) {
                System.out.println(item);
            }
        }
    }

    public void findExpiredWarranties(Equipment[] equipmentArray) {
        System.out.println("=== Expired Warranties ===");
        int i = 0;
        while (i < equipmentArray.length) {
            Equipment e = equipmentArray[i];
            if (e != null && e.getWarrantyMonths() == 0) {
                System.out.println(e);
            }
            i++;
        }
    }

    public void displayAssignmentsByDepartment(StaffMember[] staffArray) {
        System.out.println("=== Assignments by Department ===");
        for (StaffMember staff : staffArray) {
            if (staff != null) {
                System.out.println("Department: " + staff.getDepartment() +
                        " | Staff: " + staff.getName() +
                        " | Assigned Equipment: " + staff.getAssignedEquipmentCount());
            }
        }
    }

    public void calculateUtilisationRate(StaffMember[] staffArray, Equipment[] equipmentArray) {
        System.out.println("=== Utilisation Rate ===");
        int totalEquipment = 0;
        int totalAssigned = 0;

        for (Equipment e : equipmentArray) {
            if (e != null) {
                totalEquipment++;
                for (StaffMember staff : staffArray) {
                    if (staff != null) {
                        for (int i = 0; i < staff.getAssignedEquipmentCount(); i++) {
                            Equipment assigned = staff.getAssignedEquipment()[i];
                            if (assigned != null && assigned.equals(e)) {
                                totalAssigned++;
                                break;
                            }
                        }
                    }
                }
            }
        }

        if (totalEquipment == 0) {
            System.out.println("No equipment to calculate utilisation.");
        } else {
            double rate = (double) totalAssigned / totalEquipment * 100;
            System.out.printf("Utilisation Rate: %.2f%%%n", rate);
        }
    }

    public void generateMaintenanceSchedule(Equipment[] equipmentArray) {
        System.out.println("=== Maintenance Schedule ===");
        int i = 0;
        do {
            if (i >= equipmentArray.length) break;
            Equipment e = equipmentArray[i];
            if (e != null) {
                System.out.println("Schedule maintenance for: " + e.getName() +
                        " (Category: " + e.getCategory() + ")");
            }
            i++;
        } while (i < equipmentArray.length);
    }
}