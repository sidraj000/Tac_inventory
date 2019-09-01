package com.sid.tacinventory;

import java.util.Date;

public class Request {
    public String reqid;
  //  public String cNumber;
    public String itemName;
    public String pName;
    public String rollNumber;
    public String reqEmail;
    public String purpose;
    public Integer status;//0  6for declined by admin 7 for declined by club for pending 1-5 for clubs 11for confirmed by club 10for cancelled by club 12 for cancelled by inventory manager 13 for issued
  //  public String declineMessage;
    public Integer quantity;
    public Date date;
    public Request()
    {

    }

    public Request(String reqid, String itemName, String pName, String rollNumber, String reqEmail, String purpose, Integer status, Integer quantity, Date date) {
        this.reqid = reqid;
        this.itemName = itemName;
        this.pName = pName;
        this.rollNumber = rollNumber;
        this.reqEmail = reqEmail;
        this.purpose = purpose;
        this.status = status;
        this.quantity = quantity;
        this.date = date;
    }
}
