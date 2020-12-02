import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.util.JSON;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.net.UnknownHostException;

public class MongoDb {
    private static String uri = "mongodb+srv://Admin:1@cluster0.nqruu.mongodb.net/test";
    private static MongoClientURI clientURI = new MongoClientURI(uri);
    private static MongoClient mongoClient = new MongoClient(clientURI);
    private static MongoDatabase mongoDb = mongoClient.getDatabase("Java");
    private static MongoCollection collection = (MongoCollection) mongoDb.getCollection("user");

    public static boolean addNewUser(String email,String username,String password) {
        Document check = (Document) collection.find(Filters.eq("email", email)).first();
        if (check != null){
            return false;
        }else{
            Document document = new Document("email",email);
            document.append("username",username);
            document.append("password",password);
            collection.insertOne(document);
            return true;
        }
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

    public static int login(String email,String password){
        Document check = (Document) collection.find(Filters.eq("email", email)).first();
        if (check == null){
            return 1;
        }else{
            String docPass= (String) check.get("password");
            if (docPass.trim().equals(password.trim())){
                return 0;
            }else{
                return 2;
            }
        }
    }

    public static void main(String[] args) throws UnknownHostException {
        System.out.println("Database Connected");

        //addNewUser("namnguyen","namnguyen21");
        //if (authenticatedUser("namnguyen","namnguyen21") == true){
        //    updateUserAccount("namle","namle","namnguyen","namnguyen21");
        //}

    }
}