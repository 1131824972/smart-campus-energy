<template>
  <el-container class="layout-container">
    <!-- 左侧菜单栏 -->
    <el-aside width="220px" class="aside-menu">
      <div class="logo-area">
        <el-icon :size="24" color="#fff" style="margin-right: 8px"><Monitor /></el-icon>
        <span class="logo-text">智慧校园能耗平台</span>
      </div>

      <el-menu
          active-text-color="#409EFF"
          background-color="#304156"
          text-color="#bfcbd9"
          :default-active="$route.path"
          router
          class="el-menu-vertical"
      >
        <!-- 仪表盘 -->
        <el-menu-item index="/">
          <el-icon><DataLine /></el-icon>
          <span>首页仪表盘</span>
        </el-menu-item>

        <!-- 核心业务：设备管理 -->
        <el-menu-item index="/devices">
          <el-icon><Cpu /></el-icon>
          <span>智能设备管理</span>
        </el-menu-item>

        <!-- 核心业务：告警中心 -->
        <el-menu-item index="/alarms">
          <el-icon><Bell /></el-icon>
          <span>异常告警记录</span>
        </el-menu-item>
      </el-menu>
    </el-aside>

    <!-- 右侧主体区域 -->
    <el-container>
      <!-- 顶部 Header -->
      <el-header class="header-bar">
        <div class="header-left">
          <!-- 面包屑导航 (简单模拟) -->
          <span class="breadcrumb">当前位置：{{ currentRouteName }}</span>
        </div>
        <div class="header-right">
          <el-dropdown>
            <span class="user-info">
              <el-avatar :size="30" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" />
              <span style="margin-left: 8px">管理员 (Admin)</span>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item>个人中心</el-dropdown-item>
                <el-dropdown-item divided>退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>

      <!-- 主要内容区 -->
      <el-main class="main-body">
        <!-- 路由出口：页面会显示在这里 -->
        <!-- 使用 el-card 包裹内容，更有层次感 -->
        <router-view v-slot="{ Component }">
          <transition name="fade" mode="out-in">
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

// 根据当前路由路径，动态显示页面标题
const currentRouteName = computed(() => {
  switch (route.path) {
    case '/': return '首页仪表盘'
    case '/devices': return '智能设备管理'
    case '/alarms': return '异常告警记录'
    default: return '未知页面'
  }
})
</script>

<style scoped>
.layout-container {
  height: 100vh; /* 占满整个屏幕高度 */
  overflow: hidden;
}

/* 左侧菜单样式 */
.aside-menu {
  background-color: #304156; /* 深蓝灰色，专业感强 */
  color: white;
  display: flex;
  flex-direction: column;
  box-shadow: 2px 0 6px rgba(0,21,41,.35);
  z-index: 10;
}

.logo-area {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #2b3649;
  font-size: 18px;
  font-weight: bold;
  color: #fff;
  border-bottom: 1px solid #1f2d3d;
}

.el-menu-vertical {
  border-right: none; /* 去掉 Element 默认的右边框 */
}

/* 顶部 Header 样式 */
.header-bar {
  background-color: #fff;
  border-bottom: 1px solid #e6e6e6;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
  box-shadow: 0 1px 4px rgba(0,21,41,.08);
}

.breadcrumb {
  font-size: 14px;
  color: #606266;
}

.user-info {
  display: flex;
  align-items: center;
  cursor: pointer;
  font-size: 14px;
  color: #333;
}

/* 内容区背景 */
.main-body {
  background-color: #f0f2f5; /* 浅灰背景，保护视力 */
  padding: 20px;
}

/* 简单的淡入淡出动画 */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>