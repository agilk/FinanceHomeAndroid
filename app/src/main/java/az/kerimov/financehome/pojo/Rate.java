package az.kerimov.financehome.pojo;

public class Rate {
    private Integer id;

    private UserCurrency currency;

    private String ctime;

    private Double rate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserCurrency getCurrency() {
        return currency;
    }

    public void setCurrency(UserCurrency currency) {
        this.currency = currency;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return currency.getCurrency().getCode()+"("+ctime+": "+rate+")";
    }
}
