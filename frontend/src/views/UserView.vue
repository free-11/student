<template>
  <div class="user-view">
    <h2>个人信息管理</h2>
    
    <!-- 个人信息展示 -->
    <div class="info-card" v-if="userInfo">
      <h3>账号信息</h3>
      <div class="info-item">
        <label>用户名:</label>
        <span>{{ userInfo.username }}</span>
      </div>
      <div class="info-item">
        <label>密码:</label>
        <span>{{ userInfo.password }}</span>
      </div>
    </div>
    
    <!-- 修改密码表单 -->
    <div class="password-card small-card">
      <h3>修改密码</h3>
      <form @submit.prevent="updatePassword">
        <div class="form-group">
          <label for="oldPassword">旧密码:</label>
          <input type="password" id="oldPassword" v-model="passwordForm.oldPassword" required class="form-input">
        </div>
        <div class="form-group">
          <label for="newPassword">新密码:</label>
          <input type="password" id="newPassword" v-model="passwordForm.newPassword" required class="form-input">
        </div>
        <div class="form-group">
          <label for="confirmPassword">确认新密码:</label>
          <input type="password" id="confirmPassword" v-model="passwordForm.confirmPassword" required class="form-input">
        </div>
        <div class="form-actions">
          <button type="submit" class="btn-save">保存修改</button>
        </div>
      </form>
    </div>
    
    <!-- 操作按钮 -->
    <div class="action-buttons">
      <button @click="loadUserInfo" class="btn-refresh">刷新信息</button>
      <button @click="logout" class="btn-logout">退出登录</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'UserView',
  data() {
    return {
      userInfo: null,
      passwordForm: {
        oldPassword: '',
        newPassword: '',
        confirmPassword: ''
      }
    }
  },
  mounted() {
    this.loadUserInfo()
  },
  methods: {
    async loadUserInfo() {
      try {
        const token = localStorage.getItem('token')
        if (!token) {
          this.$router.push('/login')
          return
        }
        
        // 从token中提取用户名
        const decodedToken = JSON.parse(atob(token.split('.')[1]))
        const username = decodedToken.claims.username
        
        const response = await axios.get(`/user/info?username=${username}`)
        if (response.data.code === 0) {
          this.userInfo = response.data.data
        } else {
          alert('获取用户信息失败')
        }
      } catch (error) {
        console.error('获取用户信息失败:', error)
        alert('获取用户信息失败')
      }
    },
    async updatePassword() {
      try {
        // 验证新密码和确认密码是否一致
        if (this.passwordForm.newPassword !== this.passwordForm.confirmPassword) {
          alert('新密码和确认密码不一致')
          return
        }
        
        // 从token中提取用户名
        const token = localStorage.getItem('token')
        const decodedToken = JSON.parse(atob(token.split('.')[1]))
        const username = decodedToken.claims.username
        
        const response = await axios.post('/user/updatePassword', {
          username: username,
          oldPassword: this.passwordForm.oldPassword,
          newPassword: this.passwordForm.newPassword
        })
        
        if (response.data.code === 0) {
          alert('密码修改成功')
          // 重置表单
          this.passwordForm = {
            oldPassword: '',
            newPassword: '',
            confirmPassword: ''
          }
          // 重新加载用户信息
          this.loadUserInfo()
        } else {
          alert(response.data.msg || '密码修改失败')
        }
      } catch (error) {
        console.error('密码修改失败:', error)
        alert('密码修改失败')
      }
    },
    logout() {
      localStorage.removeItem('token')
      this.$router.push('/login')
    }
  }
}
</script>

<style scoped>
/* 主容器 */
.user-view {
  min-height: 100vh;
  background: linear-gradient(135deg, #f0f4ff 0%, #e0e7ff 100%);
  padding: 20px;
  font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
}

/* 标题样式 */
h2 {
  margin-bottom: 30px;
  color: #333;
  font-size: 24px;
  font-weight: 600;
  text-align: center;
  background: linear-gradient(135deg, #4f46e5, #4338ca);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

/* 信息卡片 */
.info-card, .password-card {
  background-color: white;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  padding: 24px;
  margin-bottom: 20px;
}

/* 小卡片样式 */
.small-card {
  max-width: 500px;
  margin-left: auto;
  margin-right: auto;
  padding: 20px;
}

/* 卡片标题 */
h3 {
  margin-bottom: 20px;
  color: #334155;
  font-size: 18px;
  font-weight: 600;
  border-bottom: 2px solid #e2e8f0;
  padding-bottom: 10px;
}

/* 信息项 */
.info-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 0;
  border-bottom: 1px solid #f1f5f9;
}

.info-item:last-child {
  border-bottom: none;
}

.info-item label {
  font-weight: 500;
  color: #64748b;
}

.info-item span {
  font-weight: 500;
  color: #334155;
}

/* 表单组 */
.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  margin-bottom: 8px;
  font-weight: 500;
  color: #64748b;
}

/* 表单输入框 */
.form-input {
  width: 100%;
  padding: 12px 16px;
  border: 2px solid #e2e8f0;
  border-radius: 8px;
  font-size: 16px;
  transition: all 0.3s ease;
  background-color: #f8fafc;
}

/* 输入框焦点效果 */
.form-input:focus {
  outline: none;
  border-color: #4f46e5;
  background-color: white;
  box-shadow: 0 0 0 3px rgba(79, 70, 229, 0.1);
  transform: translateY(-1px);
}

/* 表单操作按钮 */
.form-actions {
  display: flex;
  justify-content: flex-end;
  margin-top: 24px;
}

/* 保存按钮 */
.btn-save {
  padding: 12px 24px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-size: 16px;
  font-weight: 500;
  transition: all 0.3s ease;
  background: linear-gradient(135deg, #4f46e5, #4338ca);
  color: white;
  box-shadow: 0 2px 4px rgba(79, 70, 229, 0.3);
}

/* 按钮悬停效果 */
.btn-save:hover {
  background: linear-gradient(135deg, #4338ca, #3730a3);
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(79, 70, 229, 0.4);
}

/* 按钮点击效果 */
.btn-save:active {
  transform: translateY(0);
  box-shadow: 0 2px 4px rgba(79, 70, 229, 0.3);
}

/* 操作按钮容器 */
.action-buttons {
  display: flex;
  gap: 12px;
  justify-content: center;
  margin-top: 20px;
}

/* 刷新按钮 */
.btn-refresh {
  padding: 10px 20px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-size: 16px;
  font-weight: 500;
  transition: all 0.3s ease;
  background: linear-gradient(135deg, #10b981, #059669);
  color: white;
  box-shadow: 0 2px 4px rgba(16, 185, 129, 0.3);
}

/* 刷新按钮悬停效果 */
.btn-refresh:hover {
  background: linear-gradient(135deg, #059669, #047857);
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(16, 185, 129, 0.4);
}

/* 退出登录按钮 */
.btn-logout {
  padding: 10px 20px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-size: 16px;
  font-weight: 500;
  transition: all 0.3s ease;
  background: linear-gradient(135deg, #ef4444, #dc2626);
  color: white;
  box-shadow: 0 2px 4px rgba(239, 68, 68, 0.3);
}

/* 退出登录按钮悬停效果 */
.btn-logout:hover {
  background: linear-gradient(135deg, #dc2626, #b91c1c);
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(239, 68, 68, 0.4);
}

/* 响应式设计 */
@media (max-width: 768px) {
  .user-view {
    padding: 15px;
  }
  
  h2 {
    font-size: 20px;
    margin-bottom: 20px;
  }
  
  .info-card, .password-card {
    padding: 20px;
  }
  
  h3 {
    font-size: 16px;
  }
  
  .action-buttons {
    flex-direction: column;
    align-items: center;
  }
  
  .btn-refresh, .btn-logout, .btn-save {
    width: 100%;
    max-width: 200px;
  }
}
</style>