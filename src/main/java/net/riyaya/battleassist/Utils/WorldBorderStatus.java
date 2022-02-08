package net.riyaya.battleassist.Utils;

public class WorldBorderStatus {

    private int shrinkageStartSeconds;
    private int waitSeconds;
    private int size;

    public int getSize() {
        return size;
    }

    public WorldBorderStatus setSize(int size) {
        this.size = size;
        return this;
    }

    public int getShrinkageStartSeconds() {
        return shrinkageStartSeconds;
    }

    public WorldBorderStatus setShrinkageStartSeconds(int shrinkageStartSeconds) {
        this.shrinkageStartSeconds = shrinkageStartSeconds;
        return this;
    }

    public int getWaitSeconds() {
        return waitSeconds;
    }

    public WorldBorderStatus setWaitSeconds(int waitSeconds) {
        this.waitSeconds = waitSeconds;
        return this;
    }
}
