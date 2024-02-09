package top.loui.admin.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.loui.admin.common.BaseController;
import top.loui.admin.domain.vo.SysFileVo;

/**
 * 文件接口
 */
@SaCheckLogin
@RequiredArgsConstructor
@RequestMapping("/api/v1/files")
@RestController
public class SysFileController extends BaseController {

    /**
     * 文件上传
     *
     * @param file 表单文件对象
     */
    @PostMapping
    public String upload(@RequestPart MultipartFile file) {
        SysFileVo fileVo = new SysFileVo();
        return ok("上传成功", fileVo);
    }

    /**
     * 文件删除
     *
     * @param filePath 文件路径
     */
    @DeleteMapping
    public String delete(@RequestParam String filePath) {
        return ok(DELETE_SUCCESS);
    }
}
