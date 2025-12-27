import { createApp } from 'vue'
import './style.css' // Vite 默认样式，可以保留
import App from './App.vue'

// 1. 引入路由配置
import router from './router'

// 2. 引入 Element Plus (UI组件库)
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css' // 引入样式文件
// 引入 Element Plus 的图标
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

const app = createApp(App)

// 注册所有图标 (方便直接使用 <Edit /> 这种图标组件)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

// 3. 安装插件
app.use(router)
app.use(ElementPlus)

// 4. 挂载到页面
app.mount('#app')