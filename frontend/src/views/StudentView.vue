 <template>
  <div class="student-view">
    <!-- 页面头部 -->
    <h1>学生管理系统</h1>
    <p>管理学生信息，包括添加、编辑、删除和查看学生数据</p>

    <!-- 操作按钮 -->
    <button @click="openAddModal" style="margin-right: 10px; padding: 8px 16px; background: linear-gradient(135deg, #4f46e5, #4338ca); border: none; color: white; border-radius: 4px;">
      添加学生
    </button>
    <button @click="loadStudents" style="margin-right: 10px; padding: 8px 16px; background: linear-gradient(135deg, #10b981, #059669); border: none; color: white; border-radius: 4px;">
      刷新列表
    </button>

    <!-- 学生表格 -->
    <table style="width: 100%; border-collapse: collapse; margin-top: 20px;">
      <thead>
        <tr style="background-color: #f5f5f5;">
          <th style="border: 1px solid #ddd; padding: 8px;">学号</th>
          <th style="border: 1px solid #ddd; padding: 8px;">姓名</th>
          <th style="border: 1px solid #ddd; padding: 8px;">电话</th>
          <th style="border: 1px solid #ddd; padding: 8px;">年龄</th>
          <th style="border: 1px solid #ddd; padding: 8px;">性别</th>
          <th style="border: 1px solid #ddd; padding: 8px;">省份</th>
          <th style="border: 1px solid #ddd; padding: 8px;">城市</th>

          <th style="border: 1px solid #ddd; padding: 8px;">学院</th>
          <th style="border: 1px solid #ddd; padding: 8px;">专业</th>
          <th style="border: 1px solid #ddd; padding: 8px;">操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="student in students" :key="student.studentId" style="border: 1px solid #ddd;">
          <td style="border: 1px solid #ddd; padding: 8px;">{{ student.studentId }}</td>
          <td style="border: 1px solid #ddd; padding: 8px;">{{ student.name }}</td>
          <td style="border: 1px solid #ddd; padding: 8px;">{{ student.phone }}</td>
          <td style="border: 1px solid #ddd; padding: 8px;">{{ student.age }}</td>
          <td style="border: 1px solid #ddd; padding: 8px;">{{ student.gender }}</td>
          <td style="border: 1px solid #ddd; padding: 8px;">{{ student.province }}</td>
          <td style="border: 1px solid #ddd; padding: 8px;">{{ student.city }}</td>

          <td style="border: 1px solid #ddd; padding: 8px;">{{ student.collegeName }}</td>
          <td style="border: 1px solid #ddd; padding: 8px;">{{ student.majorName }}</td>
          <td style="border: 1px solid #ddd; padding: 8px;">
            <button @click="openEditModal(student)" style="margin-right: 5px; padding: 4px 8px; background: linear-gradient(135deg, #f59e0b, #d97706); border: none; color: white; border-radius: 4px; font-size: 12px;">
              编辑
            </button>
            <button @click="showDeleteConfirm(student.studentId)" style="padding: 4px 8px; background: linear-gradient(135deg, #ef4444, #dc2626); border: none; color: white; border-radius: 4px; font-size: 12px;">
              删除
            </button>
          </td>
        </tr>
      </tbody>
    </table>

    <!-- 添加/编辑学生模态框 -->
    <div id="modal" style="position: fixed; top: 0; left: 0; right: 0; bottom: 0; background-color: rgba(0, 0, 0, 0.5); justify-content: center; align-items: center; z-index: 1000; display: none;">
      <div style="background-color: white; padding: 20px; border-radius: 8px; width: 400px;">
        <h2>{{ isEdit ? '编辑学生' : '添加学生' }}</h2>
        <form @submit.prevent="saveStudent">
          <div style="margin-bottom: 15px;">
            <label style="display: block; margin-bottom: 5px;">姓名</label>
            <input v-model="formData.name" type="text" style="width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px;" required>
          </div>
          <div style="margin-bottom: 15px;">
            <label style="display: block; margin-bottom: 5px;">电话</label>
            <input v-model="formData.phone" type="text" style="width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px;" required>
          </div>
          <div style="margin-bottom: 15px;">
            <label style="display: block; margin-bottom: 5px;">年龄</label>
            <input v-model="formData.age" type="number" style="width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px;" required>
          </div>
          <div style="margin-bottom: 15px;">
            <label style="display: block; margin-bottom: 5px;">性别</label>
            <div>
              <label style="margin-right: 15px;"><input v-model="formData.gender" type="radio" value="男"> 男</label>
              <label><input v-model="formData.gender" type="radio" value="女"> 女</label>
            </div>
          </div>
          <div style="margin-bottom: 15px;">
            <label style="display: block; margin-bottom: 5px;">省份</label>
            <select v-model="formData.province" @change="onProvinceChange" style="width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px;" required>
              <option value="">请选择省份</option>
              <option v-for="province in provinces" :key="province.value" :value="province.value">{{ province.label }}</option>
            </select>
          </div>
          <div style="margin-bottom: 15px;">
            <label style="display: block; margin-bottom: 5px;">城市</label>
            <select v-model="formData.city" @change="onCityChange" style="width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px;" required>
              <option value="">请选择城市</option>
              <option v-for="city in getCities()" :key="city.value" :value="city.value">{{ city.label }}</option>
            </select>
          </div>

          <div style="margin-bottom: 15px;">
            <label style="display: block; margin-bottom: 5px;">详细地址</label>
            <input v-model="formData.detail" type="text" style="width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px;" required>
          </div>
          <div style="margin-bottom: 15px;">
            <label style="display: block; margin-bottom: 5px;">学院</label>
            <select v-model="formData.collegeId" style="width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px;" required>
              <option value="">请选择学院</option>
              <option v-for="college in colleges" :key="college.collegeId" :value="college.collegeId">{{ college.collegeName }}</option>
            </select>
          </div>
          <div style="margin-bottom: 15px;">
            <label style="display: block; margin-bottom: 5px;">专业</label>
            <select v-model="formData.majorId" style="width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px;" required>
              <option value="">请选择专业</option>
              <option v-for="major in majors" :key="major.majorId" :value="major.majorId">{{ major.majorName }}</option>
            </select>
          </div>
          <div style="margin-top: 20px; display: flex; justify-content: flex-end; gap: 10px;">
            <button type="button" @click="closeModal" style="padding: 8px 16px; background: linear-gradient(135deg, #94a3b8, #64748b); border: none; color: white; border-radius: 4px;">取消</button>
            <button type="submit" style="padding: 8px 16px; background: linear-gradient(135deg, #4f46e5, #4338ca); border: none; color: white; border-radius: 4px;">保存</button>
          </div>
        </form>
      </div>
    </div>

    <!-- 删除确认模态框 -->
    <div id="deleteModal" style="position: fixed; top: 0; left: 0; right: 0; bottom: 0; background-color: rgba(0, 0, 0, 0.5); justify-content: center; align-items: center; z-index: 1000; display: none;">
      <div style="background-color: white; padding: 20px; border-radius: 8px; width: 400px;">
        <h2>确认删除</h2>
        <p>确定要删除这个学生吗？</p>
        <div style="margin-top: 20px; display: flex; justify-content: flex-end; gap: 10px;">
          <button @click="closeDeleteModal" style="padding: 8px 16px; background: linear-gradient(135deg, #94a3b8, #64748b); border: none; color: white; border-radius: 4px;">取消</button>
          <button @click="confirmDelete" style="padding: 8px 16px; background: linear-gradient(135deg, #ef4444, #dc2626); border: none; color: white; border-radius: 4px;">确认删除</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { provinces, cities } from '../utils/addressData'

export default {
  name: 'StudentView',
  data() {
    return {
      // 学生数据
      students: [],
      // 学院列表
      colleges: [],
      // 专业列表
      majors: [],
      // 角色列表
      roles: [],
      // 省份列表
      provinces,
      // 城市列表
      cities,
      // 区县列表

      // 加载状态
      loading: false,
      // 模态框
      showModal: false,
      isEdit: false,
      formData: {
        studentId: null,
        name: '',
        phone: '',
        age: '',
        gender: '男',
        province: '',
        city: '',

        detail: '',
        collegeId: '',
        roleId: 1,
        majorId: ''
      },
      // 删除确认
      deleteStudentId: null
    }
  },
  mounted() {
    this.loadStudents()
    this.loadColleges()
    this.loadMajors()
    this.loadRoles()
  },
  methods: {
    onProvinceChange() {
      // 省份变化时重置城市选择
      this.formData.city = ''
    },
    getCities() {
      // 根据当前选择的省份获取城市列表
      return this.cities[this.formData.province] || []
    },

    async loadStudents() {
      this.loading = true
      try {
        // 从后端加载学生数据
        console.log('开始加载学生列表...')
        const response = await axios.get('/student/findAll')
        console.log('响应数据:', response)
        // 正确处理后端返回的数据格式
        this.students = response.data.data || []
        console.log('加载学生列表成功:', this.students)
      } catch (error) {
        console.error('加载学生列表失败:', error)
        // 加载失败时使用模拟数据
        this.students = [
          {
            studentId: 1,
            name: '张三',
            phone: '13800138000',
            age: 18,
            gender: '男',
            province: '北京市',
            city: '北京市',

            collegeName: '计算机科学与技术学院',
            majorName: '软件工程'
          },
          {
            studentId: 2,
            name: '李四',
            phone: '13900139000',
            age: 19,
            gender: '女',
            province: '上海市',
            city: '上海市',
            district: '浦东新区',
            collegeName: '外国语学院',
            majorName: '商务英语'
          },
          {
            studentId: 3,
            name: '王五',
            phone: '13700137000',
            age: 20,
            gender: '男',
            province: '广东省',
            city: '广州市',
            district: '天河区',
            collegeName: '经济与管理学院',
            majorName: '会计学'
          }
        ]
      } finally {
        this.loading = false
      }
    },
    async loadColleges() {
      try {
        // 从后端加载学院数据
        console.log('开始加载学院列表...')
        const response = await axios.get('/college/findAll')
        console.log('学院列表响应:', response)
        this.colleges = response.data.data || []
        console.log('加载学院列表成功:', this.colleges)
      } catch (error) {
        console.error('加载学院列表失败:', error)
        console.error('错误详情:', error.response)
        // 加载失败时使用模拟数据
        this.colleges = [
          { collegeId: 1, collegeName: '计算机科学与技术学院' },
          { collegeId: 2, collegeName: '经济与管理学院' },
          { collegeId: 3, collegeName: '外国语学院' },
          { collegeId: 4, collegeName: '机电工程学院' }
        ]
        console.log('使用模拟学院数据:', this.colleges)
      }
    },
    async loadMajors() {
      try {
        // 从后端加载专业数据
        console.log('开始加载专业列表...')
        const response = await axios.get('/major/findAll')
        console.log('专业列表响应:', response)
        this.majors = response.data.data || []
        console.log('加载专业列表成功:', this.majors)
      } catch (error) {
        console.error('加载专业列表失败:', error)
        console.error('错误详情:', error.response)
        // 加载失败时使用模拟数据
        this.majors = [
          { majorId: 1, majorName: '软件工程' },
          { majorId: 2, majorName: '计算机科学与技术' },
          { majorId: 3, majorName: '会计学' },
          { majorId: 4, majorName: '市场营销' },
          { majorId: 5, majorName: '商务英语' },
          { majorId: 6, majorName: '机械设计制造' }
        ]
        console.log('使用模拟专业数据:', this.majors)
      }
    },
    async loadRoles() {
      try {
        // 从后端加载角色数据
        console.log('开始加载角色列表...')
        const response = await axios.get('/role/findAll')
        console.log('角色列表响应:', response)
        this.roles = response.data.data || []
        console.log('加载角色列表成功:', this.roles)
      } catch (error) {
        console.error('加载角色列表失败:', error)
        console.error('错误详情:', error.response)
        // 加载失败时使用模拟数据
        this.roles = [
          { roleId: 1, roleName: '普通学生' },
          { roleId: 2, roleName: '班长' },
          { roleId: 3, roleName: '辅导员' }
        ]
        console.log('使用模拟角色数据:', this.roles)
      }
    },
    openAddModal() {
      console.log('Opening add modal')
      this.isEdit = false
      this.formData = {
        studentId: null,
        name: '',
        phone: '',
        age: '',
        gender: '男',
        province: '',
        city: '',

        detail: '',
        collegeId: '',
        roleId: 1,
        majorId: ''
      }
      // 显示模态框
      const modal = document.getElementById('modal')
      if (modal) {
        modal.style.display = 'flex'
        console.log('Modal displayed')
      }
    },
    openEditModal(student) {
      this.isEdit = true
      this.formData = { ...student }
      // 显示模态框
      const modal = document.getElementById('modal')
      if (modal) {
        modal.style.display = 'flex'
      }
    },
    closeModal() {
      // 隐藏模态框
      const modal = document.getElementById('modal')
      if (modal) {
        modal.style.display = 'none'
      }
    },
    async saveStudent() {
      try {
        console.log('Saving student...')
        console.log('Form data:', this.formData)
        
        // 准备地址数据
        const addressData = {
          province: this.formData.province,
          city: this.formData.city,
          detail: this.formData.detail
        }
        console.log('Address data:', addressData)
        
        // 先创建地址
        const addressResponse = await axios.post('/address/add', addressData)
        console.log('Address response:', addressResponse)
        const addressId = addressResponse.data.data.addressId
        console.log('Address ID:', addressId)
        
        // 准备提交数据
        const submitData = {
          ...this.formData,
          addressId: addressId
        }
        console.log('Submit data:', submitData)
        
        // 调用后端 API
        if (this.isEdit) {
          // 编辑现有学生
          await axios.put(`/student/update`, submitData)
          console.log('Student updated')
        } else {
          // 添加新学生
          await axios.post('/student/add', submitData)
          console.log('Student added')
        }
        
        // 隐藏模态框
        const modal = document.getElementById('modal')
        if (modal) {
          modal.style.display = 'none'
          console.log('Modal closed')
        }
        
        await this.loadStudents() // 重新加载学生列表
        console.log('Students reloaded')
        alert('操作成功')
      } catch (error) {
        console.error('保存学生失败:', error)
        alert('保存学生失败')
      }
    },
    showDeleteConfirm(studentId) {
      this.deleteStudentId = studentId
      const deleteModal = document.getElementById('deleteModal')
      if (deleteModal) {
        deleteModal.style.display = 'flex'
      }
    },
    closeDeleteModal() {
      const deleteModal = document.getElementById('deleteModal')
      if (deleteModal) {
        deleteModal.style.display = 'none'
      }
      alert('删除操作已取消')
    },
    async confirmDelete() {
      if (this.deleteStudentId) {
        try {
          // 调用后端 API 删除学生
          await axios.delete(`/student/delete/${this.deleteStudentId}`)
          await this.loadStudents() // 重新加载学生列表
          alert('删除成功')
        } catch (error) {
          console.error('删除学生失败:', error)
          alert('删除学生失败')
        } finally {
          // 关闭模态框
          const deleteModal = document.getElementById('deleteModal')
          if (deleteModal) {
            deleteModal.style.display = 'none'
          }
        }
      }
    }
  }
}
</script>

<style scoped>
/* 主容器 */
.student-view {
  min-height: 100vh;
  background: linear-gradient(135deg, #f0f4ff 0%, #e0e7ff 100%);
  padding: 20px;
  font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
}
</style>