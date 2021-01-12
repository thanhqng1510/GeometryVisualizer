import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.util.JSON;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.awt.*;
import java.awt.desktop.SystemSleepEvent;
import java.io.*;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.regex.Pattern;

class MongoDb {
    private static String uri = "mongodb+srv://Admin:1@cluster0.nqruu.mongodb.net/test";
    private static MongoClientURI clientURI = new MongoClientURI(uri);
    private static MongoClient mongoClient = new MongoClient(clientURI);
    private static MongoDatabase mongoDb = mongoClient.getDatabase("Java");
    private static MongoCollection collection = (MongoCollection) mongoDb.getCollection("user");

    private static String emailUser = null;



    public static int addNewUser(String email,String username,String password) {
        String EMAIL_PATTERN =
                "^[a-zA-Z][\\w-]+@([\\w]+\\.[\\w]+|[\\w]+\\.[\\w]{2,}\\.[\\w]{2,})$";
        if (!Pattern.matches(EMAIL_PATTERN, email)){
            return 1;
        }
        Document check = (Document) collection.find(Filters.eq("email", email)).first();
        if (check != null){
            return 2;
        }else{
            Document document = new Document("email",email);
            document.append("username",username);
            document.append("password",password);
            collection.insertOne(document);
            return 0;
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

    public int login(String email,String password){
        Document check = (Document) collection.find(Filters.eq("email", email)).first();
        if (check == null){
            return 1;
        }else{
            String docPass= (String) check.get("password");
            if (docPass.trim().equals(password.trim())){
                this.emailUser = email;
                return 0;
            }else{
                return 2;
            }
        }
    }
    public static void uploadimage(String filePath,String fileName){
        try{
            MongoDatabase imgDb = mongoClient.getDatabase("image");
            GridFSBucket gridBucket = GridFSBuckets.create((MongoDatabase) imgDb);
            InputStream inStream = new FileInputStream(new File(filePath));
            GridFSUploadOptions uploadOptions = new GridFSUploadOptions()
                    .chunkSizeBytes(1024).metadata(new Document("type", "image")
                            .append("content_type", "image/png"));
            ObjectId fileId = gridBucket.uploadFromStream(fileName, inStream, uploadOptions);

            Document check = (Document) collection.find(Filters.eq("email", emailUser)).first();
            if (check == null){

            }else{
                if (check.get("image") == null){
                    Vector<ObjectId> arr = new Vector<>();
                    arr.add(fileId);

                    BasicDBObject doc1=new BasicDBObject();
                    doc1.append("image", arr);

                    collection.updateOne(check, new BasicDBObject("$set",doc1));
                }else{
                    Vector<ObjectId> arr = new Vector<>();

                    ArrayList<ObjectId> ar11;
                    ar11  = (ArrayList<ObjectId>) check.get("image");

                    for (int i=0; i < ar11.size();i++){
                        arr.add(ar11.get(i));
                    }
                    arr.add(fileId);
                    BasicDBObject doc1=new BasicDBObject();
                    doc1.append("image", arr);
                    collection.updateOne(check, new BasicDBObject("$set",doc1));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static Vector<String> retrieveimage(){
        MongoDatabase imgDb = mongoClient.getDatabase("image");
        GridFSBucket gridBucket = GridFSBuckets.create(imgDb);
        Document check = (Document) collection.find(Filters.eq("email", emailUser)).first();
        Vector<String> rs = new Vector<>();
        if (check != null){
            try {
                ArrayList<ObjectId> ar11;
                ar11  = (ArrayList<ObjectId>) check.get("image");
                for (int i=0; i < ar11.size();i++){
                    GridFSFile gridFSFile = gridBucket.find(Filters.eq("_id",ar11.get(i))).first();
                    //File myObj = new File("D:/GV/" + emailUser + "/" + gridFSFile.getFilename() +".jpg");
                    rs.add(gridFSFile.getFilename());
                    FileOutputStream fileOutputStream = new FileOutputStream("C:/Users/hoang/GV/" + emailUser + "-" + gridFSFile.getFilename() +".jpg");
                    gridBucket.downloadToStream(gridFSFile.getId(), fileOutputStream);

                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        return rs;
    }





    public MongoDb(String email){
        this.emailUser = email;
    }
}