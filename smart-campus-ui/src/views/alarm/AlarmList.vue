<template>
  <div class="alarm-list-container">
    <!-- 顶部统计与搜索栏 -->
    <div class="tech-card control-panel">
      <div class="panel-left">
        <h3 class="page-title">
          <el-icon color="#ff0055"><Warning /></el-icon> 异常入侵与告警记录
        </h3>
      </div>

      <div class="panel-right">
        <!-- 搜索框：前端过滤 -->
        <el-input
            v-model="searchQuery"
            placeholder="输入设备序列号 (SN) 检索..."
            prefix-icon="Search"
            class="tech-search"
            clearable
        />
        <el-button type="danger" plain @click="fetchAlarms" :loading="loading" class="refresh-btn">
          <el-icon><Refresh /></el-icon> 同步告警日志
        </el-button>
      </div>
    </div>

    <!-- 告警数据表格 -->
    <div class="tech-card table-wrapper">
      <el-table
          :data="filteredAlarms"
          style="width: 100%"
          v-loading="loading"
          element-loading-background="rgba(0, 0, 0, 0.8)"
          element-loading-text="正在分析系统日志..."
          :header-cell-style="headerStyle"
          :row-style="rowStyle"
          :cell-style="cellStyle"
          height="600"
      >
        <!-- 序号 -->
        <el-table-column type="index" label="#" width="60" />

        <!-- 发生时间 -->
        <el-table-column prop="createTime" label="告警时间" width="200">
          <template #default="scope">
            <span class="time-text">{{ scope.row.createTime }}</span>
          </template>
        </el-table-column>

        <!-- 告警设备 -->
        <el-table-column prop="deviceName" label="告警源 (设备)" width="180">
          <template #default="scope">
            <div class="device-info">
              <span class="device-name">{{ scope.row.deviceName }}</span>
              <span class="device-sn">{{ scope.row.deviceSn }}</span>
            </div>
          </template>
        </el-table-column>

        <!-- 告警类型 (标签展示) -->
        <el-table-column prop="type" label="异常类型" width="150">
          <template #default="scope">
            <el-tag
                :type="getAlarmTagType(scope.row.type)"
                effect="dark"
                class="alarm-tag"
            >
              {{ scope.row.type }}
            </el-tag>
          </template>
        </el-table-column>

        <!-- 异常数值 -->
        <el-table-column prop="value" label="触发数值" width="150">
          <template #default="scope">
            <span class="value-text">{{ scope.row.value }}</span>
          </template>
        </el-table-column>

        <!-- 详细描述 -->
        <el-table-column prop="details" label="系统日志详情" min-width="250">
          <template #default="scope">
            <span class="detail-text">{{ scope.row.details }}</span>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import axios from 'axios'
import { Search, Refresh, Warning } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

const allAlarms = ref([]) // 存储接口返回的原始数据
const loading = ref(false)
const searchQuery = ref('') // 搜索关键词

// 表格样式配置 (深色红调)
const headerStyle = {
  backgroundColor: 'rgba(255, 0, 85, 0.1)',
  color: '#ff0055',
  borderColor: '#ff0055',
  fontWeight: 'bold',
  fontFamily: 'Microsoft YaHei'
}

const rowStyle = {
  backgroundColor: 'transparent',
  color: '#e2e8f0'
}

const cellStyle = {
  borderColor: 'rgba(255, 0, 85, 0.2)'
}

// 计算属性：实现前端搜索过滤
const filteredAlarms = computed(() => {
  if (!searchQuery.value) {
    return allAlarms.value
  }
  const query = searchQuery.value.toLowerCase()
  return allAlarms.value.filter(item =>
      (item.deviceSn && item.deviceSn.toLowerCase().includes(query)) ||
      (item.deviceName && item.deviceName.includes(query))
  )
})

// 获取告警数据
const fetchAlarms = async () => {
  loading.value = true
  try {
    const res = await axios.get('/api/alarms')
    if (res.status === 200) {
      allAlarms.value = res.data
      ElMessage({
        message: `检测到 ${res.data.length} 条历史告警记录`,
        type: 'warning', // 黄色提示框
        customClass: 'cyber-message',
        offset: 60
      })
    }
  } catch (error) {
    console.error(error)
    ElMessage.error('无法连接至日志中心')
  } finally {
    loading.value = false
  }
}

// 辅助函数：根据告警类型返回不同的 Tag 颜色
const getAlarmTagType = (type) => {
  if (type.includes('过载')) return 'danger' // 红色
  if (type.includes('电压')) return 'warning' // 黄色
  return 'info'
}

onMounted(() => {
  fetchAlarms()
})
</script>

<style scoped>
.alarm-list-container {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.control-panel {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px 24px;
  border-color: #ff0055; /* 红色边框强调告警 */
  box-shadow: 0 0 10px rgba(255, 0, 85, 0.1);
}

.control-panel::before {
  background: linear-gradient(90deg, #ff0055, transparent); /* 红色发光条 */
}

.page-title {
  margin: 0;
  color: #fff;
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 18px;
  text-shadow: 0 0 5px rgba(255, 0, 85, 0.5);
}

.panel-right {
  display: flex;
  gap: 15px;
}

/* 赛博风格搜索框 */
:deep(.tech-search .el-input__wrapper) {
  background-color: rgba(0, 0, 0, 0.5);
  box-shadow: 0 0 0 1px #ff0055 inset;
  border-radius: 4px;
}

:deep(.tech-search .el-input__inner) {
  color: #fff;
}

.refresh-btn {
  background: rgba(255, 0, 85, 0.1);
  border: 1px solid #ff0055;
  color: #ff0055;
}
.refresh-btn:hover {
  background: #ff0055;
  color: #fff;
  box-shadow: 0 0 10px #ff0055;
}

/* 表格内容微调 */
.time-text {
  font-family: 'Courier New', monospace;
  color: #ff0055;
}

.device-info {
  display: flex;
  flex-direction: column;
}

.device-name {
  font-weight: bold;
  color: #fff;
}

.device-sn {
  font-size: 12px;
  color: #64748b;
  font-family: monospace;
}

.value-text {
  font-family: monospace;
  font-weight: bold;
  font-size: 16px;
  color: #e2e8f0;
}

.detail-text {
  color: #94a3b8;
  font-size: 13px;
}

.alarm-tag {
  border: none;
  font-weight: bold;
}

/* 修改表格 hover 颜色为微红 */
:deep(.el-table__body tr:hover > td) {
  background-color: rgba(255, 0, 85, 0.05) !important;
}
</style>