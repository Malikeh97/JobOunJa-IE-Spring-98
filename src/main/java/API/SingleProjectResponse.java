package API;

import API.data.SingleProjectData;

public class SingleProjectResponse extends SuccessResponse<SingleProjectData> {
    public SingleProjectResponse(SingleProjectData data) {
        super(data);
    }
}
