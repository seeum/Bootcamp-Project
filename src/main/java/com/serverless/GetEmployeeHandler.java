package com.serverless;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.dal.Employee;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

public class GetEmployeeHandler implements RequestHandler<Map<String,Object>,ApiGatewayResponse> {

    private final Logger logger = Logger.getLogger(this.getClass());

    Employee employee = new Employee();
    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {

        Map<String,String>pathParameters =(Map<String,String>) input.get("pathParameters");
        String employeeId = pathParameters.get("id");

        Employee employee = new Employee().get(employeeId);
        HashMap<String,String> integrationResponseParameters = new HashMap<>();
        integrationResponseParameters.put("Access-Control-Allow-Origin", "*");
        if (employee != null) {
            return ApiGatewayResponse.builder()
                    .setStatusCode(200)
                    .setObjectBody(employee)
                    .setHeaders(integrationResponseParameters)
                    .build();
        } else {
            return ApiGatewayResponse.builder()
                    .setStatusCode(404)
                    .setObjectBody("Employee with id: '" + employee + "' not found.")
                    .setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless"))
                    .build();
        }

    }
}


