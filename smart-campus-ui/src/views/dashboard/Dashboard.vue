<template>
  <div class="dashboard-container">
    <!-- 顶部数据卡片 -->
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

    <!-- 模拟图表区 -->
    <div class="tech-card chart-section">
      <div class="section-header">
        <div class="header-title">
          <el-icon color="#00f3ff"><TrendCharts /></el-icon>
          <span class="title-text">REAL-TIME ENERGY FLUX // 实时能耗波动</span>
        </div>
        <div class="live-tag">LIVE DATA</div>
      </div>

      <div class="chart-box">
        <!-- 只是一个占位，后面我们会用 ECharts 填满这里 -->
        <div class="grid-lines"></div>
        <div class="loading-scan">Initializing Visual Interface...</div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const stats = ref([
  { label: 'TOTAL ENERGY (kWh)', value: '428.5', icon: 'Lightning', color: '#00f3ff' }, // 青色
  { label: 'ONLINE DEVICES', value: '10 / 10', icon: 'Cpu', color: '#10b981' },         // 绿色
  { label: 'SYSTEM ALERTS', value: '03', icon: 'Warning', color: '#ff0055' },           // 红色
  { label: 'COVERAGE ZONES', value: '04', icon: 'OfficeBuilding', color: '#bc13fe' }    // 紫色
])
</script>

<style scoped>
.dashboard-container {
  display: flex;
  flex-direction: column;
  gap: 24px;
}

/* 统计卡片特殊效果 */
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
  border-radius: 8px; /* 稍微圆一点的角 */
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 16px;
  font-size: 24px;
  background: rgba(0,0,0,0.3);
}

.stat-value {
  font-family: 'Courier New', monospace; /* 机械字体 */
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
  background: rgba(0, 0, 0, 0.2);
  border: 1px dashed #1e3a8a;
  border-radius: 4px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #1e3a8a;
  font-family: monospace;
  position: relative;
  overflow: hidden;
}

/* 装饰性网格线 */
.grid-lines {
  position: absolute;
  top: 0; left: 0; width: 100%; height: 100%;
  background-image:
      linear-gradient(rgba(0, 243, 255, 0.05) 1px, transparent 1px),
      linear-gradient(90deg, rgba(0, 243, 255, 0.05) 1px, transparent 1px);
  background-size: 40px 40px;
}
</style>