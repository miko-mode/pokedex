package com.repository;
import com.mapping.GameStats;
import org.bson.Document;

import java.util.List;

public interface BattleLogRepository extends AutoCloseable{
    void save(GameStats gameStats);
    Document findByGameId(String gameId);
}
