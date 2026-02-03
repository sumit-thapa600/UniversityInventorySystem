package models;

public class StaffMember {

    private int staffId;
    private String name;
    private String email;
    private Equipment[] assignedEquipment;
    private int assignedCount;
    private String department;

    private static final int MAX_EQUIPMENT = 5;

    public StaffMember(int staffId, String name, String email, String department) {
        this.staffId = staffId;
        this.name = name;
        this.email = email;
        this.department = department;
        this.assignedEquipment = new Equipment[MAX_EQUIPMENT];
        this.assignedCount = 0;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Equipment[] getAssignedEquipment() {
        return assignedEquipment;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getAssignedEquipmentCount() {
        return assignedCount;
    }

    public boolean addAssignedEquipment(Equipment equipment) {
        if (assignedCount >= MAX_EQUIPMENT) {
            return false;
        }
        assignedEquipment[assignedCount] = equipment;
        assignedCount++;
        return true;
    }

    public boolean removeAssignedEquipment(String assetId) {
        for (int i = 0; i < assignedCount; i++) {
            if (assignedEquipment[i] != null &&
                    assignedEquipment[i].getAssetId().equals(assetId)) {
                assignedEquipment[i] = assignedEquipment[assignedCount - 1];
                assignedEquipment[assignedCount - 1] = null;
                assignedCount--;
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "StaffMember{" +
                "staffId=" + staffId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", department='" + department + '\'' +
                ", assignedEquipmentCount=" + assignedCount +
                '}';
    }
}