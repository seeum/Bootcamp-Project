package com.serverless;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.dal.Employee;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DeleteEmployeeHandler implements RequestHandler<Map<String ,Object>, ApiGatewayResponse> {


    @Override
    public ApiGatewayResponse handleRequest(Map<String ,Object> input, Context context) {

        Map<String,String>pathParameters =(Map<String,String>) input.get("pathParameters");
        String employeeId = pathParameters.get("id");

        Boolean success = new Employee().delete(employeeId);

        HashMap<String,String> integrationResponseParameters = new HashMap<>();
        integrationResponseParameters.put("Access-Control-Allow-Origin", "*");
        if (success) {
            return ApiGatewayResponse.builder()
                    .setStatusCode(204)
                    .setObjectBody("deleted successfully")
                    .setHeaders(integrationResponseParameters)
                    .build();
        } else {
            return ApiGatewayResponse.builder()
                    .setStatusCode(404)
                    .setObjectBody("Product with id: '" + employeeId + "' not found.")
                    .setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless"))
                    .build();
        }


    }


}
