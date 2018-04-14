package az.kerimov.financehome.pojo;

import android.util.Log;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Data {
    private List<Currency> sysCurrencies;
    private List<UserCurrency> currencies;
    private UserCurrency currency;
    private List<Rate> rates;
    private Rate rate;

    private User user;
    private List<Wallet> wallets;
    private Wallet wallet;
    private Integer newId;
    private String sessionKey;

    private Orientation orientation;
    private List<Orientation> orientations;
    private Category category;
    private List<Category> categories;
    private SubCategory subCategory;
    private List<SubCategory> subCategories;

    private List<TransactionReport> transactions;

    public List<UserCurrency> getCurrencies() {


        Collections.sort(currencies, new Comparator<UserCurrency>() {
            @Override
            public int compare(UserCurrency u1, UserCurrency u2) {
                return u1.getCurrency().getShortDescription().compareTo(u2.getCurrency().getShortDescription());

            }
        });

        return currencies;
    }

    public void setCurrencies(List<UserCurrency> currencies) {

        this.currencies = currencies;
    }

    public UserCurrency getUserCurrency() {
        return currency;
    }

    public void setCurrency(UserCurrency currency) {
        this.currency = currency;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Wallet> getWallets() {


        Collections.sort(wallets, new Comparator<Wallet>() {
            @Override
            public int compare(Wallet u1, Wallet u2) {
                return u1.getWalletName().compareTo(u2.getWalletName());

            }
        });

        return wallets;
    }

    public void setWallets(List<Wallet> wallets) {
        this.wallets = wallets;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public Integer getNewId() {
        return newId;
    }

    public void setNewId(Integer newId) {
        this.newId = newId;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public List<Orientation> getOrientations() {
        return orientations;
    }

    public void setOrientations(List<Orientation> orientations) {
        this.orientations = orientations;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {

        this.category = category;
    }

    public List<Category> getCategories() {

        Collections.sort(categories, new Comparator<Category>() {
            @Override
            public int compare(Category u1, Category u2) {
                return u1.getName().compareTo(u2.getName());

            }
        });
        return categories;
    }

    public void setCategories(List<Category> categories) {

        this.categories = categories;
    }

    public SubCategory getSubCategory() {

        Collections.sort(subCategories, new Comparator<SubCategory>() {
            @Override
            public int compare(SubCategory u1, SubCategory u2) {
                return u1.getName().compareTo(u2.getName());

            }
        });
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public List<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }

    public List<Currency> getSysCurrencies() {
        return sysCurrencies;
    }

    public void setSysCurrencies(List<Currency> sysCurrencies) {
        this.sysCurrencies = sysCurrencies;
    }

    public List<TransactionReport> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionReport> transactions) {
        this.transactions = transactions;
    }
}
