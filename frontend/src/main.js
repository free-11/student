import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'

const app = createApp(App)

// 配置axios
axios.defaults.baseURL = '/api'

// 添加请求拦截器，在请求头中添加JWT token
axios.interceptors.request.use(
  config => {
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// 添加响应拦截器，处理401错误
axios.interceptors.response.use(
  response => {
    return response
  },
  error => {
    if (error.response && error.response.status === 401) {
      // 清除token并跳转到登录页面
      localStorage.removeItem('token')
      router.push('/login')
    }
    return Promise.reject(error)
  }
)

// 全局注册axios
app.config.globalProperties.$axios = axios

// 使用路由
app.use(router)

// 全局注册Element Plus组件
app.config.globalProperties.$ELEMENT = {
  size: 'default'
}

// 挂载应用
app.mount('#app')
console.log('Vue app mounted')