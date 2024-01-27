package top.loui.admin.common;

import top.loui.admin.utils.JsonUtils;

public abstract class BaseController {

    protected static final String QUERY_SUCCESS = "查询成功";
    protected static final String QUERY_FAILED = "查询失败";

    protected static final String INSERT_SUCCESS = "新增成功";
    protected static final String INSERT_FAILED = "新增失败";

    protected static final String UPDATE_SUCCESS = "修改成功";
    protected static final String UPDATE_FAILED = "修改失败";

    protected static final String DELETE_SUCCESS = "删除成功";
    protected static final String DELETE_FAILED = "删除失败";

    protected String ok() {
        return JsonUtils.toJsonString(Result.ok());
    }

    protected String ok(String msg) {
        return JsonUtils.toJsonString(Result.ok(msg));
    }

    protected <T> String ok(String msg, T data) {
        return JsonUtils.toJsonString(Result.ok(msg, data));
    }

    protected <T> String data(T data) {
        return JsonUtils.toJsonString(Result.ok(Result.SUCCESS_MESSAGE, data));
    }

    protected String fail() {
        return JsonUtils.toJsonString(Result.fail());
    }

    protected String fail(String msg) {
        return JsonUtils.toJsonString(Result.fail(msg));
    }

    protected <T> String fail(String msg, T data) {
        return JsonUtils.toJsonString(Result.fail(msg, data));
    }
}
