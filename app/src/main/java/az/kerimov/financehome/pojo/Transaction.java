package az.kerimov.financehome.pojo;

import java.util.Date;

public class Transaction {
    private Integer id;

    private User user;

    private Category category;

    private SubCategory subCategory;

    private Wallet wallet;

    private Wallet walletOther;

    private UserCurrency currency;

    private Double amount;

    private String notes;

    private String cdate;

    private Date ctime;

    private Date added;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public Wallet getWalletOther() {
        return walletOther;
    }

    public void setWalletOther(Wallet walletOther) {
        this.walletOther = walletOther;
    }

    public UserCurrency getCurrency() {
        return currency;
    }

    public void setCurrency(UserCurrency currency) {
        this.currency = currency;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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
    }
}
