package com.sid.tacinventory;

import java.util.Date;

public class Request {
  //  public String cNumber;
    public String itemName;
    public String pName;
    public String rollNumber;
    public String reqEmail;
    public String purpose;
    public String itemid;
    public String reqid;
    public String branch;
    public String Class;
    public String pNumber;
    public Integer status;//0  6for declined by admin 7 for declined by club for pending 1-4 for clubs 11for confirmed by club 10for cancelled by club 5 for confirmed by tac 12 for cancelled by inventory manager 13 for issued
  //  public String declineMessage;
    public Integer quantity;
    public String date;
    public Request()
    {

    }

    public Request(String itemName, String pName, String rollNumber, String reqEmail, String purpose, String itemid, String reqid, Integer status, Integer quantity, String date) {
        this.itemName = itemName;
        this.pName = pName;
        this.rollNumber = rollNumber;
        this.reqEmail = reqEmail;
        this.purpose = purpose;
        this.itemid = itemid;
        this.reqid = reqid;
        this.status = status;
        this.quantity = quantity;
        this.date = date;
    }
}
