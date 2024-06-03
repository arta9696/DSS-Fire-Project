package app.Database;

import app.DataObjectModels.User;
import app.Main;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.MongoTimeoutException;
import com.mongodb.client.*;
import org.bson.*;
import org.bson.conversions.Bson;
import org.bson.types.Binary;

import java.lang.reflect.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class DBCon implements AutoCloseable {
    MongoClient mongoClient;
    MongoDatabase database;
    public DBCon() {

        mongoClient = MongoClients.create(MongoClientSettings.builder()
                .applyToSocketSettings(builder -> {
                    builder.connectTimeout(3000, MILLISECONDS);
                    builder.readTimeout(3000, MILLISECONDS);
                })
                .applyToClusterSettings( builder -> builder.serverSelectionTimeout(3000, MILLISECONDS))
                .applyConnectionString(new ConnectionString(Main.DBConnectionString))
                .build());
        database = mongoClient.getDatabase(Main.DatabaseName);
    }

    @Override
    public void close() throws Exception {
        mongoClient.close();
    }

    private static <T> Document putInDocument(T object) {
        Field[] fields = object.getClass().getDeclaredFields();
        Document document = new Document();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(object);
                document.put(field.getName(), value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return document;
    }
    private static Document getQuery(Map<String, Object> identifiers) {
        Document searchQuery = new Document();
        for (String key: identifiers.keySet()) {
            searchQuery.put(key, identifiers.get(key));
        }
        return searchQuery;
    }

    private <T> void Create(T object){
        Class<?> clazz = object.getClass();
        MongoCollection<Document> collection = database.getCollection(clazz.getSimpleName());
        Document document = putInDocument(object);

        collection.insertOne(document);
    }
    private <T> List<T> Read(Class<T> clazz, Map<String, Object> identifiers) throws NoSuchMethodException {
        Field[] fields = clazz.getDeclaredFields();
        Constructor<?> constructor = clazz.getDeclaredConstructor(Arrays.stream(fields).map(Field::getType).toArray(Class[]::new));
        constructor.setAccessible(true);

        MongoCollection<Document> collection = database.getCollection(clazz.getSimpleName());
        Document searchQuery = getQuery(identifiers);

        FindIterable<Document> cursor = collection.find(searchQuery);
        List<T> objects = new ArrayList<>();
        try (final MongoCursor<Document> cursorIterator = cursor.cursor()) {
            while (cursorIterator.hasNext()) {
                Document objectDocument = cursorIterator.next();

                Object[] array = new Object[fields.length];
                for (int i = 0; i<fields.length; i++){
                    fields[i].setAccessible(true);

                    if(objectDocument.get(fields[i].getName()).getClass()==Binary.class){
                        array[i] = ((Binary)(objectDocument.get(fields[i].getName()))).getData();
                    }else {
                        array[i] = objectDocument.get(fields[i].getName());
                    }
                }

                Object object = constructor.newInstance(array);
                objects.add((T)object);
            }
        } catch (InvocationTargetException | InstantiationException | MongoTimeoutException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return objects;
    }
    private <T> void Update(Map<String, Object> identifiers, T replacementValues){
        Class<?> clazz = replacementValues.getClass();
        MongoCollection<Document> collection = database.getCollection(clazz.getSimpleName());
        Document query = getQuery(identifiers);
        Document newDocument = putInDocument(replacementValues);

        Document updateObject = new Document();
        updateObject.put("$set", newDocument);
        collection.updateOne(query, updateObject);
    }
    private  <T> void Delete(Class<T> clazz, Map<String, Object> identifiers){
        MongoCollection<Document> collection = database.getCollection(clazz.getSimpleName());
        Document searchQuery = getQuery(identifiers);

        collection.deleteOne(searchQuery);
    }

    private static String hashPassword(char[] password, byte[] salt){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            byte[] hashedPassword = md.digest(new String(password).getBytes(StandardCharsets.UTF_8));
            return new String(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
    private static byte[] saltGen() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    public void createUser(String login, String password, String role) {
        byte[] salt = DBCon.saltGen();
        String pass_hash = DBCon.hashPassword(password.toCharArray(), salt);
        Create(new User(login, pass_hash, role, salt));
    }
    public int CheckUser(String login, char[] password) throws NoSuchMethodException {
        //TODO this is unsecure!
        // if time available - change to "Check on server" system
        // Send user pass on server, do this check, return user role or exception!

        Map<String, Object> map = new HashMap<>();
        map.put("login", login);
        List<User> users = Read(User.class, map);
        if(users.isEmpty()){
            return -2;
        }

        Optional<User> checkUser = Optional.empty();
        while (!users.isEmpty()){
            User tryUser = users.get(0);
            if(Objects.equals(hashPassword(password, tryUser.salt), tryUser.pass_hash)){
                checkUser = Optional.of(tryUser);
                break;
            }else {
                users.remove(tryUser);
            }
        }
        if(checkUser.isEmpty()){
            return -1;
        }
        if(Objects.equals(checkUser.get().role, "Client")){
            return 0;
        }
        if(Objects.equals(checkUser.get().role, "Engineer")){
            return 1;
        }
        return -10;//Unknown role
    }
}
