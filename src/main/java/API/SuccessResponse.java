package API;

public class SuccessResponse extends BaseResponse {
     <T> SuccessResponse(T data) {
        this.status = "success";
        this.data = data;
    }
}
