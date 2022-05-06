package org.backendtestframework.models;

import org.bson.codecs.pojo.annotations.BsonProperty;

public class MongoDBResponse {

    @BsonProperty(value = "name")
	public String name;
    
    @BsonProperty(value = "age")
	public String age;
    
    @BsonProperty(value = "car")
	public String car;
}
