package api;

public class FailResponse<T> extends BaseResponse<T>  {
    public FailResponse(T data) {
        super();
        this.setStatus("fail");
        this.setData(data);
    }
}
