package com.example.plugin.listeners;

import com.example.plugin.state.PlayerSignatureState;
import com.example.plugin.state.SignatureStateManager;
import com.hypixel.hytale.server.core.event.events.ecs.BreakBlockEvent;
import com.hypixel.hytale.math.vector.Vector3i;

/**
 * Listener para eventos de quebra de bloco.
 * - Carrega energia ao minerar com picareta
 * - Executa explosão se modo explosivo estiver ativo
 */
public class BlockBreakListener {

    private static final int ENERGY_PER_BLOCK = 10;
    private static final int EXPLOSION_RADIUS = 1; // 3x3 = raio 1

    /**
     * Processa evento de quebra de bloco.
     */
    public void onBlockBreak(BreakBlockEvent event) {
        // Verificar se o item é uma picareta (contém "Pickaxe" no ID)
        var itemInHand = event.getItemInHand();
        if (itemInHand == null) {
            return;
        }

        // TODO: Verificar se é uma picareta
        String itemId = itemInHand.getItem().getId();
        if (!itemId.contains("Pickaxe"))
            return;

        // TODO: Obter o PlayerRef do evento
        // Por enquanto, usamos um ID placeholder baseado no evento
        String playerId = "player"; // Placeholder

        SignatureStateManager manager = SignatureStateManager.getInstance();
        // TODO: Usar PlayerRef real quando disponível
        // PlayerSignatureState state = manager.getState(playerRef);

        // Obter posição do bloco
        Vector3i targetBlock = event.getTargetBlock();
        int centerX = targetBlock.getX();
        int centerY = targetBlock.getY();
        int centerZ = targetBlock.getZ();

        // TODO: Implementar lógica de explosão quando tivermos acesso ao PlayerRef
        // Por agora, apenas log para debug
        System.out.println("[SignatureTools] Block broken at: " + centerX + ", " + centerY + ", " + centerZ);
    }
}
