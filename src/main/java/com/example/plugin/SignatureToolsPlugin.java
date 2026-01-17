package com.example.plugin;

import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.server.core.asset.type.item.config.Item;
import com.hypixel.hytale.server.core.asset.type.item.config.ItemWeapon;
import com.hypixel.hytale.server.core.modules.entitystats.modifier.StaticModifier;
import com.hypixel.hytale.server.core.modules.entitystats.modifier.StaticModifier.CalculationType;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Plugin principal para Signature Tools.
 * Injeta a signature skill "Explosive Mining" na Picareta de Ferro via Runtime
 * Patching.
 */
public class SignatureToolsPlugin extends JavaPlugin {

    public SignatureToolsPlugin(@NonNullDecl JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        super.setup();

        try {
            System.out.println("[SignatureTools] Attempting to inject signature skill into Iron Pickaxe...");
            injectExplosiveSkill();
            System.out.println("[SignatureTools] Injection successful!");
        } catch (Exception e) {
            System.err.println("[SignatureTools] Failed to inject signature skill:");
            e.printStackTrace();
        }

        System.out.println("[SignatureTools] Plugin loaded! Explosive Mining skill enabled.");
    }

    private void injectExplosiveSkill() throws Exception {
        // 1. Obter a definição da Picareta de Ferro via getAsset()
        Item pickaxe = Item.getAssetMap().getAsset("Tool_Pickaxe_Iron");
        if (pickaxe == null) {
            System.out.println("[SignatureTools] 'Tool_Pickaxe_Iron' not found. Trying 'hytale:Tool_Pickaxe_Iron'...");
            pickaxe = Item.getAssetMap().getAsset("hytale:Tool_Pickaxe_Iron");
        }

        if (pickaxe == null) {
            System.err.println("[SignatureTools] ERROR: Tool_Pickaxe_Iron NOT FOUND in AssetMap! Aborting injection.");
            return;
        }

        // 2. Adicionar a interação Ability1 (Tecla Q)
        Map<InteractionType, String> interactions = pickaxe.getInteractions();
        if (interactions == null) {
            System.out.println("[SignatureTools] Interactions map was null, initializing new map via Reflection...");
            interactions = new HashMap<>();
            setProtectedField(pickaxe, "interactions", interactions);
        }

        // ID do interaction deve bater com o asset criado
        interactions.put(InteractionType.Ability1, "signaturetools:root_interact_explosive");
        System.out.println("[SignatureTools] Added Ability1 interaction.");

        // 3. Adicionar configuração de Weapon (para carregar energia)
        ItemWeapon weaponConfig = new ItemWeapon();

        // Criar modifier via Reflection (construtor é protected)
        Constructor<StaticModifier> modCtor = StaticModifier.class.getDeclaredConstructor();
        modCtor.setAccessible(true);
        StaticModifier energyMod = modCtor.newInstance();

        setProtectedField(energyMod, "amount", 5.0f);
        setProtectedField(energyMod, "calculationType", CalculationType.ADDITIVE);

        Map<String, StaticModifier[]> rawModifiers = new HashMap<>();
        rawModifiers.put("SignatureEnergy", new StaticModifier[] { energyMod });

        setProtectedField(weaponConfig, "rawStatModifiers", rawModifiers);
        setProtectedField(weaponConfig, "rawEntityStatsToClear", new String[] { "SignatureEnergy" });

        // 4. Injetar o Weapon config no Item
        setProtectedField(pickaxe, "weapon", weaponConfig);

        System.out.println("[SignatureTools] Injected Weapon configuration for Signature Energy.");
    }

    // Helper para setar campos protected/private via Reflection
    private void setProtectedField(Object target, String fieldName, Object value) throws Exception {
        Class<?> clazz = target.getClass();
        Field field = null;

        // Tentar encontrar o campo na hierarquia
        while (clazz != null) {
            try {
                field = clazz.getDeclaredField(fieldName);
                break;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }

        if (field == null) {
            throw new NoSuchFieldException("Field " + fieldName + " not found in " + target.getClass().getName());
        }

        field.setAccessible(true);
        field.set(target, value);
    }

    @Override
    protected void shutdown() {
        super.shutdown();
        System.out.println("[SignatureTools] Plugin unloaded!");
    }
}
