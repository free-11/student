<template>
  <div class="address-view">
    <h2>地址管理</h2>
    <div class="action-buttons">
      <button @click="openAddModal" class="btn-add">添加地址</button>
    </div>

    <!-- 添加地址模态框 -->
    <div v-if="showModal" class="modal">
      <div class="modal-content">
        <span @click="closeModal" class="close">&times;</span>
        <h3>添加地址</h3>
        <form @submit.prevent="saveAddress">
          <div class="form-group">
            <label>省份:</label>
            <input type="text" v-model="formData.province" required>
          </div>
          <div class="form-group">
            <label>城市:</label>
            <input type="text" v-model="formData.city" required>
          </div>
          <div class="form-group">
            <label>详细地址:</label>
            <input type="text" v-model="formData.detail" required>
          </div>
          <div class="form-actions">
            <button type="button" @click="closeModal" class="btn-cancel">取消</button>
            <button type="submit" class="btn-save">保存</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'AddressView',
  data() {
    return {
      showModal: false,
      formData: {
        province: '',
        city: '',
        detail: ''
      }
    }
  },
  methods: {
    openAddModal() {
      this.formData = {
        province: '',
        city: '',
        detail: ''
      }
      this.showModal = true
    },
    closeModal() {
      this.showModal = false
    },
    async saveAddress() {
      try {
        const response = await axios.post('/api/form/add', this.formData)
        if (response.data.code === 200) {
          alert('添加地址成功')
          this.closeModal()
        } else {
          alert('添加地址失败')
        }
      } catch (error) {
        console.error('添加地址失败:', error)
        alert('添加地址失败')
      }
    }
  }
}
</script>

<style scoped>
.address-view {
  padding: 20px;
}

h2 {
  margin-bottom: 20px;
  color: #333;
}

.action-buttons {
  margin-bottom: 20px;
}

.btn-add, .btn-cancel, .btn-save {
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
  transition: background-color 0.3s;
}

.btn-add {
  background-color: #4CAF50;
  color: white;
}

.btn-add:hover {
  background-color: #45a049;
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