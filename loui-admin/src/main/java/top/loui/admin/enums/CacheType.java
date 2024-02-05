package top.loui.admin.enums;

/**
 * 缓存类型
 */
public enum CacheType {

    /**
     * 不启用缓存功能
     */
    NONE,

    /**
     * 开启本地缓存（caffeine）
     */
    LOCAL,

    /**
     * 开启远程缓存（redis）
     */
    REMOTE,

    /**
     * 开启二级缓存
     */
    BOTH
    ;
}
