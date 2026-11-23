package com.config;
import io.github.cdimascio.dotenv.Dotenv;
public class MongoConfig {
    private static final Dotenv dotenv = Dotenv.load();

    public static String getConnectionString() {
        return dotenv.get("MONGODB_URI");
    }

    public static String getDatabaseName() {
        return dotenv.get("MONGODB_DATABASE");
    }

    public static String getCollectionName() {
        return dotenv.get("MONGODB_COLLECTION");
    }
}
