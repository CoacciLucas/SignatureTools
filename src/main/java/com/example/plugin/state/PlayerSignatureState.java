package com.example.plugin.state;

/**
 * Estado da signature skill para cada jogador.
 */
public class PlayerSignatureState {
    private int signatureEnergy; // 0-100
    private int explosiveCharges; // 0-3
    private boolean explosiveModeActive;

    public PlayerSignatureState() {
        this.signatureEnergy = 0;
        this.explosiveCharges = 0;
        this.explosiveModeActive = false;
    }

    public int getSignatureEnergy() {
        return signatureEnergy;
    }

    public void addSignatureEnergy(int amount) {
        this.signatureEnergy = Math.min(100, this.signatureEnergy + amount);
    }

    public void resetSignatureEnergy() {
        this.signatureEnergy = 0;
    }

    public boolean isEnergyFull() {
        return this.signatureEnergy >= 100;
    }

    public int getExplosiveCharges() {
        return explosiveCharges;
    }

    public void setExplosiveCharges(int charges) {
        this.explosiveCharges = charges;
    }

    public void decrementCharges() {
        if (this.explosiveCharges > 0) {
            this.explosiveCharges--;
        }
        if (this.explosiveCharges == 0) {
            this.explosiveModeActive = false;
        }
    }

    public boolean isExplosiveModeActive() {
        return explosiveModeActive;
    }

    public void activateExplosiveMode() {
        this.explosiveModeActive = true;
        this.explosiveCharges = 3;
        this.signatureEnergy = 0;
    }

    public void deactivateExplosiveMode() {
        this.explosiveModeActive = false;
        this.explosiveCharges = 0;
    }
}
