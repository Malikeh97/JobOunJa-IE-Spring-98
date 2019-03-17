package API;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public abstract class BaseResponse<T> {
    protected T data;
    protected String status;

    private String JSONBuilder() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }
}
