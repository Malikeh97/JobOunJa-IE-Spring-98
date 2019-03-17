package API;

public class SuccessResponse<T> extends BaseResponse<T> {
    public SuccessResponse(T data) {
        super();
        this.setStatus("success");
        this.setData(data);
    }
}
