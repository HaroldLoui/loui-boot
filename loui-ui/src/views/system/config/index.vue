<template>
  <div class="app-container">
    <div class="search-container">
      <el-form ref="queryFormRef" :model="queryParams" :inline="true">
        <el-form-item label="关键字" prop="name">
          <el-input
            v-model="queryParams.keywords"
            placeholder="参数名称"
            clearable
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleQuery()"
            ><i-ep-search />搜索</el-button
          >
          <el-button @click="resetQuery()"><i-ep-refresh />重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <el-card shadow="never" class="table-container">
      <template #header>
        <el-button
          v-hasPerm="['sys:config:add']"
          type="success"
          @click="openDialog()"
          ><i-ep-plus />新增</el-button
        >
        <el-button
          type="danger"
          :disabled="ids.length === 0"
          @click="handleDelete()"
          ><i-ep-delete />删除</el-button
        >
      </template>
      <el-table
        v-loading="loading"
        highlight-current-row
        :data="configList"
        border
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column
          label="参数名称"
          prop="configName"
          width="200"
          align="center"
        />
        <el-table-column
          label="参数键名"
          prop="configKey"
          width="200"
          align="center"
        />
        <el-table-column
          label="参数键值"
          prop="configValue"
          width="200"
          align="center"
        />
        <el-table-column label="系统内置" align="center" width="150">
          <template #default="scope">
            <el-tag v-if="scope.row.configType === 'Y'" type="success"
              >是</el-tag
            >
            <el-tag v-else type="info">否</el-tag>
          </template>
        </el-table-column>
        <el-table-column
          label="创建时间"
          prop="createTime"
          width="200"
          align="center"
        />
        <el-table-column
          label="更新时间"
          prop="updateTime"
          width="200"
          align="center"
        />
        <el-table-column label="备注" prop="remark" align="center" />
        <el-table-column fixed="right" label="操作" align="center" width="220">
          <template #default="scope">
            <el-button
              v-hasPerm="['sys:config:edit']"
              type="primary"
              link
              size="small"
              @click.stop="openDialog(scope.row.id)"
              ><i-ep-edit />编辑</el-button
            >
            <el-button
              v-hasPerm="['sys:config:delete']"
              type="danger"
              link
              size="small"
              @click.stop="handleDelete(scope.row.id)"
              ><i-ep-delete />删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>

      <pagination
        v-if="total > 0"
        v-model:total="total"
        v-model:page="queryParams.pageNum"
        v-model:limit="queryParams.pageSize"
        @pagination="handleQuery"
      />
    </el-card>

    <el-dialog
      v-model="dialog.visible"
      :title="dialog.title"
      width="500px"
      @close="closeDialog"
    >
      <el-form
        ref="dataFormRef"
        :model="formData"
        :rules="rules"
        label-width="80px"
      >
        <el-form-item label="参数名称" prop="configName">
          <el-input
            v-model="formData.configName"
            placeholder="请输入参数名称"
          />
        </el-form-item>
        <el-form-item label="参数键名" prop="configKey">
          <el-input v-model="formData.configKey" placeholder="请输入参数键名" />
        </el-form-item>
        <el-form-item label="参数键值" prop="configValue">
          <el-input
            v-model="formData.configValue"
            placeholder="请输入参数键值"
          />
        </el-form-item>
        <el-form-item label="系统内置" prop="configType">
          <el-radio-group v-model="formData.configType">
            <el-radio label="Y">是</el-radio>
            <el-radio label="N">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input
            v-model="formData.remark"
            type="textarea"
            placeholder="请输入备注"
            :autosize="{ minRows: 2, maxRows: 4 }"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="handleSubmit">确 定</el-button>
          <el-button @click="closeDialog">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import {
  SysConfigForm,
  SysConfigPageVO,
  SysConfigQuery,
} from "@/api/config/types";
import {
  getSysConfigPage,
  getSysConfigForm,
  updateSysConfig,
  addSysConfig,
  deleteSysConfigs,
} from "@/api/config";

const queryFormRef = ref(ElForm);
const dataFormRef = ref(ElForm);

const loading = ref(false);
const ids = ref<string[]>([]);
const total = ref(0);

const queryParams = reactive<SysConfigQuery>({
  pageNum: 1,
  pageSize: 10,
});

const configList = ref<SysConfigPageVO[]>();

const formData = reactive<SysConfigForm>({
  configName: "",
  configKey: "",
  configValue: "",
  configType: "N",
});

const rules = reactive({
  configName: [{ required: true, message: "请输入参数名称", trigger: "blur" }],
  configKey: [{ required: true, message: "请输入参数键名", trigger: "blur" }],
  configValue: [{ required: true, message: "请输入参数键值", trigger: "blur" }],
});

const dialog = reactive({
  title: "",
  visible: false,
});

onMounted(() => {
  handleQuery();
});

/** 查询 */
function handleQuery() {
  loading.value = true;
  getSysConfigPage(queryParams)
    .then(({ data }) => {
      configList.value = data.list;
      total.value = data.total;
    })
    .finally(() => {
      loading.value = false;
    });
}

/**
 * 重置查询
 */
function resetQuery() {
  queryFormRef.value.resetFields();
  queryParams.pageNum = 1;
  handleQuery();
}

/** 行复选框选中  */
function handleSelectionChange(selection: any) {
  ids.value = selection.map((item: any) => item.id);
}

/**
 * 打开系统配置表单弹窗
 *
 * @param configId 系统配置ID
 */
function openDialog(configId?: number | string) {
  dialog.visible = true;
  if (configId) {
    dialog.title = "修改系统配置";
    getSysConfigForm(configId).then(({ data }) => {
      Object.assign(formData, data);
    });
  } else {
    dialog.title = "新增系统配置";
  }
}

/** 系统配置表单提交 */
function handleSubmit() {
  dataFormRef.value.validate((isValid: boolean) => {
    if (isValid) {
      loading.value = false;
      const configId = formData.id;
      if (configId) {
        updateSysConfig(configId, formData)
          .then(() => {
            ElMessage.success("修改成功");
            closeDialog();
            handleQuery();
          })
          .finally(() => (loading.value = false));
      } else {
        addSysConfig(formData)
          .then(() => {
            ElMessage.success("新增成功");
            closeDialog();
            handleQuery();
          })
          .finally(() => (loading.value = false));
      }
    }
  });
}

/** 关闭系统配置弹窗 */
function closeDialog() {
  dialog.visible = false;
  resetForm();
}

/**  重置系统配置表单 */
function resetForm() {
  dataFormRef.value.resetFields();
  dataFormRef.value.clearValidate();

  formData.id = undefined;
  formData.configType = "N";
}

/** 删除系统配置 */
function handleDelete(configId?: number) {
  const configIds = [configId || ids.value].join(",");
  if (!configIds) {
    ElMessage.warning("请勾选删除项");
    return;
  }

  ElMessageBox.confirm("确认删除已选中的数据项?", "警告", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  }).then(() => {
    deleteSysConfigs(configIds).then(() => {
      ElMessage.success("删除成功");
      resetQuery();
    });
  });
}
</script>

<style lang="scss" scoped></style>
