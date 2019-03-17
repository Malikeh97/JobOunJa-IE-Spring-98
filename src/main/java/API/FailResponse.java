package API;

public class FailResponse extends BaseResponse<String>  {
    public FailResponse(String data) {
        super();
        this.setStatus("fail");
        this.setData(data);
    }
}
