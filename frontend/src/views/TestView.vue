<template>
  <div class="test-view">
    <h1>测试页面</h1>
    <button @click="testAPI" style="padding: 8px 16px; background: linear-gradient(135deg, #4f46e5, #4338ca); border: none; color: white; border-radius: 4px;">
      测试API调用
    </button>
    <div v-if="testResult">
      <h2>测试结果:</h2>
      <pre>{{ testResult }}</pre>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'TestView',
  data() {
    return {
      testResult: null
    }
  },
  methods: {
    async testAPI() {
      try {
        console.log('开始测试API调用...')
        const response = await axios.get('/student/findAll')
        console.log('API响应:', response)
        this.testResult = JSON.stringify(response.data, null, 2)
      } catch (error) {
        console.error('API调用失败:', error)
        this.testResult = JSON.stringify(error, null, 2)
      }
    }
  }
}
</script>

<style scoped>
.test-view {
  min-height: 100vh;
  background: linear-gradient(135deg, #f0f4ff 0%, #e0e7ff 100%);
  padding: 20px;
  font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
}

pre {
  background-color: #f5f5f5;
  padding: 10px;
  border-radius: 4px;
  white-space: pre-wrap;
  margin-top: 20px;
}
</style>