package API;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ErrorResponse extends BaseResponse<String> {
    private Integer code;

    public ErrorResponse(String data, Integer code) {
        super();
        this.setStatus("error");
        this.setData(data);
        this.setCode(code);
    }
}
