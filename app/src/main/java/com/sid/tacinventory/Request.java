package com.sid.tacinventory;

import java.util.Date;

public class Request {
    public String reqid;
    public String itemName;
    public String pName;
    public String rollNumber;
    public Date reqDate;
    public Integer status;
    public Integer quantity;//0 for pending 1 for processed 2 for cancelled
    public Request()
    {

    }

    public Request(String reqid, String itemName, String pName, String rollNumber, Date reqDate, Integer status, Integer quantity) {
        this.reqid = reqid;
        this.itemName = itemName;
        this.pName = pName;
        this.rollNumber = rollNumber;
        this.reqDate = reqDate;
        this.status = status;
        this.quantity = quantity;
    }
}
