<template>
  <div class="role-view">
    <h2>角色管理</h2>
    <div class="action-buttons">
      <button @click="loadRoles" class="btn-refresh">刷新列表</button>
    </div>
    <table class="role-table">
      <thead>
        <tr>
          <th>角色ID</th>
          <th>角色名称</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="role in roles" :key="role.roleId">
          <td>{{ role.roleId }}</td>
          <td>{{ role.roleName }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'RoleView',
  data() {
    return {
      roles: []
    }
  },
  mounted() {
    this.loadRoles()
  },
  methods: {
    async loadRoles() {
      try {
        const response = await axios.get('/api/role/findAll')
        if (response.data.code === 0) {
          this.roles = response.data.data
        } else {
          alert('加载角色列表失败')
        }
      } catch (error) {
        console.error('加载角色列表失败:', error)
        alert('加载角色列表失败')
      }
    }
  }
}
</script>

<style scoped>
.role-view {
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

.btn-refresh {
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
  transition: background-color 0.3s;
  background-color: #2196F3;
  color: white;
}

.btn-refresh:hover {
  background-color: #0b7dda;
}

.role-table {
  width: 100%;
  border-collapse: collapse;
  background-color: white;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.role-table th, .role-table td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #ddd;
}

.role-table th {
  background-color: #f2f2f2;
  font-weight: bold;
}

.role-table tr:hover {
  background-color: #f5f5f5;
}
</style>
