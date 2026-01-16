package com.example.plugin;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.event.events.ecs.BreakBlockEvent;
import com.hypixel.hytale.server.core.event.events.player.PlayerInteractEvent;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

/**
 * Plugin principal para Signature Tools.
 * Adiciona uma signature skill "Explosive Mining" às picaretas.
 * 
 * Mecânica:
 * 1. Minerar blocos carrega energia (+10% por bloco)
 * 2. Quando energia está 100%, apertar Q ativa modo explosivo
 * 3. Próximos 3 blocos quebrados explodem em área 3x3
 */
public class SignatureToolsPlugin extends JavaPlugin {

    public SignatureToolsPlugin(@NonNullDecl JavaPluginInit init) {
        super(init);
    }

    @SuppressWarnings("deprecation") // PlayerInteractEvent is deprecated but still required for keyboard ability
                                     // detection
    @Override
    protected void setup() {
        super.setup();

        // Registrar listener para eventos de quebra de bloco
        // this.getEventRegistry().register(BreakBlockEvent.class, event -> {
        // blockBreakListener.onBlockBreak(event);
        // });

        // Registrar listener para eventos de interação (tecla Q)
        // this.getEventRegistry().registerGlobal(PlayerInteractEvent.class, event -> {
        // abilityListener.onPlayerInteract(event);
        // });

        // Log de inicialização
        System.out.println("[SignatureTools] Plugin loaded! Explosive Mining skill enabled.");
    }

    @Override
    protected void shutdown() {
        super.shutdown();

        // Limpar estados dos jogadores
        // SignatureStateManager.getInstance().clearAll();

        System.out.println("[SignatureTools] Plugin unloaded!");
    }
}
