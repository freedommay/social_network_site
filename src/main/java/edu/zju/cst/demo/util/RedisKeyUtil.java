package edu.zju.cst.demo.util;

public class RedisKeyUtil {
    private static String SPLIT = ":";
    private static String BIZ_LIKE = "LIKE";
    private static String BIZ_DISLIKE = "DISLIKE";
    private static String BIZ_EVENTQUEUE = "EVENT_QUEUE";
    private static String BIZ_FOLLOWER = "FOLLOWER"; // 某实体对应的关注者
    private static String BIZ_FOLLOWEE = "FOLLOWEE"; // 某用户所关注的内容

    public static String getLikeKey(int entityType, int entityID) {
        return BIZ_LIKE + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityID);
    }

    public static String getDisLikeKey(int entityType, int entityID) {
        return BIZ_DISLIKE + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityID);
    }

    public static String getEventQueueKey() {
        return BIZ_EVENTQUEUE;
    }

    public static String getFollowerKey(int entityType, int entityID) {
        return BIZ_FOLLOWER + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityID);
    }

    public static String getFolloweeKey(int userID, int entityType) {
        return BIZ_FOLLOWEE + SPLIT + String.valueOf(userID) + SPLIT + String.valueOf(entityType);
    }
}
