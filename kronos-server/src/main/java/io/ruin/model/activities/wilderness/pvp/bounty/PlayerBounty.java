package io.ruin.model.activities.wilderness.pvp.bounty;

import com.google.gson.annotations.Expose;

public class PlayerBounty {

    @Expose public BountySkull bountySkull;

    public PlayerBounty(BountySkull bountySkull) {
        this.bountySkull = bountySkull;
    }


}
