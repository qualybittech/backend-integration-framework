package org.backendtestframework.util;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.Iterator;

import org.backendtestframework.models.MongoDBResponse;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import static com.mongodb.client.model.Filters.eq;

public class MongoDBClient {
	
   public static void main( String args[] ) {
	   
	   Util util = new Util();
	    
       String dbUserName = util.getValueFromAppProperties("userName");
       String dbPassword = util.getValueFromAppProperties("password");
       String dbClusterName = util.getValueFromAppProperties("cluster");
	   
	   ConnectionString connectionString = new ConnectionString("mongodb+srv://"+dbUserName+":"+dbPassword
			   													+"@"+dbClusterName+"?retryWrites=true&w=majority");
	   
	   MongoClientSettings settings = MongoClientSettings.builder()
	           .applyConnectionString(connectionString)
	           .build();
	   
      //Creating a MongoDB client
	   MongoClient mongoClient = MongoClients.create(settings);
	   
      //Connecting to the database
	   MongoDatabase database = mongoClient.getDatabase("AutomationDB");
	   
      //Creating a collection object
      MongoCollection<Document> collection = database.getCollection("Collection1");
      
      MongoCollection<MongoDBResponse> mongoDBResponse = database.getCollection("Collection1", MongoDBResponse.class);
      
      System.out.println(mongoDBResponse);
      
      /*Retrieving the documents
      FindIterable<Document> iterDoc = collection.find();
      Iterator it = iterDoc.iterator();
      while (it.hasNext()) {
         System.out.println(it.next());
      }*/
      
      Document document = collection.find(eq("_id", new ObjectId("61fe3660667d3aa67ece73e4"))).first();
      if (document == null) {
    	  System.out.println("Document not exist");
    	      	  
      } else {
    	  System.out.println(document);
      }
   }
}