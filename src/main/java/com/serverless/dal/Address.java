package com.serverless.dal;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@DynamoDBDocument
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @JsonProperty
    private String country;
    @JsonProperty
    private String city;
    @JsonProperty
    private String zip;
}
