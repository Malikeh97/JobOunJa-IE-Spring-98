package API;

public class FailResponse extends BaseResponse  {
    FailResponse(String data) {
        this.status = "fail";
        this.data = data;
    }
}
