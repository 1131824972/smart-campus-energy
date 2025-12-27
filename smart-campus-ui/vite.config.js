import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'

export default defineConfig({
    plugins: [vue()],
    // 1. 设置基础路径为 ./，防止打包后路径错误
    base: './',
    build: {
        // 2. 打包输出目录：直接输出到 Spring Boot 的静态资源目录
        // 注意：请根据你实际的文件夹结构调整 ../src/main/resources/static
        outDir: '../src/main/resources/static',
        // 3. 清空目标目录（防止旧文件残留）
        emptyOutDir: true
    },
    server: {
        port: 5173,
        proxy: {
            '/api': {
                target: 'http://localhost:8081',
                changeOrigin: true,
            }
        }
    }
})