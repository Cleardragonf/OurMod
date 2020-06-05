package com.cleardragonf.ourmod.MCM;

public class MCMValueCapability implements IMCMValueCapability{

    private static int mcmValue = 10000;

    static int minValue= 0;

    public void setMCMValue(int setValue) {
        this.setPlayerTemp2(this.getMCMValue() + setValue);
        if(getMCMValue() < minValue) {
            this.setPlayerTemp2(-100);
        }
    }


    @Override
    public int mcmValue() {
        return this.getMCMValue();
    }

    @Override
    public int minValue() {
        return this.minValue;
    }



    public static int getMCMValue() {
        return mcmValue;
    }


    public void setPlayerTemp2(int mcmValue) {
        MCMValueCapability.mcmValue = mcmValue;
    }

}
