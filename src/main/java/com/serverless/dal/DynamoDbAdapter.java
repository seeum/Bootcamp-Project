package com.serverless.dal;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import lombok.extern.log4j.Log4j;

@Log4j

public class DynamoDbAdapter {

    private static DynamoDbAdapter dbAdapter = null;
    private final AmazonDynamoDB client;
    private  DynamoDBMapper mapper;

    private DynamoDbAdapter(){
        this.client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.AP_SOUTHEAST_1).build();
    }

    public static DynamoDbAdapter getInstance(){
        if(dbAdapter == null) {
            dbAdapter = new DynamoDbAdapter();
            log.debug("----------------------newly created------------");
        }
        else{
            log.debug("------------------old----------------------");
        }
        return dbAdapter;
    }

    public AmazonDynamoDB getDbClient(){
        return this.client;
    }

    public DynamoDBMapper createDbMapper(DynamoDBMapperConfig mapperConfig){
        if(this.client != null)
            mapper = new DynamoDBMapper(this.client , mapperConfig);

        return this.mapper;
    }
}
