package com.example.plugin.listeners;

import com.example.plugin.state.PlayerSignatureState;
import com.example.plugin.state.SignatureStateManager;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.event.events.player.PlayerInteractEvent;
import com.hypixel.hytale.server.core.util.EventTitleUtil;
import com.hypixel.hytale.protocol.InteractionType;

/**
 * Listener para eventos de interação do jogador.
 * Captura a tecla Q (Ability1) para ativar o modo explosivo.
 */
public class AbilityListener {

    /**
     * Processa evento de interação do jogador.
     */
    @SuppressWarnings({ "deprecation", "removal" }) // PlayerInteractEvent and Player.getPlayerRef() are deprecated
                                                    // but still required for keyboard ability detection
    public void onPlayerInteract(PlayerInteractEvent event) {
        // Get player and playerRef from the event
        var player = event.getPlayer();
        var playerRef = player.getPlayerRef();

        // Verificar se é a tecla Q (Ability1)
        InteractionType interactionType = event.getActionType();

        if (interactionType != InteractionType.Ability1) {
            return;
        }

        // TODO: Verificar se o jogador está segurando uma picareta
        // ItemStack heldItem = playerRef.getHeldItem();
        // if (!isPickaxe(heldItem)) return;

        SignatureStateManager manager = SignatureStateManager.getInstance();
        PlayerSignatureState state = manager.getState(playerRef);

        // Verificar se a energia está cheia
        if (!state.isEnergyFull()) {
            EventTitleUtil.showEventTitleToPlayer(
                    playerRef,
                    Message.raw(""),
                    Message.raw("§c⚡ Not enough energy! §7(" + state.getSignatureEnergy() + "/100)"),
                    true);
            return;
        }

        // Ativar modo explosivo
        state.activateExplosiveMode();

        EventTitleUtil.showEventTitleToPlayer(
                playerRef,
                Message.raw("§6⚡ EXPLOSIVE MINING ACTIVATED! ⚡"),
                Message.raw("§eNext 3 blocks will explode!"),
                true);
    }
}
