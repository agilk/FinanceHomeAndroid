package az.kerimov.financehome.pojo;

public class Error {

    private String code;
    private String message;

    public Error() {
    }

    public Error(Throwable th, String lang){
            code = th.getClass().getName();
            message = th.getStackTrace().toString();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
