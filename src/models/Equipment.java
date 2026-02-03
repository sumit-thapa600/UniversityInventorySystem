package models;

import java.util.Objects;

public class Equipment extends InventoryItem {

    private String assetId;
    private String brand;
    private int warrantyMonths;
    private String category;

    public Equipment(String assetId, String name, String brand,
                     boolean isAvailable, String category, int warrantyMonths) {
        super(assetId, name, isAvailable); // id = assetId
        this.assetId = assetId;
        this.brand = brand;
        this.category = category;
        this.warrantyMonths = warrantyMonths;
    }

    @Override
    public String getItemType() {
        return "Equipment";
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
        setId(assetId); // keep base id in sync
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getWarrantyMonths() {
        return warrantyMonths;
    }

    public void setWarrantyMonths(int warrantyMonths) {
        this.warrantyMonths = warrantyMonths;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // equals by assetId
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Equipment)) return false;
        Equipment that = (Equipment) o;
        return Objects.equals(assetId, that.assetId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assetId);
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "assetId='" + assetId + '\'' +
                ", name='" + getName() + '\'' +
                ", brand='" + brand + '\'' +
                ", category='" + category + '\'' +
                ", warrantyMonths=" + warrantyMonths +
                ", isAvailable=" + isAvailable() +
                '}';
    }
}