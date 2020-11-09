import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.net.UnknownHostException;

public class MongoDb {
    private static String uri = "mongodb+srv://Admin:1@cluster0.nqruu.mongodb.net/test";
    private static MongoClientURI clientURI = new MongoClientURI(uri);
    private static MongoClient mongoClient = new MongoClient(clientURI);
    private static MongoDatabase mongoDb = mongoClient.getDatabase("Java");
    private static MongoCollection collection = (MongoCollection) mongoDb.getCollection("user");

    public static void addNewUser(String username,String password) {
        Document document = new Document("username",username);
        document.append("password",password);
        collection.insertOne(document);
        System.out.println("User has been added");
    }

    public static boolean authenticatedUser(String username,String password){
        Document document = new Document("username",username);
        document.append("password",password);
        Document found = (Document) collection.find(document).first();
        if (found != null){
            return true;
        }else{
            return false;
        }
    }

    public static void updateUserAccount(String newUsername,String newPassword,String username,String password){
        Document document = new Document("username",username);
        document.append("password",password);
        Document found = (Document) collection.find(document).first();
        if (found != null){
            Bson updatedDocument = new Document("username",newUsername).append("password", newPassword);
            Bson updatedoperation = new Document("$set",updatedDocument);
            collection.updateOne(found,updatedoperation);
        }
    }

    public static void main(String[] args) throws UnknownHostException {
        System.out.println("Database Connected");

        addNewUser("namnguyen","namnguyen21");
        //if (authenticatedUser("namnguyen","namnguyen21") == true){
        //    updateUserAccount("namle","namle","namnguyen","namnguyen21");
        //}

    }
}