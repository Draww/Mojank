package cc.funkemunky.fixer.api.data;

import cc.funkemunky.fixer.api.utils.BoundingBox;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

@Getter
@Setter
public class PlayerData {

    public Player player;
    public double fallDistance, lastFallDistance;
    public int packetsReceived, swings, heldItems, chatVerbose;
    public long flyingPacketsInSecond = 0, armSwingsInSecond = 0, heldItemsInSecond = 0, lastChat;
    private BoundingBox boundingBox;
    private boolean onGround, onGroundBefore, inLiquid;

    public PlayerData(Player player) {
        this.player = player;
    }
}
