package net.riyaya.battleassist.Utils;

import net.riyaya.battleassist.BattleAssist;

public class GameStatus {

    private boolean status;
    private int time;

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return status;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public void resetTime() {
        time = BattleAssist.config.getRemoveElytraSeconds()
                        + BattleAssist.config.getStartBorderShrinkageSeconds()
                        + BattleAssist.config.getFirstBorder().getShrinkageStartSeconds()
                        + BattleAssist.config.getFirstBorder().getWaitSeconds()
                        + BattleAssist.config.getSecondBorder().getShrinkageStartSeconds()
                        + BattleAssist.config.getSecondBorder().getWaitSeconds()
                        + BattleAssist.config.getThirdBorder().getShrinkageStartSeconds()
                        + BattleAssist.config.getThirdBorder().getWaitSeconds();
    }
}
