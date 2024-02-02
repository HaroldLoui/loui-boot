import request from "@/utils/request";
import { AxiosPromise } from "axios";
import { SysConfigQuery, SysConfigPageResult, SysConfigForm } from "./types";

/**
 * 系统配置分页列表
 *
 * @param queryParams
 */
export function getSysConfigPage(
  queryParams: SysConfigQuery
): AxiosPromise<SysConfigPageResult> {
  return request({
    url: "/api/v1/config/page",
    method: "get",
    params: queryParams,
  });
}

/**
 * 系统配置表单数据
 *
 * @param id
 */
export function getSysConfigForm(
  id: number | string
): AxiosPromise<SysConfigForm> {
  return request({
    url: "/api/v1/config/" + id + "/form",
    method: "get",
  });
}

/**
 * 新增系统配置
 *
 * @param data
 */
export function addSysConfig(data: SysConfigForm) {
  return request({
    url: "/api/v1/config",
    method: "post",
    data: data,
  });
}

/**
 * 修改系统配置
 *
 * @param id
 * @param data
 */
export function updateSysConfig(id: number | string, data: SysConfigForm) {
  return request({
    url: "/api/v1/config/" + id,
    method: "put",
    data: data,
  });
}

/**
 * 删除系统配置
 */
export function deleteSysConfigs(ids: string) {
  return request({
    url: "/api/v1/config/" + ids,
    method: "delete",
  });
}
