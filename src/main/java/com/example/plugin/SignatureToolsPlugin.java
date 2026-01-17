package com.example.plugin;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class SignatureToolsPlugin extends JavaPlugin {

    public SignatureToolsPlugin(@NonNullDecl JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        super.setup();
        System.out.println("[SignatureTools] Plugin Loaded!");
        System.out.println("[SignatureTools] Custom Item 'Explosive Pickaxe' registered.");
        System.out.println("[SignatureTools] Use command: /give signaturetools:explosive_pickaxe 1");
    }

    @Override
    protected void shutdown() {
        super.shutdown();
        System.out.println("[SignatureTools] Plugin Unloaded!");
    }
}
