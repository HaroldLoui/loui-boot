/**
 * 系统配置查询参数
 */
export interface SysConfigQuery extends PageQuery {
  /**
   * 关键字（参数名称）
   */
  keywords?: string;
}

/**
 * 系统配置分页对象
 */
export interface SysConfigPageVO {
  /**
   * 参数主键
   */
  id: number | string;
  /**
   * 参数名称
   */
  configName: string;
  /**
   * 参数键名
   */
  configKey: string;
  /**
   * 参数键值
   */
  configValue: string;
  /**
   * 系统内置（Y是 N否）
   */
  configType: string;
  /**
   * 创建时间
   */
  createTime?: Date;
  /**
   * 修改时间
   */
  updateTime?: Date;
  /**
   * 备注
   */
  remark?: string;
}

/**
 * 字典分页项类型声明
 */
export type SysConfigPageResult = PageResult<SysConfigPageVO[]>;

/**
 * 系统配置类型声明
 */
export interface SysConfigForm {
  /**
   * 参数主键
   */
  id?: number | string;
  /**
   * 参数名称
   */
  configName: string;
  /**
   * 参数键名
   */
  configKey: string;
  /**
   * 参数键值
   */
  configValue: string;
  /**
   * 系统内置（Y是 N否）
   */
  configType: string;
  /**
   * 创建时间
   */
  createTime?: Date;
  /**
   * 修改时间
   */
  updateTime?: Date;
  /**
   * 备注
   */
  remark?: string;
}
