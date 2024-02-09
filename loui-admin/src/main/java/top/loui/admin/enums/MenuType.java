package top.loui.admin.enums;

import io.github.linpeilie.annotations.AutoEnumMapper;
import lombok.Getter;

@Getter
@AutoEnumMapper("value")
public enum MenuType {
    BUTTON(4),
    CATALOG(2),
    EXTLINK(3),
    MENU(1),
    NULL(0);

    private final Integer value;

    MenuType(Integer value) {
        this.value = value;
    }

    public static MenuType getType(Integer type) {
        return switch (type) {
            case 1 -> MenuType.MENU;
            case 2 -> MenuType.CATALOG;
            case 3 -> MenuType.EXTLINK;
            case 4 -> MenuType.BUTTON;
            default -> MenuType.NULL;
        };
    }
}