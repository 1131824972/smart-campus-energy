<template>
  <div class="device-list-container">
    <!-- 顶部操作栏 -->
    <div class="tech-card action-bar">
      <div class="bar-left">
        <h3 class="page-title">
          <el-icon><Cpu /></el-icon> 接入设备列表
        </h3>
        <span class="sub-title">共监控 {{ deviceList.length }} 台智能电表</span>
      </div>
      <div class="bar-right">
        <el-button type="primary" :icon="Refresh" @click="fetchDevices" :loading="loading" class="neon-btn">
          刷新数据
        </el-button>
      </div>
    </div>

    <!-- 设备数据表格 -->
    <div class="tech-card table-wrapper">
      <el-table
          :data="deviceList"
          style="width: 100%"
          v-loading="loading"
          element-loading-background="rgba(0, 0, 0, 0.7)"
          element-loading-text="正在同步设备状态..."
          :header-cell-style="headerStyle"
          :row-style="rowStyle"
          :cell-style="cellStyle"
      >
        <el-table-column prop="id" label="ID" width="60" />

        <el-table-column prop="name" label="设备名称" min-width="150">
          <template #default="scope">
            <span style="color: #fff; font-weight: bold">{{ scope.row.name }}</span>
          </template>
        </el-table-column>

        <el-table-column prop="sn" label="序列号 (SN)" min-width="160">
          <template #default="scope">
            <span style="font-family: monospace; color: #00f3ff">{{ scope.row.sn }}</span>
          </template>
        </el-table-column>

        <el-table-column prop="buildingName" label="所属建筑" min-width="120" />

        <el-table-column prop="roomNumber" label="房间号" width="100">
          <template #default="scope">
            <el-tag effect="dark" type="info" size="small">{{ scope.row.roomNumber }}</el-tag>
          </template>
        </el-table-column>

        <el-table-column prop="maxPower" label="额定功率" width="120">
          <template #default="scope">
            <span style="color: #f59e0b">{{ scope.row.maxPower }} W</span>
          </template>
        </el-table-column>

        <el-table-column prop="status" label="通讯状态" width="120">
          <template #default="scope">
            <div class="status-cell">
              <!-- 根据状态显示不同颜色的点 -->
              <span class="status-dot" :class="scope.row.status === 'ONLINE' ? 'online' : 'offline'"></span>
              <span>{{ scope.row.status === 'ONLINE' ? '在线' : '离线' }}</span>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="操作" width="150" fixed="right">
          <template #default>
            <el-button link type="primary" size="small" class="action-btn">查看详情</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { Refresh } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

const deviceList = ref([])
const loading = ref(false)

// 样式配置函数：为了覆盖 Element Plus 默认的大白底色，手动注入样式
const headerStyle = {
  backgroundColor: 'rgba(30, 58, 138, 0.4)',
  color: '#00f3ff',
  borderColor: '#1e3a8a',
  fontWeight: 'bold'
}

const rowStyle = {
  backgroundColor: 'transparent',
  color: '#e2e8f0'
}

const cellStyle = {
  borderColor: 'rgba(30, 58, 138, 0.3)'
}

// 核心逻辑：获取后端数据
const fetchDevices = async () => {
  loading.value = true
  try {
    // 这里的 /api 会被 vite.config.js 代理转发到 localhost:8081
    const res = await axios.get('/api/devices')
    if (res.status === 200) {
      deviceList.value = res.data
      ElMessage.success({
        message: `成功同步 ${res.data.length} 台设备数据`,
        type: 'success',
        offset: 100 // 往下挪一点，别遮住顶部
      })
    }
  } catch (error) {
    console.error(error)
    ElMessage.error('无法连接至设备控制中心，请检查后端服务！')
  } finally {
    loading.value = false
  }
}

// 页面加载时自动获取
onMounted(() => {
  fetchDevices()
})
</script>

<style scoped>
.device-list-container {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.action-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px 24px;
}

.page-title {
  margin: 0;
  color: #fff;
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 18px;
}

.sub-title {
  color: #64748b;
  font-size: 12px;
  margin-left: 10px;
  margin-top: 4px;
  display: block;
}

/* 霓虹按钮 */
.neon-btn {
  background: rgba(0, 243, 255, 0.1);
  border: 1px solid #00f3ff;
  color: #00f3ff;
  transition: all 0.3s;
}

.neon-btn:hover {
  background: #00f3ff;
  color: #000;
  box-shadow: 0 0 15px #00f3ff;
}

/* 表格覆盖样式 */
.table-wrapper {
  padding: 0; /* 让表格贴边 */
  overflow: hidden;
}

:deep(.el-table) {
  /* 强制背景透明 */
  background-color: transparent !important;
  --el-table-border-color: rgba(30, 58, 138, 0.3);
  --el-table-bg-color: transparent;
  --el-table-tr-bg-color: transparent;
  --el-table-header-bg-color: transparent;
  --el-table-row-hover-bg-color: rgba(0, 243, 255, 0.05); /* 鼠标悬停高亮 */
}

/* 去掉表格底部的白线 */
:deep(.el-table__inner-wrapper::before) {
  display: none;
}

.status-cell {
  display: flex;
  align-items: center;
  gap: 6px;
}

.status-dot {
  width: 6px;
  height: 6px;
  border-radius: 50%;
  box-shadow: 0 0 5px currentColor;
}

.online { background-color: #10b981; color: #10b981; }
.offline { background-color: #64748b; color: #64748b; }

.action-btn {
  color: #00f3ff;
}
.action-btn:hover {
  color: #fff;
  text-shadow: 0 0 5px #00f3ff;
}
</style>