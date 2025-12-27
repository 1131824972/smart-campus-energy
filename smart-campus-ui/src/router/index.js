import { createRouter, createWebHistory } from 'vue-router'

// 引入组件
import Dashboard from '../views/dashboard/Dashboard.vue'
import DeviceList from '../views/device/DeviceList.vue' // 新增

const routes = [
    {
        path: '/',
        name: 'Dashboard',
        component: Dashboard
    },
    {
        path: '/devices',
        name: 'Devices',
        component: DeviceList // 指向真实的设备列表页
    },
    {
        path: '/alarms',
        name: 'Alarms',
        // 告警页暂时还是占位符，下次开发
        component: { template: '<div class="tech-card" style="color:white; padding:20px"><h2>🚧 告警中心正在建设中...</h2></div>' }
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router