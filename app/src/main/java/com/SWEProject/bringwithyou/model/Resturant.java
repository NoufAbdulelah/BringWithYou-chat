package com.SWEProject.bringwithyou.model;

public class Resturant {

    private String BuildingName;
    private String Type;
    private Integer ShopID;
    private Integer Building;
    private String Key;
    private String Image;
    private String Menu;

    public Resturant(){
        BuildingName = "";
        Type = "";
        ShopID = 0;
        Building = 0;
        Key = "";
        Image = "";
        Menu = "";
    }
    public Resturant(String buildingName, String type, Integer shopID, Integer building, String key, String image,String menu) {
        BuildingName = buildingName;
        Type = type;
        ShopID = shopID;
        Building = building;
        Key = key;
        Image = image;
        Menu = menu;
    }


    public String getBuildingName() {
        return BuildingName;
    }

    public void setBuildingName(String buildingName) {
        BuildingName = buildingName;
    }

    public String getType() {
        return Type;
    }


    public void setType(String type) {
        Type = type;
    }

    public Integer getShopID() {
        return ShopID;
    }

    public void setShopID(Integer shopID) {
        ShopID = shopID;
    }

    public Integer getBuilding() {
        return Building;
    }

    public void setBuilding(Integer building) {
        Building = building;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) { Image = image;
    }

    public void setMenu (String menu){
        Menu = menu;
    }

    public String getMenu (){
        return Menu;
    }

}
