package az.kerimov.financehome.pojo;

public class Wallet {
    private Integer id;

    private User user;

    private UserCurrency currency;

    private String customName;

    private Double balanceAmount;

    private Boolean defaultElement;

    public Boolean getDefaultElement() {
        return defaultElement;
    }

    public void setDefaultElement(Boolean defaultElement) {
        this.defaultElement = defaultElement;
    }

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

    public UserCurrency getCurrency() {
        return currency;
    }

    public void setCurrency(UserCurrency currency) {
        this.currency = currency;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public Double getBalanceAmount() {
        return Math.round(balanceAmount*100.0)/100.0;
    }

    public void setBalanceAmount(Double balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    @Override
    public String toString() {
        return user.getLogin()+"("+customName+"-["+balanceAmount+" "+currency.getCurrency().getShortDescription()+"]"+")";
    }
}
