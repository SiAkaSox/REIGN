package io.ruin.services.referrals;

import com.google.common.collect.Lists;

import java.util.List;

public class Referral {

    public String code;

    public int maxUses;

    public int remainingUses;

    public int rewardId;

    public List<String> redeemUsers;

    public long createdAt;

    public Referral(String code, int maxUses, int rewardId) {
        this.code = code;
        this.maxUses = maxUses;
        this.rewardId = rewardId;
        this.redeemUsers = Lists.newCopyOnWriteArrayList();
        this.remainingUses = maxUses;
        this.createdAt = System.currentTimeMillis();
    }
}
