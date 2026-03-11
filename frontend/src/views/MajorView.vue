<template>
  <div class="major-view">
    <h2>专业管理</h2>
    <div class="action-buttons">
      <button @click="loadMajors" class="btn-refresh">刷新列表</button>
    </div>
    <table class="major-table">
      <thead>
        <tr>
          <th>专业ID</th>
          <th>专业名称</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="major in majors" :key="major.majorId">
          <td>{{ major.majorId }}</td>
          <td>{{ major.majorName }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'MajorView',
  data() {
    return {
      majors: []
    }
  },
  mounted() {
    this.loadMajors()
  },
  methods: {
    async loadMajors() {
      try {
        const response = await axios.get('/api/major/findAll')
        if (response.data.code === 0) {
          this.majors = response.data.data
        } else {
          alert('加载专业列表失败')
        }
      } catch (error) {
        console.error('加载专业列表失败:', error)
        alert('加载专业列表失败')
      }
    }
  }
}
</script>

<style scoped>
.major-view {
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

.major-table {
  width: 100%;
  border-collapse: collapse;
  background-color: white;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.major-table th, .major-table td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #ddd;
}

.major-table th {
  background-color: #f2f2f2;
  font-weight: bold;
}

.major-table tr:hover {
  background-color: #f5f5f5;
}
</style>
