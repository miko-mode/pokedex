package com.repository;

import com.config.MongoConfig;
import com.mapping.GameStats;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import static com.mongodb.client.model.Filters.eq;

public class MongoBattleLogRepository implements BattleLogRepository {
    private final MongoClient mongoClient;
    private final MongoDatabase database;

    private final MongoCollection<Document> collection;

    public MongoBattleLogRepository() {

        mongoClient = MongoClients.create(
                MongoConfig.getConnectionString()
        );

        database = mongoClient.getDatabase(
                MongoConfig.getDatabaseName()
        );

        collection = database.getCollection(
                MongoConfig.getCollectionName()
        );
    }

    @Override
    public void save(GameStats gameStats) {

        Document doc = new Document("gameId", gameStats.getGameId())
                .append("userPokemonName", gameStats.getUserPokemonName())
                .append("cpuPokemonName", gameStats.getCpuPokemonName())
                .append("numberOfTurns", gameStats.getNumberOfTurns())
                .append("winnerPokemon", gameStats.getWinnerPokemon());


        collection.insertOne(doc);
    }

    @Override
    public Document findByGameId(String gameId) {
        return collection.find(eq("gameId",gameId)).first();
    }

    @Override
    public void close() {
        mongoClient.close();
    }
}
