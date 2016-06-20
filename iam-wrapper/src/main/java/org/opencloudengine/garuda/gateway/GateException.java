package org.opencloudengine.garuda.gateway;

import org.opencloudengine.garuda.model.GateResponse;
import org.opencloudengine.garuda.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by uengine on 2016. 6. 16..
 */
public class GateException {

    public static String NO_MAPPING_URI = "no_mapping_uri";
    public static String CLASS_NOT_FOUND = "class_not_found";
    public static String WORKFLOW_NOT_SUPPORT = "workflow_not_support";
    public static String SERVER_ERROR = "server_error";
    public static String AUTHENTICATION_FAIL = "authentication_fail";

    public GateResponse getResponse(String code, HttpServletRequest request, String msg) {
        GateResponse response = new GateResponse();
        switch (code) {
            case "no_mapping_uri":
                response.setError(NO_MAPPING_URI);
                response.setError_description("No mapping uri found for" + request.getPathInfo());
                break;

            case "class_not_found":
                response.setError(CLASS_NOT_FOUND);
                response.setError_description("class not found while execute " + request.getPathInfo());
                break;

            case "workflow_not_support":
                response.setError(WORKFLOW_NOT_SUPPORT);
                response.setError_description("Workflow is not supported yet : " + request.getPathInfo());
                break;

            case "server_error":
                response.setError(SERVER_ERROR);
                response.setError_description("unknown server error while execute " + request.getPathInfo());
                break;

            case "authentication_fail":
                response.setError(AUTHENTICATION_FAIL);
                response.setError_description("authentication failed : " + request.getPathInfo());
                break;

            default:
                response.setError(SERVER_ERROR);
                response.setError_description("unknown server error while execute " + request.getPathInfo());
                break;
        }
        if (!StringUtils.isEmpty(msg)) {
            response.setError_description(msg);
        }
        return response;
    }
}