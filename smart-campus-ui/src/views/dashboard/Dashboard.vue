<template>
  <div class="dashboard-container">
    <!-- 顶部数据卡片 (保持不变) -->
    <el-row :gutter="20">
      <el-col :span="6" v-for="(item, index) in stats" :key="index">
        <div class="tech-card stat-card">
          <div class="card-glow" :style="{ background: item.color }"></div>
          <div class="stat-icon-box" :style="{ borderColor: item.color, boxShadow: `0 0 10px ${item.color}` }">
            <el-icon :color="item.color"><component :is="item.icon" /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value neon-text">{{ item.value }}</div>
            <div class="stat-label">{{ item.label }}</div>
          </div>
        </div>
      </el-col>
    </el-row>

    <!-- 核心图表区域 -->
    <div class="tech-card chart-section">
      <div class="section-header">
        <div class="header-title">
          <el-icon color="#00f3ff"><TrendCharts /></el-icon>
          <span class="title-text">REAL-TIME ENERGY FLUX // 实时能耗波动监控</span>
        </div>

        <div class="header-controls">
          <!-- 简单展示当前监控的是哪个设备 -->
          <span class="monitor-target" v-if="currentDeviceName">
             TARGET: {{ currentDeviceName }}
           </span>
          <div class="live-tag">LIVE</div>
        </div>
      </div>

      <div class="chart-box">
        <!-- 引入全息图表组件 -->
        <!-- 只有当 deviceId 拿到后才渲染，防止报错 -->
        <EnergyTrendChart v-if="currentDeviceId" :device-id="currentDeviceId" />

        <!-- 如果还在加载，显示文字 -->
        <div v-else class="loading-text">正在建立数据链路...</div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import EnergyTrendChart from '../../components/EnergyTrendChart.vue' // 引入组件

// 统计数据 (模拟)
const stats = ref([
  { label: '今日总能耗 (kWh)', value: '428.5', icon: 'Lightning', color: '#00f3ff' },
  { label: '在线设备数', value: '10 / 10', icon: 'Cpu', color: '#10b981' },
  { label: '今日告警数', value: '03', icon: 'Warning', color: '#ff0055' },
  { label: '覆盖区域', value: '04', icon: 'OfficeBuilding', color: '#bc13fe' }
])

const currentDeviceId = ref(null)
const currentDeviceName = ref('')

// 获取第一个有效设备，用于展示图表
const initDashboardData = async () => {
  try {
    const res = await axios.get('/api/devices')
    if (res.data && res.data.length > 0) {
      // 默认选中第一个设备进行监控
      const firstDevice = res.data[0]
      currentDeviceId.value = firstDevice.id
      currentDeviceName.value = firstDevice.name
    }
  } catch (e) {
    console.error('无法加载设备列表', e)
  }
}

onMounted(() => {
  initDashboardData()
})
</script>

<style scoped>
.dashboard-container {
  display: flex;
  flex-direction: column;
  gap: 24px;
}

/* 统计卡片样式 (复用之前的) */
.stat-card {
  display: flex;
  align-items: center;
  position: relative;
  transition: transform 0.3s;
}

.stat-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.5);
}

.card-glow {
  position: absolute;
  right: -20px;
  top: -20px;
  width: 100px;
  height: 100px;
  filter: blur(50px);
  opacity: 0.2;
  border-radius: 50%;
}

.stat-icon-box {
  width: 48px;
  height: 48px;
  border: 1px solid;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 16px;
  font-size: 24px;
  background: rgba(0,0,0,0.3);
}

.stat-value {
  font-family: 'Courier New', monospace;
  font-size: 28px;
  font-weight: bold;
  color: #fff;
  text-shadow: 0 0 10px rgba(255, 255, 255, 0.3);
  letter-spacing: -1px;
}

.stat-label {
  font-size: 12px;
  color: #94a3b8;
  letter-spacing: 1px;
  margin-top: 4px;
}

/* 图表区 */
.chart-section {
  min-height: 500px;
  display: flex;
  flex-direction: column;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid #1e3a8a;
  padding-bottom: 15px;
  margin-bottom: 20px;
}

.header-title {
  display: flex;
  align-items: center;
  gap: 10px;
}

.title-text {
  font-family: monospace;
  color: #00f3ff;
  font-size: 16px;
  letter-spacing: 1px;
}

.header-controls {
  display: flex;
  align-items: center;
  gap: 15px;
}

.monitor-target {
  font-family: monospace;
  color: #94a3b8;
  font-size: 12px;
  border: 1px solid #1e3a8a;
  padding: 2px 8px;
  border-radius: 4px;
}

.live-tag {
  background: #ff0055;
  color: white;
  font-size: 10px;
  padding: 2px 8px;
  border-radius: 2px;
  font-weight: bold;
  box-shadow: 0 0 10px #ff0055;
  animation: flash 1s infinite alternate;
}

@keyframes flash {
  from { opacity: 1; }
  to { opacity: 0.5; }
}

.chart-box {
  flex: 1;
  position: relative;
  min-height: 400px;
}

.loading-text {
  color: #00f3ff;
  font-family: monospace;
  text-align: center;
  margin-top: 100px;
  animation: pulse 1s infinite;
}
</style>