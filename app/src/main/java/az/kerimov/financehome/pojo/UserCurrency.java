package az.kerimov.financehome.pojo;;

public class UserCurrency {

    private Integer id;

    private User user;

    private Currency currency;

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

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
