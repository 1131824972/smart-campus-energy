import { createRouter, createWebHistory } from 'vue-router'

// 引入组件
import Dashboard from '../views/dashboard/Dashboard.vue'
import DeviceList from '../views/device/DeviceList.vue'
import AlarmList from '../views/alarm/AlarmList.vue' // 新增

const routes = [
    {
        path: '/',
        name: 'Dashboard',
        component: Dashboard
    },
    {
        path: '/devices',
        name: 'Devices',
        component: DeviceList
    },
    {
        path: '/alarms',
        name: 'Alarms',
        component: AlarmList // 替换原来的占位符
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router