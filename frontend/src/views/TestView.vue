<template>
  <div class="test-view">
    <h1>测试页面</h1>
    <p>这是一个测试页面，用于验证 Vue 组件是否能够正常显示。</p>
    <el-button type="primary" style="background: linear-gradient(135deg, #4f46e5, #4338ca); border: none;">
      测试按钮
    </el-button>
  </div>
</template>

<script>
export default {
  name: 'TestView'
}
</script>

<style scoped>
.test-view {
  padding: 20px;
  background-color: #f0f4ff;
  min-height: 100vh;
}
</style>