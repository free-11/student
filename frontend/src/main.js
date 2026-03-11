import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

const app = createApp(App)

// 使用路由
app.use(router)

// 全局注册Element Plus组件
app.config.globalProperties.$ELEMENT = {
  size: 'default'
}

// 挂载应用
app.mount('#app')
console.log('Vue app mounted')