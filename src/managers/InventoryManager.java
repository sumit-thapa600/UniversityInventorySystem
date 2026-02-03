package managers;

import exceptions.AssignmentLimitExceededException;
import exceptions.EquipmentNotAvailableException;
import models.Equipment;
import models.StaffMember;

public class InventoryManager {

    public void assignEquipment(StaffMember staff, Equipment equipment)
            throws EquipmentNotAvailableException, AssignmentLimitExceededException {

        if (!equipment.isAvailable()) {
            throw new EquipmentNotAvailableException("Equipment is not available: " + equipment.getAssetId());
        }

        if (staff.getAssignedEquipmentCount() >= 5) {
            throw new AssignmentLimitExceededException("Staff member has reached assignment limit.");
        }

        boolean added = staff.addAssignedEquipment(equipment);
        if (added) {
            equipment.setAvailable(false);
        }
    }

    public void returnEquipment(StaffMember staff, String assetId) {
        boolean removed = staff.removeAssignedEquipment(assetId);
        if (removed) {
            // In a real system we would look up the equipment by assetId.
            // Here we assume the caller will also update the equipment object.
        }
    }

    public double calculateMaintenanceFee(Equipment equipment, int daysOverdue) {
        double baseRate;

        switch (equipment.getCategory().toLowerCase()) {
            case "electronics":
                baseRate = 5.0;
                break;
            case "lab":
                baseRate = 7.5;
                break;
            case "furniture":
                baseRate = 3.0;
                break;
            default:
                baseRate = 2.0;
        }

        if (daysOverdue <= 0) {
            return 0.0;
        }

        return baseRate * daysOverdue;
    }

    public Equipment searchEquipment(Equipment[] equipmentArray, String name) {
        for (Equipment e : equipmentArray) {
            if (e != null && e.getName().equalsIgnoreCase(name)) {
                return e;
            }
        }
        return null;
    }

    public Equipment[] searchEquipment(Equipment[] equipmentArray, String category, boolean availableOnly) {
        Equipment[] results = new Equipment[equipmentArray.length];
        int index = 0;

        for (Equipment e : equipmentArray) {
            if (e != null &&
                    e.getCategory().equalsIgnoreCase(category) &&
                    (!availableOnly || e.isAvailable())) {
                results[index++] = e;
            }
        }
        return results;
    }

    public Equipment[] searchEquipment(Equipment[] equipmentArray, int minWarranty, int maxWarranty) {
        Equipment[] results = new Equipment[equipmentArray.length];
        int index = 0;

        for (Equipment e : equipmentArray) {
            if (e != null &&
                    e.getWarrantyMonths() >= minWarranty &&
                    e.getWarrantyMonths() <= maxWarranty) {
                results[index++] = e;
            }
        }
        return results;
    }

    public boolean validateAssignment(StaffMember staff, Equipment equipment) {
        if (staff == null || equipment == null) {
            return false;
        }

        if (!equipment.isAvailable()) {
            return false;
        } else {
            if (staff.getAssignedEquipmentCount() >= 5) {
                return false;
            } else {
                return true;
            }
        }
    }
}