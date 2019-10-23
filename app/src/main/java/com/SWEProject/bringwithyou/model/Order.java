package com.SWEProject.bringwithyou.model;

public class Order {

    private String date;
    private String description;
    private String ShopName;
    private Integer ShopId;
    private long price;
    private String status;
    private String userId;
    private String buyerId;

    public Order (String date,String description,String ShopName,Integer ShopId,long price,String status,String userId,String buyerId){
        this.date = date;
        this.description = description;
        this.ShopName = ShopName;
        this.ShopId = ShopId;
        this.price = price;
        this.status = status;
        this.userId =  userId;
        this.buyerId = buyerId;
    }

    public Order(){
        date = "";
      description="";
      ShopName="";
      ShopId=0;
      price=0;
      status="";
      buyerId = "";

    }


    public String getDate (){
      return date ;
    }

    public String getDescription (){
        return description ;
    }

    public String getShopName(){
        return ShopName ;
    }
    public Integer getShopId (){
        return ShopId ;
    }

    public String getStatus (){
        return status ;
    }

    public long getPrice (){
        return price ;
    }
    public String getUserId(){
        return userId ;
    }
    public String getBuyerId(){ return buyerId;}


    public void setDate (String date){
        this.date  = date ;
    }

    public void setDescription (String description){
        this.description  = description ;
    }
    public void setShopName (String ShopName){
        this.ShopName  = ShopName ;
    }
    public void setShopId (Integer ShopId){
        this.ShopId  = ShopId ;
    }

    public void setStatus (String status){
        this.status  = status ;
    }
    public void setPrice(long price){
        this.price  = price ;
    }
    public void setUserId(String userId) {this.userId = userId;  }
    public void setBuyerId(String buyerId) { this.buyerId = buyerId;}

}
