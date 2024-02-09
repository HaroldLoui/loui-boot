package top.loui.admin.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import io.github.linpeilie.annotations.AutoMapping;
import lombok.Data;
import top.loui.admin.domain.SysUser;

/**
 * 用户导入对象
 *
 * @author haoxr
 * @since 2022/4/10
 */
@AutoMapper(target = SysUser.class, reverseConvertGenerate = false)
@Data
public class UserImportVO {

    @ExcelProperty(value = "用户名")
    private String username;

    @ExcelProperty(value = "昵称")
    private String nickname;

    @AutoMapping(target = "gender", expression = "java(top.loui.admin.enums.Gender.getValueByLabel(source.getGenderLabel()))")
    @ExcelProperty(value = "性别")
    private String genderLabel;

    @ExcelProperty(value = "手机号码")
    private String mobile;

    @ExcelProperty(value = "邮箱")
    private String email;

    @ExcelProperty("角色")
    private String roleCodes;

}