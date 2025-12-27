<template>
  <el-container class="layout-container">
    <!-- 侧边栏：深空科技风格 -->
    <el-aside width="240px" class="tech-aside">
      <div class="logo-area">
        <div class="logo-neon-box">
          <el-icon :size="22" color="#00f3ff"><Lightning /></el-icon>
        </div>
        <div class="logo-text">
          <span>CYBER</span>
          <span class="highlight">ENERGY</span>
        </div>
      </div>

      <el-menu
          :default-active="$route.path"
          router
          class="tech-menu"
          text-color="#94a3b8"
          active-text-color="#00f3ff"
          background-color="transparent"
      >
        <el-menu-item index="/">
          <el-icon><DataLine /></el-icon>
          <span>监控大屏 (Dashboard)</span>
        </el-menu-item>

        <el-menu-item index="/devices">
          <el-icon><Cpu /></el-icon>
          <span>设备矩阵 (Devices)</span>
        </el-menu-item>

        <el-menu-item index="/alarms">
          <el-icon><Warning /></el-icon>
          <span>入侵告警 (Alarms)</span>
        </el-menu-item>

        <div class="menu-label">SYSTEM</div>

        <el-menu-item index="/settings" disabled>
          <el-icon><Setting /></el-icon>
          <span>系统核心 (Core)</span>
        </el-menu-item>
      </el-menu>
    </el-aside>

    <!-- 右侧内容 -->
    <el-container>
      <!-- 透明头部 -->
      <el-header class="tech-header">
        <div class="header-left">
          <el-icon class="pulse-icon" color="#00f3ff" size="20"><Location /></el-icon>
          <span class="location-text">SECTOR: UNIVERSITY-MAIN / {{ currentRouteName }}</span>
        </div>

        <div class="header-right">
          <div class="status-indicator">
            <span class="dot"></span> SYSTEM ONLINE
          </div>
          <el-avatar :size="30" shape="square" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" style="border: 1px solid #00f3ff" />
        </div>
      </el-header>

      <!-- 主视图 -->
      <el-main class="tech-main">
        <router-view v-slot="{ Component }">
          <transition name="cyber-fade" mode="out-in">
            <component :is="Component" />
          </transition>
        </router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()

const currentRouteName = computed(() => {
  switch (route.path) {
    case '/': return 'DASHBOARD'
    case '/devices': return 'DEVICES'
    case '/alarms': return 'ALERTS'
    default: return 'UNKNOWN'
  }
})
</script>

<style scoped>
.layout-container {
  height: 100vh;
  background: transparent;
}

/* --- 侧边栏 --- */
.tech-aside {
  background: rgba(10, 15, 30, 0.8); /* 极深蓝半透明 */
  border-right: 1px solid #1e3a8a;
  backdrop-filter: blur(20px);
  display: flex;
  flex-direction: column;
}

.logo-area {
  height: 60px;
  display: flex;
  align-items: center;
  padding-left: 20px;
  background: linear-gradient(90deg, rgba(0, 243, 255, 0.1) 0%, transparent 100%);
  border-bottom: 1px solid #1e3a8a;
}

.logo-neon-box {
  width: 36px;
  height: 36px;
  border: 1px solid #00f3ff;
  box-shadow: 0 0 10px rgba(0, 243, 255, 0.3);
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 12px;
  background: rgba(0, 0, 0, 0.3);
}

.logo-text {
  font-family: 'Segoe UI', sans-serif;
  font-weight: 800;
  font-size: 16px;
  color: #fff;
  letter-spacing: 1px;
}
.logo-text .highlight {
  color: #00f3ff;
}

.tech-menu {
  border-right: none;
  padding-top: 20px;
}

.menu-label {
  font-size: 10px;
  color: #475569;
  padding: 20px 0 10px 24px;
  letter-spacing: 2px;
  font-weight: bold;
}

/* 菜单项激活特效：左侧光剑 */
:deep(.el-menu-item.is-active) {
  background: linear-gradient(90deg, rgba(0, 243, 255, 0.15), transparent) !important;
  border-left: 3px solid #00f3ff;
  color: #fff !important;
  text-shadow: 0 0 8px rgba(0, 243, 255, 0.6);
}

:deep(.el-menu-item:hover) {
  color: #fff !important;
  background: rgba(255, 255, 255, 0.05) !important;
}

/* --- 顶部 --- */
.tech-header {
  height: 60px;
  border-bottom: 1px solid rgba(30, 58, 138, 0.5);
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 30px;
  background: rgba(5, 10, 20, 0.5);
}

.location-text {
  font-family: 'Courier New', monospace;
  font-size: 14px;
  color: #00f3ff;
  letter-spacing: 1px;
  margin-left: 10px;
}

.pulse-icon {
  animation: pulse 2s infinite;
}

.status-indicator {
  font-size: 12px;
  color: #10b981;
  margin-right: 20px;
  display: flex;
  align-items: center;
  gap: 6px;
  font-family: monospace;
}

.dot {
  width: 8px;
  height: 8px;
  background-color: #10b981;
  border-radius: 50%;
  box-shadow: 0 0 8px #10b981;
}

/* --- 内容区 --- */
.tech-main {
  padding: 20px;
}

@keyframes pulse {
  0% { opacity: 1; text-shadow: 0 0 10px #00f3ff; }
  50% { opacity: 0.5; text-shadow: 0 0 0 transparent; }
  100% { opacity: 1; text-shadow: 0 0 10px #00f3ff; }
}

/* 页面切换：全息扫描效果 */
.cyber-fade-enter-active,
.cyber-fade-leave-active {
  transition: all 0.4s cubic-bezier(0.25, 1, 0.5, 1);
}

.cyber-fade-enter-from {
  opacity: 0;
  transform: translateY(20px) scale(0.98);
  filter: blur(10px);
}

.cyber-fade-leave-to {
  opacity: 0;
  transform: scale(1.02);
  filter: blur(10px);
}
</style>