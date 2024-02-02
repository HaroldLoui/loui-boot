package top.loui.admin.enums;

import lombok.Getter;

@Getter
public enum Gender {

    UNKNOWN("未知", 0),
    MALE("男", 1),
    FEMALE("女", 2),
    ;

    private final String label;
    private final Integer value;

    Gender(String label, Integer value) {
        this.label = label;
        this.value = value;
    }

    public static Integer getValueByLabel(String label) {
        return switch (label) {
            case "男" -> 1;
            case "女" -> 2;
            default -> 0;
        };
    }

    public static String getLabelByValue(Integer value) {
        return switch (value) {
            case 1 -> "男";
            case 2 -> "女";
            default -> "未知";
        };
    }
}
