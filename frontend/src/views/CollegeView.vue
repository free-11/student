<template>
  <div class="college-view">
    <h2>学院管理</h2>
    <div class="action-buttons">
      <button @click="loadColleges" class="btn-refresh">刷新列表</button>
    </div>
    <table class="college-table">
      <thead>
        <tr>
          <th>学院ID</th>
          <th>学院名称</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="college in colleges" :key="college.collegeId">
          <td>{{ college.collegeId }}</td>
          <td>{{ college.collegeName }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'CollegeView',
  data() {
    return {
      colleges: []
    }
  },
  mounted() {
    this.loadColleges()
  },
  methods: {
    async loadColleges() {
      try {
        const response = await axios.get('/api/college/findAll')
        if (response.data.code === 0) {
          this.colleges = response.data.data
        } else {
          alert('加载学院列表失败')
        }
      } catch (error) {
        console.error('加载学院列表失败:', error)
        alert('加载学院列表失败')
      }
    }
  }
}
</script>

<style scoped>
.college-view {
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

.college-table {
  width: 100%;
  border-collapse: collapse;
  background-color: white;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.college-table th, .college-table td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #ddd;
}

.college-table th {
  background-color: #f2f2f2;
  font-weight: bold;
}

.college-table tr:hover {
  background-color: #f5f5f5;
}
</style>
