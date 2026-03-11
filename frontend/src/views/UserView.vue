<template>
  <div class="user-view">
    <h2>用户管理</h2>
    <div class="action-buttons">
      <button @click="openRegisterModal" class="btn-register">注册用户</button>
      <button @click="openLoginModal" class="btn-login">用户登录</button>
    </div>
    <div id="loginStatus" class="login-status" v-if="loginInfo">
      <p>当前登录用户: {{ loginInfo.username }}</p>
      <button @click="logout" class="btn-logout">退出登录</button>
    </div>

    <!-- 注册用户模态框 -->
    <div v-if="showRegisterModal" class="modal">
      <div class="modal-content">
        <span @click="closeRegisterModal" class="close">&times;</span>
        <h3>注册用户</h3>
        <form @submit.prevent="register">
          <div class="form-group">
            <label>用户名:</label>
            <input type="text" v-model="registerForm.username" required>
          </div>
          <div class="form-group">
            <label>密码:</label>
            <input type="password" v-model="registerForm.password" required>
          </div>
          <div class="form-actions">
            <button type="button" @click="closeRegisterModal" class="btn-cancel">取消</button>
            <button type="submit" class="btn-save">注册</button>
          </div>
        </form>
      </div>
    </div>

    <!-- 登录模态框 -->
    <div v-if="showLoginModal" class="modal">
      <div class="modal-content">
        <span @click="closeLoginModal" class="close">&times;</span>
        <h3>用户登录</h3>
        <form @submit.prevent="login">
          <div class="form-group">
            <label>用户名:</label>
            <input type="text" v-model="loginForm.username" required>
          </div>
          <div class="form-group">
            <label>密码:</label>
            <input type="password" v-model="loginForm.password" required>
          </div>
          <div class="form-actions">
            <button type="button" @click="closeLoginModal" class="btn-cancel">取消</button>
            <button type="submit" class="btn-save">登录</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'UserView',
  data() {
    return {
      showRegisterModal: false,
      showLoginModal: false,
      registerForm: {
        username: '',
        password: ''
      },
      loginForm: {
        username: '',
        password: ''
      },
      loginInfo: null
    }
  },
  mounted() {
    // 检查本地存储中是否有登录信息
    const savedLoginInfo = localStorage.getItem('loginInfo')
    if (savedLoginInfo) {
      this.loginInfo = JSON.parse(savedLoginInfo)
    }
  },
  methods: {
    openRegisterModal() {
      this.showRegisterModal = true
    },
    closeRegisterModal() {
      this.showRegisterModal = false
      this.registerForm = {
        username: '',
        password: ''
      }
    },
    openLoginModal() {
      this.showLoginModal = true
    },
    closeLoginModal() {
      this.showLoginModal = false
      this.loginForm = {
        username: '',
        password: ''
      }
    },
    async register() {
      try {
        const response = await axios.post('/api/user/register', this.registerForm)
        if (response.data.code === 200) {
          alert('注册成功')
          this.closeRegisterModal()
        } else {
          alert(response.data.msg || '注册失败')
        }
      } catch (error) {
        console.error('注册失败:', error)
        alert('注册失败')
      }
    },
    async login() {
      try {
        const response = await axios.post('/api/user/login', this.loginForm)
        if (response.data.code === 200) {
          this.loginInfo = {
            username: this.loginForm.username,
            token: response.data.data
          }
          localStorage.setItem('loginInfo', JSON.stringify(this.loginInfo))
          alert('登录成功')
          this.closeLoginModal()
        } else {
          alert(response.data.msg || '登录失败')
        }
      } catch (error) {
        console.error('登录失败:', error)
        alert('登录失败')
      }
    },
    logout() {
      this.loginInfo = null
      localStorage.removeItem('loginInfo')
      alert('已退出登录')
    }
  }
}
</script>

<style scoped>
.user-view {
  padding: 20px;
}

h2 {
  margin-bottom: 20px;
  color: #333;
}

.action-buttons {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
}

.btn-register, .btn-login, .btn-logout, .btn-cancel, .btn-save {
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
  transition: background-color 0.3s;
}

.btn-register {
  background-color: #4CAF50;
  color: white;
}

.btn-register:hover {
  background-color: #45a049;
}

.btn-login {
  background-color: #2196F3;
  color: white;
}

.btn-login:hover {
  background-color: #0b7dda;
}

.btn-logout {
  background-color: #f44336;
  color: white;
  margin-left: 10px;
}

.btn-logout:hover {
  background-color: #da190b;
}

.btn-cancel {
  background-color: #9e9e9e;
  color: white;
  margin-right: 10px;
}

.btn-cancel:hover {
  background-color: #757575;
}

.btn-save {
  background-color: #4CAF50;
  color: white;
}

.btn-save:hover {
  background-color: #45a049;
}

.login-status {
  background-color: #e3f2fd;
  padding: 15px;
  border-radius: 4px;
  margin-bottom: 20px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.login-status p {
  margin: 0;
  color: #1976d2;
  font-weight: bold;
}

/* 模态框样式 */
.modal {
  position: fixed;
  z-index: 1000;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgba(0,0,0,0.4);
  display: flex;
  align-items: center;
  justify-content: center;
}

.modal-content {
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  width: 400px;
  box-shadow: 0 4px 8px rgba(0,0,0,0.2);
}

.close {
  color: #aaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
  cursor: pointer;
}

.close:hover {
  color: black;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

.form-group input {
  width: 100%;
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
  box-sizing: border-box;
}

.form-actions {
  display: flex;
  justify-content: flex-end;
  margin-top: 20px;
}
</style>