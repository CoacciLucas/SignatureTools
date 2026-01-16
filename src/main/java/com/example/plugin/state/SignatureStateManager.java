package com.example.plugin.state;

import com.hypixel.hytale.server.core.universe.PlayerRef;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

/**
 * Gerenciador centralizado dos estados de signature skill dos jogadores.
 */
public class SignatureStateManager {
    private static SignatureStateManager instance;
    private final Map<String, PlayerSignatureState> playerStates;

    private SignatureStateManager() {
        this.playerStates = new ConcurrentHashMap<>();
    }

    public static SignatureStateManager getInstance() {
        if (instance == null) {
            instance = new SignatureStateManager();
        }
        return instance;
    }

    /**
     * Obtém ou cria o estado de um jogador.
     */
    public PlayerSignatureState getState(PlayerRef playerRef) {
        String playerId = playerRef.getUuid().toString();
        return playerStates.computeIfAbsent(playerId, k -> new PlayerSignatureState());
    }

    /**
     * Remove o estado de um jogador (quando desconecta).
     */
    public void removeState(PlayerRef playerRef) {
        String playerId = playerRef.getUuid().toString();
        playerStates.remove(playerId);
    }

    /**
     * Limpa todos os estados.
     */
    public void clearAll() {
        playerStates.clear();
    }
}
