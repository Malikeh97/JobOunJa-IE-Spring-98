package API;

public class ErrorResponse extends BaseResponse {
    ErrorResponse(String data) {
        this.status = "error";
        this.data = data;
    }
}
