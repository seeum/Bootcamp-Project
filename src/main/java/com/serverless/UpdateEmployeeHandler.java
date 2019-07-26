package com.serverless;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.dal.Employee;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UpdateEmployeeHandler implements RequestHandler<Map<String,Object>, ApiGatewayResponse> {


    //Employee employee = new Employee();

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {


        try {

            Map<String,String>pathParameters =(Map<String,String>) input.get("pathParameters");
            String employeeId = pathParameters.get("id");


            JsonNode body = new ObjectMapper().readTree((String) input.get("body"));
            Employee employee = new Employee().get(employeeId);

            employee.setName(body.get("name").asText());

            //Address address = new Address();
            employee.getAddress().setCountry(body.get("address").get("country").asText());
            employee.getAddress().setCity(body.get("address").get("city").asText());
            employee.getAddress().setZip(body.get("address").get("zip").asText());

            //employee.setAddress(address);

            employee.save(employee);
            HashMap<String,String> integrationResponseParameters = new HashMap<>();
            integrationResponseParameters.put("Access-Control-Allow-Origin", "*");

            return ApiGatewayResponse.builder()
                    .setStatusCode(200)
                    .setObjectBody(employee)
                    .setHeaders(integrationResponseParameters)
                    .build();

        } catch (IOException e) {
           //logger.error("Error in saving product: ");
            Response responseBody = new Response("Error in saving product: ", input);
            HashMap<String,String> integrationResponseParameters = new HashMap<>();
            integrationResponseParameters.put("Access-Control-Allow-Origin", "*");
            return ApiGatewayResponse.builder()
                    .setStatusCode(500)
                    .setObjectBody(responseBody)
                    .setHeaders(integrationResponseParameters)
                    .build();
        }


    }
}
