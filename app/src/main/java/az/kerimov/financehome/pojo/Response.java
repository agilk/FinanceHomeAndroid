package az.kerimov.financehome.pojo;

public class Response {
    private Error error;
    private Data data;

    public Response() {
    }

    public Response(Error error, Data data) {
        this.error = error;
        this.data = data;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
