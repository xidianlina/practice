package mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MongoTest {

    private static final String HOST = "localhost";
    private static final int PORT = 27017;
    private static final String DB = "test";

    public static void main(String[] args) {
        MongoTest mongoTest = new MongoTest();
        MongoDatabase db = mongoTest.getMongoDatabase("", 0, "");
        // mongoTest.createCollectionTest();
        // mongoTest.mongoBase(db, "test");
        // mongoTest.relationShip(db, "user");

        mongoTest.relationShip2(db, "test");
    }

    public void relationShip2(MongoDatabase db, String col) {
        Document addr1 = new Document("building", "22 A, Indiana Apt")
                .append("pincode", 123456)
                .append("city", "Los Angeles")
                .append("state", "California");

        Document addr2 = new Document("building", "170 A, Acropolis Apt")
                .append("pincode", 456789)
                .append("city", "Chicago")
                .append("state", "Illinois");

        insertOneDocument(db, col, addr1);
        List<Document> docs = findDocumentDetail(db, col);
        System.out.println(docs);
    }

    public void relationShip(MongoDatabase db, String col) {
        Document addr1 = new Document("building", "22 A, Indiana Apt")
                .append("pincode", 123456)
                .append("city", "Los Angeles")
                .append("state", "California");

        Document addr2 = new Document("building", "170 A, Acropolis Apt")
                .append("pincode", 456789)
                .append("city", "Chicago")
                .append("state", "Illinois");

        List<Document> addrs = new ArrayList<Document>();
        addrs.add(addr1);
        addrs.add(addr2);

        Document user = new Document("contact", "987654321")
                .append("dob", "01-01-1991")
                .append("name", "Tom Benzamin")
                .append("address", addrs);

        insertOneDocument(db, col, user);
        findDocument(db, "test");
    }

    public void mongoBase(MongoDatabase db, String col) {
        Document document = new Document("title", "MongoDB").
                append("description", "database").
                append("likes", 100).
                append("by", "Fly");
        Document document2 = new Document("title", "PHP").
                append("description", "project").
                append("likes", 100).
                append("by", "tian");
        Document document3 = new Document("title", "GO").
                append("description", "golang").
                append("likes", 100).
                append("by", "tian");

        List<Document> documents = new ArrayList<Document>();
        documents.add(document);
        documents.add(document2);
        documents.add(document3);

        insertManyDocument(db, col, documents);

        findDocument(db, col);
        System.out.println("----------------------");
        Document document4 = new Document("title", "Java").
                append("description", "language").
                append("likes", 150).
                append("by", "tian");

        insertOneDocument(db, col, document4);
        findDocument(db, col);

        Document update = new Document("likes", 8000);
        updateDocument(db, col, "likes", 150, update);
        System.out.println("================");
        findDocument(db, col);

        deleteOneDocument(db, col, "likes", 100);
        System.out.println("++++++++++++++++");
        findDocument(db, "test");

        deleteManyDocument(db, col, "likes", 100);
        System.out.println("~~~~~~~~~~~~~~~~~");
        findDocument(db, col);
    }

    public void deleteOneDocument(MongoDatabase db, String col, String field, Object value) {
        MongoCollection collection = db.getCollection(col);
        collection.deleteOne(Filters.eq(field, value));
    }

    public void deleteManyDocument(MongoDatabase db, String col, String field, Object value) {
        MongoCollection collection = db.getCollection(col);
        collection.deleteMany(Filters.eq(field, value));
    }

    public void updateDocument(MongoDatabase db, String col, String field, Object value, Document update) {
        MongoCollection collection = db.getCollection(col);
        collection.updateMany(Filters.eq(field, value), new Document("$set", update));
    }

    public void findDocument(MongoDatabase db, String col) {
        MongoCollection<Document> collections = db.getCollection(col);

        FindIterable<Document> findIterable = collections.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while (mongoCursor.hasNext()) {
            System.out.println(mongoCursor.next());
        }
    }

    public List<Document> findDocumentDetail(MongoDatabase db, String col) {
        MongoCollection<Document> collections = db.getCollection(col);

        FindIterable<Document> findIterable = collections.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        List<Document> res = new ArrayList<Document>();
        while (mongoCursor.hasNext()) {
            Document doc = mongoCursor.next();
            for (Map.Entry<String, Object> entry : doc.entrySet()) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }
            System.out.println("-----------------");
            res.add(doc);
        }

        return res;
    }

    public void insertOneDocument(MongoDatabase db, String col, Document document) {
        MongoCollection<Document> collection = db.getCollection(col);
        collection.insertOne(document);
    }

    public void insertManyDocument(MongoDatabase db, String col, List<Document> documents) {
        MongoCollection<Document> collection = db.getCollection(col);
        collection.insertMany(documents);
    }

    public MongoCollection<Document> getCollection(MongoDatabase db, String col) {
        MongoCollection<Document> collection = db.getCollection(col);
        return collection;
    }

    public void createCollectionTest() {
        MongoDatabase db = getMongoDatabase("", 0, "");
        if (db == null) {
            return;
        }
        String dbName = db.getName();
        System.out.println("database name is " + dbName);

        db.createCollection("my_mongo");
    }

    public MongoDatabase getMongoDatabase(String host, int port, String db) {
        MongoDatabase mongoDatabase = null;
        try {
            MongoClient mongoClient = null;
            if (host.isEmpty() || port == 0) {
                mongoClient = new MongoClient(HOST, PORT);
            } else {
                mongoClient = new MongoClient(host, port);
            }

            if (db.isEmpty()) {
                mongoDatabase = mongoClient.getDatabase(DB);
            } else {
                mongoDatabase = mongoClient.getDatabase(db);
            }
            System.out.println("Connect to database success " + mongoClient);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }

        return mongoDatabase;
    }
}
