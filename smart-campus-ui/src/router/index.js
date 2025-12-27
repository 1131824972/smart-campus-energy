import { createRouter, createWebHistory } from 'vue-router'

// 引入组件
import Dashboard from '../views/dashboard/Dashboard.vue'

const routes = [
    {
        path: '/',
        name: 'Dashboard',
        component: Dashboard
    },
    {
        path: '/devices',
        name: 'Devices',
        // 暂时用占位符，下一步我们写这个
        component: { template: '<div class="content-card"><h2>设备管理模块 (开发中)</h2></div>' }
    },
    {
        path: '/alarms',
        name: 'Alarms',
        component: { template: '<div class="content-card"><h2>告警中心模块 (开发中)</h2></div>' }
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router