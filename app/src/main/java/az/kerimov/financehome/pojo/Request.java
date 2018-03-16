package az.kerimov.financehome.pojo;

public class Request {
    //for Currencies
    private String currencyCode;
    private String currencyShortDescription;
    private String currencyDescription;

    //for Rates
    private Double rate;
    private String rateDate;

    //for Users
    private String login;
    private String password;
    private Integer userId;
    private Integer walletId;
    private String customName;
    private Double newBalance;
    private String firstName;
    private String lastName;
    private String mobilePhone;

    //for Categories
    private Integer orientationId;
    private Integer categoryId;
    private Integer subCategoryId;
    private Boolean debt;

    //for Transactions
    private Double amount;
    private String notes;
    private String date;
    private Integer walletIdOther;
    private Integer transactionId;

    //Common
    private String lang;
    private String sessionKey;

    public String getCurrencyCode()  {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyShortDescription() {
        return currencyShortDescription;
    }

    public void setCurrencyShortDescription(String currencyShortDescription) {
        this.currencyShortDescription = currencyShortDescription;
    }

    public String getCurrencyDescription() {
        return currencyDescription;
    }

    public void setCurrencyDescription(String currencyDescription) {
        this.currencyDescription = currencyDescription;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getRateDate() {
        return rateDate;
    }

    public void setRateDate(String rateDate) {
        this.rateDate = rateDate;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getLogin()  {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword()  {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getWalletId()  {
        return walletId;
    }

    public void setWalletId(Integer walletId) {
        this.walletId = walletId;
    }

    public String getCustomName()  {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public Double getNewBalance()  {
        return newBalance;
    }

    public void setNewBalance(Double newBalance) {
        this.newBalance = newBalance;
    }

    public Integer getOrientationId()  {
        return orientationId;
    }

    public void setOrientationId(Integer orientationId) {
        this.orientationId = orientationId;
    }

    public Integer getCategoryId()  {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getSubCategoryId()  {
        return subCategoryId;
    }

    public void setSubCategoryId(Integer subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public boolean isDebt() {
        return debt;
    }

    public void setDebt(boolean debt) {
        this.debt = debt;
    }

    public Double getAmount()  {
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

    public String getDate()  {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getWalletIdOther() {
        return walletIdOther;
    }

    public void setWalletIdOther(Integer walletIdOther) {
        this.walletIdOther = walletIdOther;
    }

    public Integer getTransactionId()  {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
}
