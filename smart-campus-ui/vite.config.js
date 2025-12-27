import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [vue()],
    server: {
        port: 5173, // 前端运行端口
        proxy: {
            // 关键配置：告诉 Vite，如果遇到以 /api 开头的请求，都转发给 Spring Boot
            '/api': {
                target: 'http://localhost:8081', // 你的后端地址
                changeOrigin: true,
                // rewrite: (path) => path.replace(/^\/api/, '') // 如果后端不需要 /api 前缀，可以开启这行。但你的 Controller 都有 /api，所以不需要 rewrite
            }
        }
    }
})