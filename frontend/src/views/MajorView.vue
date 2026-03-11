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
        const response = await axios.get('/major/findAll')
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
/* 主容器 */
.major-view {
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

/* 操作按钮容器 */
.action-buttons {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
  justify-content: center;
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

/* 按钮悬停效果 */
.btn-refresh:hover {
  background: linear-gradient(135deg, #059669, #047857);
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(16, 185, 129, 0.4);
}

/* 按钮点击效果 */
.btn-refresh:active {
  transform: translateY(0);
  box-shadow: 0 2px 4px rgba(16, 185, 129, 0.3);
}

/* 表格样式 */
.major-table {
  width: 100%;
  border-collapse: collapse;
  background-color: white;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  border-radius: 12px;
  overflow: hidden;
}

/* 表格表头 */
.major-table th {
  padding: 16px;
  text-align: left;
  background: linear-gradient(135deg, #f8fafc, #e2e8f0);
  font-weight: 600;
  color: #334155;
  border-bottom: 2px solid #e2e8f0;
}

/* 表格单元格 */
.major-table td {
  padding: 14px 16px;
  text-align: left;
  border-bottom: 1px solid #f1f5f9;
  transition: all 0.3s ease;
}

/* 表格行悬停效果 */
.major-table tr:hover {
  background-color: #f8fafc;
  transform: translateY(-1px);
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
}

/* 表格最后一行 */
.major-table tr:last-child td {
  border-bottom: none;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .major-view {
    padding: 15px;
  }
  
  h2 {
    font-size: 20px;
    margin-bottom: 20px;
  }
  
  .action-buttons {
    flex-direction: column;
    align-items: center;
  }
  
  .btn-refresh {
    width: 100%;
    max-width: 200px;
  }
  
  .major-table {
    font-size: 14px;
  }
  
  .major-table th,
  .major-table td {
    padding: 10px 12px;
  }
}
</style>
