package az.kerimov.financehome.pojo;

import java.util.Date;

public class TransactionReport {
    private Integer id;

    private User user;

    private Double amount;

    private Double amountLocal;

    private Double amountUsd;

    private String currency;

    private String wallet1;

    private String wallet2;

    private String category;

    private String subCategory;

    private String notes;

    private String cdate;
/*
    private Date ctime;

    private Date added;
*/
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getAmountLocal() {
        return amountLocal;
    }

    public void setAmountLocal(Double amountLocal) {
        this.amountLocal = amountLocal;
    }

    public Double getAmountUsd() {
        return amountUsd;
    }

    public void setAmountUsd(Double amountUsd) {
        this.amountUsd = amountUsd;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getWallet1() {
        return wallet1;
    }

    public void setWallet1(String wallet1) {
        this.wallet1 = wallet1;
    }

    public String getWallet2() {
        return wallet2;
    }

    public void setWallet2(String wallet2) {
        this.wallet2 = wallet2;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCdate() {
        return cdate;
    }

    public void setCdate(String cdate) {
        this.cdate = cdate;
    }
/*
    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Date getAdded() {
        return added;
    }

    public void setAdded(Date added) {
        this.added = added;
    }*/
}
