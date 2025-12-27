<template>
  <div class="chart-container" ref="chartRef"></div>
</template>

<script setup>
import { ref, onMounted, onUnmounted, watch } from 'vue'
import * as echarts from 'echarts'
import axios from 'axios'

// 接收父组件传来的 deviceId，告知我们要画哪个设备的图
const props = defineProps({
  deviceId: {
    type: Number,
    required: true
  }
})

const chartRef = ref(null)
let myChart = null
let timer = null

// 初始化图表
const initChart = () => {
  if (!chartRef.value) return

  myChart = echarts.init(chartRef.value)

  // 基础配置 (骨架)
  const option = {
    backgroundColor: 'transparent', // 透明背景
    tooltip: {
      trigger: 'axis',
      backgroundColor: 'rgba(0,0,0,0.8)',
      borderColor: '#00f3ff',
      textStyle: { color: '#fff' }
    },
    grid: {
      top: '15%',
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      boundaryGap: false,
      data: [], // 暂时留空
      axisLine: { lineStyle: { color: '#1e3a8a' } },
      axisLabel: { color: '#94a3b8', fontFamily: 'monospace' },
      splitLine: { show: false }
    },
    yAxis: {
      type: 'value',
      name: '功率 (W)',
      nameTextStyle: { color: '#00f3ff', padding: [0, 0, 0, 10] },
      axisLine: { show: false },
      axisLabel: { color: '#94a3b8' },
      splitLine: {
        lineStyle: {
          color: 'rgba(30, 58, 138, 0.3)', // 极淡的网格线
          type: 'dashed'
        }
      }
    },
    series: [
      {
        name: '实时功率',
        type: 'line',
        smooth: true, // 平滑曲线
        symbol: 'circle',
        symbolSize: 8,
        itemStyle: {
          color: '#00f3ff', // 点的颜色
          shadowBlur: 10,
          shadowColor: '#00f3ff'
        },
        lineStyle: {
          color: '#00f3ff',
          width: 3,
          shadowBlur: 10, // 线条发光
          shadowColor: '#00f3ff'
        },
        areaStyle: {
          // 区域渐变填充
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: 'rgba(0, 243, 255, 0.4)' },
            { offset: 1, color: 'rgba(0, 243, 255, 0)' }
          ])
        },
        data: [] // 暂时留空
      }
    ]
  }
  myChart.setOption(option)
}

// 获取真实数据
const fetchData = async () => {
  if (!props.deviceId) return

  try {
    // 调用后端接口：获取最近10条数据
    const res = await axios.get(`/api/energy-data/recent?deviceId=${props.deviceId}`)
    if (res.status === 200 && res.data.length > 0) {
      // 提取 X轴 (时间) 和 Y轴 (功率) 数据
      const xData = res.data.map(item => item.createTime)
      const yData = res.data.map(item => item.power)

      // 更新图表
      myChart.setOption({
        xAxis: { data: xData },
        series: [{ data: yData }]
      })
    }
  } catch (error) {
    console.error("图表数据获取失败", error)
  }
}

// 监听 deviceId 变化，如果切设备了，立即刷新
watch(() => props.deviceId, (newVal) => {
  if (newVal) fetchData()
})

// 挂载时启动
onMounted(() => {
  initChart()
  fetchData()

  // 开启定时器，每 3 秒刷新一次，实现“实时监控”效果
  timer = setInterval(fetchData, 3000)

  // 窗口缩放时自适应
  window.addEventListener('resize', () => myChart?.resize())
})

// 销毁时清理
onUnmounted(() => {
  if (timer) clearInterval(timer)
  if (myChart) myChart.dispose()
})
</script>

<style scoped>
.chart-container {
  width: 100%;
  height: 100%;
  min-height: 350px;
}
</style>