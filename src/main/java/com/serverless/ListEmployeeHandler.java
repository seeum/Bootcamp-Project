package com.serverless;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.dal.Employee;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListEmployeeHandler implements RequestHandler<Map<String,Object>,ApiGatewayResponse> {


    //private final Logger logger = Logger.getLogger(this.getClass());




    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {

        Employee employee = new Employee();
        List<Employee> employees = employee.list();


        HashMap<String,String>integrationResponseParameters = new HashMap<>();
        integrationResponseParameters.put("Access-Control-Allow-Origin", "*");
        return ApiGatewayResponse.builder()
                .setStatusCode(200)
                .setObjectBody(employees)
                .setHeaders(integrationResponseParameters)
                .build();
    }
}
