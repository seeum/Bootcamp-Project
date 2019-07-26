package com.serverless;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.dal.Employee;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

public class CreateEmployeeHandler implements RequestHandler<Map<String,Object>,ApiGatewayResponse> {


    private final Logger logger = Logger.getLogger(this.getClass());

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {

        try {

            Employee employee = new ObjectMapper().readValue((String) input.get("body"),Employee.class);

            logger.debug("---------------------------------------------------------");
            logger.debug(employee.getAddress().getCity());
            logger.debug("---------------------------------------------------------");

            employee.save(employee);

            HashMap<String,String> integrationResponseParameters = new HashMap<>();
            integrationResponseParameters.put("Access-Control-Allow-Origin", "*");

            return ApiGatewayResponse.builder()
                    .setStatusCode(200)
                    .setObjectBody(employee)
                    .setHeaders(integrationResponseParameters)
                    .build();

        } catch (IOException e) {
            logger.error("Error in saving product: ");
            Response responseBody = new Response("Error in saving product: ", input);
            return ApiGatewayResponse.builder()
                    .setStatusCode(500)
                    .setObjectBody(responseBody)
                    .setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless"))
                    .build();
        }


    }
}
