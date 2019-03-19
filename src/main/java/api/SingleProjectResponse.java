package api;

import api.data.SingleProjectData;

public class SingleProjectResponse extends SuccessResponse<SingleProjectData> {
    public SingleProjectResponse(SingleProjectData data) {
        super(data);
    }
}
