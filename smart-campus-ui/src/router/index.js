import { createRouter, createWebHistory } from 'vue-router'

// 定义路由规则
const routes = [
    {
        path: '/',
        name: 'Home',
        // 简单的临时首页，之后我们会换成真正的组件文件
        component: { template: '<div style="padding:20px"><h2>📊 仪表盘</h2><p>这里将展示实时能耗图表...</p></div>' }
    },
    {
        path: '/devices',
        name: 'Devices',
        component: { template: '<div style="padding:20px"><h2>🔌 设备管理</h2><p>这里将展示设备列表...</p></div>' }
    },
    {
        path: '/alarms',
        name: 'Alarms',
        component: { template: '<div style="padding:20px"><h2>⚠️ 告警中心</h2><p>这里将展示异常告警记录...</p></div>' }
    }
]

// 创建路由实例
const router = createRouter({
    // 使用 HTML5 模式 (URL没有 # 号)
    history: createWebHistory(),
    routes
})

export default router