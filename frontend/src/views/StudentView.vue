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
          <th style="border: 1px solid #ddd; padding: 8px;">区县</th>
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
          <td style="border: 1px solid #ddd; padding: 8px;">{{ student.district }}</td>
          <td style="border: 1px solid #ddd; padding: 8px;">{{ student.collegeName }}</td>
          <td style="border: 1px solid #ddd; padding: 8px;">{{ student.majorName }}</td>
          <td style="border: 1px solid #ddd; padding: 8px;">
            <button @click="openEditModal(student)" style="margin-right: 5px; padding: 4px 8px; background: linear-gradient(135deg, #f59e0b, #d97706); border: none; color: white; border-radius: 4px; font-size: 12px;">
              编辑
            </button>
            <button @click="deleteStudent(student.studentId)" style="padding: 4px 8px; background: linear-gradient(135deg, #ef4444, #dc2626); border: none; color: white; border-radius: 4px; font-size: 12px;">
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
            <label style="display: block; margin-bottom: 5px;">区县</label>
            <select v-model="formData.district" style="width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px;" required>
              <option value="">请选择区县</option>
              <option v-for="district in getDistricts()" :key="district.value" :value="district.value">{{ district.label }}</option>
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
  </div>
</template>

<script>
import axios from 'axios'

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
      // 省份列表
      provinces: [
        { value: '北京市', label: '北京市' },
        { value: '上海市', label: '上海市' },
        { value: '广东省', label: '广东省' },
        { value: '江苏省', label: '江苏省' },
        { value: '浙江省', label: '浙江省' },
        { value: '山东省', label: '山东省' },
        { value: '河南省', label: '河南省' },
        { value: '湖北省', label: '湖北省' },
        { value: '湖南省', label: '湖南省' },
        { value: '四川省', label: '四川省' }
      ],
      // 城市列表
      cities: {},
      // 区县列表
      districts: {},
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
        district: '',
        detail: '',
        collegeId: '',
        roleId: 1,
        majorId: ''
      }
    }
  },
  mounted() {
    this.loadStudents()
    this.loadColleges()
    this.loadMajors()
    this.initCities()
  },
  methods: {
    initCities() {
      // 初始化城市数据（全国省份）
      this.cities = {
        '北京市': [{ value: '北京市', label: '北京市' }],
        '天津市': [{ value: '天津市', label: '天津市' }],
        '河北省': [
          { value: '石家庄市', label: '石家庄市' },
          { value: '唐山市', label: '唐山市' },
          { value: '秦皇岛市', label: '秦皇岛市' },
          { value: '邯郸市', label: '邯郸市' },
          { value: '邢台市', label: '邢台市' },
          { value: '保定市', label: '保定市' },
          { value: '张家口市', label: '张家口市' },
          { value: '承德市', label: '承德市' },
          { value: '沧州市', label: '沧州市' },
          { value: '廊坊市', label: '廊坊市' },
          { value: '衡水市', label: '衡水市' }
        ],
        '山西省': [
          { value: '太原市', label: '太原市' },
          { value: '大同市', label: '大同市' },
          { value: '阳泉市', label: '阳泉市' },
          { value: '长治市', label: '长治市' },
          { value: '晋城市', label: '晋城市' },
          { value: '朔州市', label: '朔州市' },
          { value: '晋中市', label: '晋中市' },
          { value: '运城市', label: '运城市' },
          { value: '忻州市', label: '忻州市' },
          { value: '临汾市', label: '临汾市' },
          { value: '吕梁市', label: '吕梁市' }
        ],
        '内蒙古自治区': [
          { value: '呼和浩特市', label: '呼和浩特市' },
          { value: '包头市', label: '包头市' },
          { value: '乌海市', label: '乌海市' },
          { value: '赤峰市', label: '赤峰市' },
          { value: '通辽市', label: '通辽市' },
          { value: '鄂尔多斯市', label: '鄂尔多斯市' },
          { value: '呼伦贝尔市', label: '呼伦贝尔市' },
          { value: '巴彦淖尔市', label: '巴彦淖尔市' },
          { value: '乌兰察布市', label: '乌兰察布市' },
          { value: '兴安盟', label: '兴安盟' },
          { value: '锡林郭勒盟', label: '锡林郭勒盟' },
          { value: '阿拉善盟', label: '阿拉善盟' }
        ],
        '辽宁省': [
          { value: '沈阳市', label: '沈阳市' },
          { value: '大连市', label: '大连市' },
          { value: '鞍山市', label: '鞍山市' },
          { value: '抚顺市', label: '抚顺市' },
          { value: '本溪市', label: '本溪市' },
          { value: '丹东市', label: '丹东市' },
          { value: '锦州市', label: '锦州市' },
          { value: '营口市', label: '营口市' },
          { value: '阜新市', label: '阜新市' },
          { value: '辽阳市', label: '辽阳市' },
          { value: '盘锦市', label: '盘锦市' },
          { value: '铁岭市', label: '铁岭市' },
          { value: '朝阳市', label: '朝阳市' },
          { value: '葫芦岛市', label: '葫芦岛市' }
        ],
        '吉林省': [
          { value: '长春市', label: '长春市' },
          { value: '吉林市', label: '吉林市' },
          { value: '四平市', label: '四平市' },
          { value: '辽源市', label: '辽源市' },
          { value: '通化市', label: '通化市' },
          { value: '白山市', label: '白山市' },
          { value: '松原市', label: '松原市' },
          { value: '白城市', label: '白城市' },
          { value: '延边朝鲜族自治州', label: '延边朝鲜族自治州' }
        ],
        '黑龙江省': [
          { value: '哈尔滨市', label: '哈尔滨市' },
          { value: '齐齐哈尔市', label: '齐齐哈尔市' },
          { value: '鸡西市', label: '鸡西市' },
          { value: '鹤岗市', label: '鹤岗市' },
          { value: '双鸭山市', label: '双鸭山市' },
          { value: '大庆市', label: '大庆市' },
          { value: '伊春市', label: '伊春市' },
          { value: '佳木斯市', label: '佳木斯市' },
          { value: '七台河市', label: '七台河市' },
          { value: '牡丹江市', label: '牡丹江市' },
          { value: '黑河市', label: '黑河市' },
          { value: '绥化市', label: '绥化市' },
          { value: '大兴安岭地区', label: '大兴安岭地区' }
        ],
        '上海市': [{ value: '上海市', label: '上海市' }],
        '江苏省': [
          { value: '南京市', label: '南京市' },
          { value: '无锡市', label: '无锡市' },
          { value: '徐州市', label: '徐州市' },
          { value: '常州市', label: '常州市' },
          { value: '苏州市', label: '苏州市' },
          { value: '南通市', label: '南通市' },
          { value: '连云港市', label: '连云港市' },
          { value: '淮安市', label: '淮安市' },
          { value: '盐城市', label: '盐城市' },
          { value: '扬州市', label: '扬州市' },
          { value: '镇江市', label: '镇江市' },
          { value: '泰州市', label: '泰州市' },
          { value: '宿迁市', label: '宿迁市' }
        ],
        '浙江省': [
          { value: '杭州市', label: '杭州市' },
          { value: '宁波市', label: '宁波市' },
          { value: '温州市', label: '温州市' },
          { value: '嘉兴市', label: '嘉兴市' },
          { value: '湖州市', label: '湖州市' },
          { value: '绍兴市', label: '绍兴市' },
          { value: '金华市', label: '金华市' },
          { value: '衢州市', label: '衢州市' },
          { value: '舟山市', label: '舟山市' },
          { value: '台州市', label: '台州市' },
          { value: '丽水市', label: '丽水市' }
        ],
        '安徽省': [
          { value: '合肥市', label: '合肥市' },
          { value: '芜湖市', label: '芜湖市' },
          { value: '蚌埠市', label: '蚌埠市' },
          { value: '淮南市', label: '淮南市' },
          { value: '马鞍山市', label: '马鞍山市' },
          { value: '淮北市', label: '淮北市' },
          { value: '铜陵市', label: '铜陵市' },
          { value: '安庆市', label: '安庆市' },
          { value: '黄山市', label: '黄山市' },
          { value: '滁州市', label: '滁州市' },
          { value: '阜阳市', label: '阜阳市' },
          { value: '宿州市', label: '宿州市' },
          { value: '六安市', label: '六安市' },
          { value: '亳州市', label: '亳州市' },
          { value: '池州市', label: '池州市' },
          { value: '宣城市', label: '宣城市' }
        ],
        '福建省': [
          { value: '福州市', label: '福州市' },
          { value: '厦门市', label: '厦门市' },
          { value: '莆田市', label: '莆田市' },
          { value: '三明市', label: '三明市' },
          { value: '泉州市', label: '泉州市' },
          { value: '漳州市', label: '漳州市' },
          { value: '南平市', label: '南平市' },
          { value: '龙岩市', label: '龙岩市' },
          { value: '宁德市', label: '宁德市' }
        ],
        '江西省': [
          { value: '南昌市', label: '南昌市' },
          { value: '景德镇市', label: '景德镇市' },
          { value: '萍乡市', label: '萍乡市' },
          { value: '九江市', label: '九江市' },
          { value: '新余市', label: '新余市' },
          { value: '鹰潭市', label: '鹰潭市' },
          { value: '赣州市', label: '赣州市' },
          { value: '吉安市', label: '吉安市' },
          { value: '宜春市', label: '宜春市' },
          { value: '抚州市', label: '抚州市' },
          { value: '上饶市', label: '上饶市' }
        ],
        '山东省': [
          { value: '济南市', label: '济南市' },
          { value: '青岛市', label: '青岛市' },
          { value: '淄博市', label: '淄博市' },
          { value: '枣庄市', label: '枣庄市' },
          { value: '东营市', label: '东营市' },
          { value: '烟台市', label: '烟台市' },
          { value: '潍坊市', label: '潍坊市' },
          { value: '济宁市', label: '济宁市' },
          { value: '泰安市', label: '泰安市' },
          { value: '威海市', label: '威海市' },
          { value: '日照市', label: '日照市' },
          { value: '临沂市', label: '临沂市' },
          { value: '德州市', label: '德州市' },
          { value: '聊城市', label: '聊城市' },
          { value: '滨州市', label: '滨州市' },
          { value: '菏泽市', label: '菏泽市' }
        ],
        '河南省': [
          { value: '郑州市', label: '郑州市' },
          { value: '开封市', label: '开封市' },
          { value: '洛阳市', label: '洛阳市' },
          { value: '平顶山市', label: '平顶山市' },
          { value: '安阳市', label: '安阳市' },
          { value: '鹤壁市', label: '鹤壁市' },
          { value: '新乡市', label: '新乡市' },
          { value: '焦作市', label: '焦作市' },
          { value: '濮阳市', label: '濮阳市' },
          { value: '许昌市', label: '许昌市' },
          { value: '漯河市', label: '漯河市' },
          { value: '三门峡市', label: '三门峡市' },
          { value: '南阳市', label: '南阳市' },
          { value: '商丘市', label: '商丘市' },
          { value: '信阳市', label: '信阳市' },
          { value: '周口市', label: '周口市' },
          { value: '驻马店市', label: '驻马店市' }
        ],
        '湖北省': [
          { value: '武汉市', label: '武汉市' },
          { value: '黄石市', label: '黄石市' },
          { value: '十堰市', label: '十堰市' },
          { value: '宜昌市', label: '宜昌市' },
          { value: '襄阳市', label: '襄阳市' },
          { value: '鄂州市', label: '鄂州市' },
          { value: '荆门市', label: '荆门市' },
          { value: '孝感市', label: '孝感市' },
          { value: '荆州市', label: '荆州市' },
          { value: '黄冈市', label: '黄冈市' },
          { value: '咸宁市', label: '咸宁市' },
          { value: '随州市', label: '随州市' },
          { value: '恩施土家族苗族自治州', label: '恩施土家族苗族自治州' },
          { value: '仙桃市', label: '仙桃市' },
          { value: '潜江市', label: '潜江市' },
          { value: '天门市', label: '天门市' },
          { value: '神农架林区', label: '神农架林区' }
        ],
        '湖南省': [
          { value: '长沙市', label: '长沙市' },
          { value: '株洲市', label: '株洲市' },
          { value: '湘潭市', label: '湘潭市' },
          { value: '衡阳市', label: '衡阳市' },
          { value: '邵阳市', label: '邵阳市' },
          { value: '岳阳市', label: '岳阳市' },
          { value: '常德市', label: '常德市' },
          { value: '张家界市', label: '张家界市' },
          { value: '益阳市', label: '益阳市' },
          { value: '郴州市', label: '郴州市' },
          { value: '永州市', label: '永州市' },
          { value: '怀化市', label: '怀化市' },
          { value: '娄底市', label: '娄底市' },
          { value: '湘西土家族苗族自治州', label: '湘西土家族苗族自治州' }
        ],
        '广东省': [
          { value: '广州市', label: '广州市' },
          { value: '韶关市', label: '韶关市' },
          { value: '深圳市', label: '深圳市' },
          { value: '珠海市', label: '珠海市' },
          { value: '汕头市', label: '汕头市' },
          { value: '佛山市', label: '佛山市' },
          { value: '江门市', label: '江门市' },
          { value: '湛江市', label: '湛江市' },
          { value: '茂名市', label: '茂名市' },
          { value: '肇庆市', label: '肇庆市' },
          { value: '惠州市', label: '惠州市' },
          { value: '梅州市', label: '梅州市' },
          { value: '汕尾市', label: '汕尾市' },
          { value: '河源市', label: '河源市' },
          { value: '阳江市', label: '阳江市' },
          { value: '清远市', label: '清远市' },
          { value: '东莞市', label: '东莞市' },
          { value: '中山市', label: '中山市' },
          { value: '潮州市', label: '潮州市' },
          { value: '揭阳市', label: '揭阳市' },
          { value: '云浮市', label: '云浮市' }
        ],
        '广西壮族自治区': [
          { value: '南宁市', label: '南宁市' },
          { value: '柳州市', label: '柳州市' },
          { value: '桂林市', label: '桂林市' },
          { value: '梧州市', label: '梧州市' },
          { value: '北海市', label: '北海市' },
          { value: '防城港市', label: '防城港市' },
          { value: '钦州市', label: '钦州市' },
          { value: '贵港市', label: '贵港市' },
          { value: '玉林市', label: '玉林市' },
          { value: '百色市', label: '百色市' },
          { value: '贺州市', label: '贺州市' },
          { value: '河池市', label: '河池市' },
          { value: '来宾市', label: '来宾市' },
          { value: '崇左市', label: '崇左市' }
        ],
        '海南省': [
          { value: '海口市', label: '海口市' },
          { value: '三亚市', label: '三亚市' },
          { value: '三沙市', label: '三沙市' },
          { value: '儋州市', label: '儋州市' },
          { value: '五指山市', label: '五指山市' },
          { value: '琼海市', label: '琼海市' },
          { value: '文昌市', label: '文昌市' },
          { value: '万宁市', label: '万宁市' },
          { value: '东方市', label: '东方市' },
          { value: '定安县', label: '定安县' },
          { value: '屯昌县', label: '屯昌县' },
          { value: '澄迈县', label: '澄迈县' },
          { value: '临高县', label: '临高县' },
          { value: '白沙黎族自治县', label: '白沙黎族自治县' },
          { value: '昌江黎族自治县', label: '昌江黎族自治县' },
          { value: '乐东黎族自治县', label: '乐东黎族自治县' },
          { value: '陵水黎族自治县', label: '陵水黎族自治县' },
          { value: '保亭黎族苗族自治县', label: '保亭黎族苗族自治县' },
          { value: '琼中黎族苗族自治县', label: '琼中黎族苗族自治县' }
        ],
        '重庆市': [{ value: '重庆市', label: '重庆市' }],
        '四川省': [
          { value: '成都市', label: '成都市' },
          { value: '自贡市', label: '自贡市' },
          { value: '攀枝花市', label: '攀枝花市' },
          { value: '泸州市', label: '泸州市' },
          { value: '德阳市', label: '德阳市' },
          { value: '绵阳市', label: '绵阳市' },
          { value: '广元市', label: '广元市' },
          { value: '遂宁市', label: '遂宁市' },
          { value: '内江市', label: '内江市' },
          { value: '乐山市', label: '乐山市' },
          { value: '南充市', label: '南充市' },
          { value: '眉山市', label: '眉山市' },
          { value: '宜宾市', label: '宜宾市' },
          { value: '广安市', label: '广安市' },
          { value: '达州市', label: '达州市' },
          { value: '雅安市', label: '雅安市' },
          { value: '巴中市', label: '巴中市' },
          { value: '资阳市', label: '资阳市' },
          { value: '阿坝藏族羌族自治州', label: '阿坝藏族羌族自治州' },
          { value: '甘孜藏族自治州', label: '甘孜藏族自治州' },
          { value: '凉山彝族自治州', label: '凉山彝族自治州' }
        ],
        '贵州省': [
          { value: '贵阳市', label: '贵阳市' },
          { value: '六盘水市', label: '六盘水市' },
          { value: '遵义市', label: '遵义市' },
          { value: '安顺市', label: '安顺市' },
          { value: '毕节市', label: '毕节市' },
          { value: '铜仁市', label: '铜仁市' },
          { value: '黔西南布依族苗族自治州', label: '黔西南布依族苗族自治州' },
          { value: '黔东南苗族侗族自治州', label: '黔东南苗族侗族自治州' },
          { value: '黔南布依族苗族自治州', label: '黔南布依族苗族自治州' }
        ],
        '云南省': [
          { value: '昆明市', label: '昆明市' },
          { value: '曲靖市', label: '曲靖市' },
          { value: '玉溪市', label: '玉溪市' },
          { value: '保山市', label: '保山市' },
          { value: '昭通市', label: '昭通市' },
          { value: '丽江市', label: '丽江市' },
          { value: '普洱市', label: '普洱市' },
          { value: '临沧市', label: '临沧市' },
          { value: '楚雄彝族自治州', label: '楚雄彝族自治州' },
          { value: '红河哈尼族彝族自治州', label: '红河哈尼族彝族自治州' },
          { value: '文山壮族苗族自治州', label: '文山壮族苗族自治州' },
          { value: '西双版纳傣族自治州', label: '西双版纳傣族自治州' },
          { value: '大理白族自治州', label: '大理白族自治州' },
          { value: '德宏傣族景颇族自治州', label: '德宏傣族景颇族自治州' },
          { value: '怒江傈僳族自治州', label: '怒江傈僳族自治州' },
          { value: '迪庆藏族自治州', label: '迪庆藏族自治州' }
        ],
        '西藏自治区': [
          { value: '拉萨市', label: '拉萨市' },
          { value: '日喀则市', label: '日喀则市' },
          { value: '昌都市', label: '昌都市' },
          { value: '林芝市', label: '林芝市' },
          { value: '山南市', label: '山南市' },
          { value: '那曲市', label: '那曲市' },
          { value: '阿里地区', label: '阿里地区' }
        ],
        '陕西省': [
          { value: '西安市', label: '西安市' },
          { value: '铜川市', label: '铜川市' },
          { value: '宝鸡市', label: '宝鸡市' },
          { value: '咸阳市', label: '咸阳市' },
          { value: '渭南市', label: '渭南市' },
          { value: '延安市', label: '延安市' },
          { value: '汉中市', label: '汉中市' },
          { value: '榆林市', label: '榆林市' },
          { value: '安康市', label: '安康市' },
          { value: '商洛市', label: '商洛市' }
        ],
        '甘肃省': [
          { value: '兰州市', label: '兰州市' },
          { value: '嘉峪关市', label: '嘉峪关市' },
          { value: '金昌市', label: '金昌市' },
          { value: '白银市', label: '白银市' },
          { value: '天水市', label: '天水市' },
          { value: '武威市', label: '武威市' },
          { value: '张掖市', label: '张掖市' },
          { value: '平凉市', label: '平凉市' },
          { value: '酒泉市', label: '酒泉市' },
          { value: '庆阳市', label: '庆阳市' },
          { value: '定西市', label: '定西市' },
          { value: '陇南市', label: '陇南市' },
          { value: '临夏回族自治州', label: '临夏回族自治州' },
          { value: '甘南藏族自治州', label: '甘南藏族自治州' }
        ],
        '青海省': [
          { value: '西宁市', label: '西宁市' },
          { value: '海东市', label: '海东市' },
          { value: '海北藏族自治州', label: '海北藏族自治州' },
          { value: '黄南藏族自治州', label: '黄南藏族自治州' },
          { value: '海南藏族自治州', label: '海南藏族自治州' },
          { value: '果洛藏族自治州', label: '果洛藏族自治州' },
          { value: '玉树藏族自治州', label: '玉树藏族自治州' },
          { value: '海西蒙古族藏族自治州', label: '海西蒙古族藏族自治州' }
        ],
        '宁夏回族自治区': [
          { value: '银川市', label: '银川市' },
          { value: '石嘴山市', label: '石嘴山市' },
          { value: '吴忠市', label: '吴忠市' },
          { value: '固原市', label: '固原市' },
          { value: '中卫市', label: '中卫市' }
        ],
        '新疆维吾尔自治区': [
          { value: '乌鲁木齐市', label: '乌鲁木齐市' },
          { value: '克拉玛依市', label: '克拉玛依市' },
          { value: '吐鲁番市', label: '吐鲁番市' },
          { value: '哈密市', label: '哈密市' },
          { value: '昌吉回族自治州', label: '昌吉回族自治州' },
          { value: '博尔塔拉蒙古自治州', label: '博尔塔拉蒙古自治州' },
          { value: '巴音郭楞蒙古自治州', label: '巴音郭楞蒙古自治州' },
          { value: '阿克苏地区', label: '阿克苏地区' },
          { value: '克孜勒苏柯尔克孜自治州', label: '克孜勒苏柯尔克孜自治州' },
          { value: '喀什地区', label: '喀什地区' },
          { value: '和田地区', label: '和田地区' },
          { value: '伊犁哈萨克自治州', label: '伊犁哈萨克自治州' },
          { value: '塔城地区', label: '塔城地区' },
          { value: '阿勒泰地区', label: '阿勒泰地区' },
          { value: '石河子市', label: '石河子市' },
          { value: '阿拉尔市', label: '阿拉尔市' },
          { value: '图木舒克市', label: '图木舒克市' },
          { value: '五家渠市', label: '五家渠市' },
          { value: '北屯市', label: '北屯市' },
          { value: '铁门关市', label: '铁门关市' },
          { value: '双河市', label: '双河市' },
          { value: '可克达拉市', label: '可克达拉市' },
          { value: '昆玉市', label: '昆玉市' }
        ]
      }
      
      // 初始化区县数据（主要城市的区县）
      this.districts = {
        '北京市': {
          '北京市': [
            { value: '东城区', label: '东城区' },
            { value: '西城区', label: '西城区' },
            { value: '朝阳区', label: '朝阳区' },
            { value: '海淀区', label: '海淀区' },
            { value: '丰台区', label: '丰台区' },
            { value: '石景山区', label: '石景山区' },
            { value: '门头沟区', label: '门头沟区' },
            { value: '房山区', label: '房山区' },
            { value: '通州区', label: '通州区' },
            { value: '顺义区', label: '顺义区' },
            { value: '昌平区', label: '昌平区' },
            { value: '大兴区', label: '大兴区' },
            { value: '怀柔区', label: '怀柔区' },
            { value: '平谷区', label: '平谷区' },
            { value: '密云区', label: '密云区' },
            { value: '延庆区', label: '延庆区' }
          ]
        },
        '上海市': {
          '上海市': [
            { value: '黄浦区', label: '黄浦区' },
            { value: '徐汇区', label: '徐汇区' },
            { value: '长宁区', label: '长宁区' },
            { value: '静安区', label: '静安区' },
            { value: '普陀区', label: '普陀区' },
            { value: '虹口区', label: '虹口区' },
            { value: '杨浦区', label: '杨浦区' },
            { value: '闵行区', label: '闵行区' },
            { value: '宝山区', label: '宝山区' },
            { value: '嘉定区', label: '嘉定区' },
            { value: '浦东新区', label: '浦东新区' },
            { value: '金山区', label: '金山区' },
            { value: '松江区', label: '松江区' },
            { value: '青浦区', label: '青浦区' },
            { value: '奉贤区', label: '奉贤区' },
            { value: '崇明区', label: '崇明区' }
          ]
        },
        '广州市': {
          '广州市': [
            { value: '越秀区', label: '越秀区' },
            { value: '海珠区', label: '海珠区' },
            { value: '荔湾区', label: '荔湾区' },
            { value: '天河区', label: '天河区' },
            { value: '白云区', label: '白云区' },
            { value: '黄埔区', label: '黄埔区' },
            { value: '番禺区', label: '番禺区' },
            { value: '花都区', label: '花都区' },
            { value: '南沙区', label: '南沙区' },
            { value: '从化区', label: '从化区' },
            { value: '增城区', label: '增城区' }
          ]
        },
        '深圳市': {
          '深圳市': [
            { value: '罗湖区', label: '罗湖区' },
            { value: '福田区', label: '福田区' },
            { value: '南山区', label: '南山区' },
            { value: '宝安区', label: '宝安区' },
            { value: '龙岗区', label: '龙岗区' },
            { value: '盐田区', label: '盐田区' },
            { value: '龙华区', label: '龙华区' },
            { value: '坪山区', label: '坪山区' },
            { value: '光明区', label: '光明区' },
            { value: '大鹏新区', label: '大鹏新区' }
          ]
        },
        '成都市': {
          '成都市': [
            { value: '锦江区', label: '锦江区' },
            { value: '青羊区', label: '青羊区' },
            { value: '金牛区', label: '金牛区' },
            { value: '武侯区', label: '武侯区' },
            { value: '成华区', label: '成华区' },
            { value: '龙泉驿区', label: '龙泉驿区' },
            { value: '青白江区', label: '青白江区' },
            { value: '新都区', label: '新都区' },
            { value: '温江区', label: '温江区' },
            { value: '双流区', label: '双流区' },
            { value: '郫都区', label: '郫都区' },
            { value: '金堂县', label: '金堂县' },
            { value: '大邑县', label: '大邑县' },
            { value: '蒲江县', label: '蒲江县' },
            { value: '新津县', label: '新津县' },
            { value: '都江堰市', label: '都江堰市' },
            { value: '彭州市', label: '彭州市' },
            { value: '邛崃市', label: '邛崃市' },
            { value: '崇州市', label: '崇州市' },
            { value: '简阳市', label: '简阳市' }
          ]
        },
        '杭州市': {
          '杭州市': [
            { value: '上城区', label: '上城区' },
            { value: '下城区', label: '下城区' },
            { value: '江干区', label: '江干区' },
            { value: '拱墅区', label: '拱墅区' },
            { value: '西湖区', label: '西湖区' },
            { value: '滨江区', label: '滨江区' },
            { value: '萧山区', label: '萧山区' },
            { value: '余杭区', label: '余杭区' },
            { value: '富阳区', label: '富阳区' },
            { value: '临安区', label: '临安区' },
            { value: '桐庐县', label: '桐庐县' },
            { value: '淳安县', label: '淳安县' },
            { value: '建德市', label: '建德市' }
          ]
        },
        '武汉市': {
          '武汉市': [
            { value: '江岸区', label: '江岸区' },
            { value: '江汉区', label: '江汉区' },
            { value: '硚口区', label: '硚口区' },
            { value: '汉阳区', label: '汉阳区' },
            { value: '武昌区', label: '武昌区' },
            { value: '青山区', label: '青山区' },
            { value: '洪山区', label: '洪山区' },
            { value: '东西湖区', label: '东西湖区' },
            { value: '汉南区', label: '汉南区' },
            { value: '蔡甸区', label: '蔡甸区' },
            { value: '江夏区', label: '江夏区' },
            { value: '黄陂区', label: '黄陂区' },
            { value: '新洲区', label: '新洲区' }
          ]
        },
        '重庆市': {
          '重庆市': [
            { value: '万州区', label: '万州区' },
            { value: '涪陵区', label: '涪陵区' },
            { value: '渝中区', label: '渝中区' },
            { value: '大渡口区', label: '大渡口区' },
            { value: '江北区', label: '江北区' },
            { value: '沙坪坝区', label: '沙坪坝区' },
            { value: '九龙坡区', label: '九龙坡区' },
            { value: '南岸区', label: '南岸区' },
            { value: '北碚区', label: '北碚区' },
            { value: '綦江区', label: '綦江区' },
            { value: '大足区', label: '大足区' },
            { value: '渝北区', label: '渝北区' },
            { value: '巴南区', label: '巴南区' },
            { value: '黔江区', label: '黔江区' },
            { value: '长寿区', label: '长寿区' },
            { value: '江津区', label: '江津区' },
            { value: '合川区', label: '合川区' },
            { value: '永川区', label: '永川区' },
            { value: '南川区', label: '南川区' },
            { value: '璧山区', label: '璧山区' },
            { value: '铜梁区', label: '铜梁区' },
            { value: '潼南区', label: '潼南区' },
            { value: '荣昌区', label: '荣昌区' },
            { value: '开州区', label: '开州区' },
            { value: '梁平区', label: '梁平区' },
            { value: '武隆区', label: '武隆区' },
            { value: '城口县', label: '城口县' },
            { value: '丰都县', label: '丰都县' },
            { value: '垫江县', label: '垫江县' },
            { value: '忠县', label: '忠县' },
            { value: '云阳县', label: '云阳县' },
            { value: '奉节县', label: '奉节县' },
            { value: '巫山县', label: '巫山县' },
            { value: '巫溪县', label: '巫溪县' },
            { value: '石柱土家族自治县', label: '石柱土家族自治县' },
            { value: '秀山土家族苗族自治县', label: '秀山土家族苗族自治县' },
            { value: '酉阳土家族苗族自治县', label: '酉阳土家族苗族自治县' },
            { value: '彭水苗族土家族自治县', label: '彭水苗族土家族自治县' }
          ]
        },
        '天津市': {
          '天津市': [
            { value: '和平区', label: '和平区' },
            { value: '河东区', label: '河东区' },
            { value: '河西区', label: '河西区' },
            { value: '南开区', label: '南开区' },
            { value: '河北区', label: '河北区' },
            { value: '红桥区', label: '红桥区' },
            { value: '东丽区', label: '东丽区' },
            { value: '西青区', label: '西青区' },
            { value: '津南区', label: '津南区' },
            { value: '北辰区', label: '北辰区' },
            { value: '武清区', label: '武清区' },
            { value: '宝坻区', label: '宝坻区' },
            { value: '滨海新区', label: '滨海新区' },
            { value: '宁河区', label: '宁河区' },
            { value: '静海区', label: '静海区' },
            { value: '蓟州区', label: '蓟州区' }
          ]
        }
      }
    },
    onProvinceChange() {
      // 省份变化时重置城市和区县选择
      this.formData.city = ''
      this.formData.district = ''
    },
    onCityChange() {
      // 城市变化时重置区县选择
      this.formData.district = ''
    },
    getCities() {
      // 根据当前选择的省份获取城市列表
      return this.cities[this.formData.province] || []
    },
    getDistricts() {
      // 根据当前选择的省份和城市获取区县列表
      if (!this.formData.province || !this.formData.city) {
        return []
      }
      return this.districts[this.formData.province]?.[this.formData.city] || []
    },
    async loadStudents() {
      this.loading = true
      try {
        // 从后端加载学生数据
        const response = await axios.get('/api/student/findAll')
        // 正确处理后端返回的数据格式
        this.students = response.data.data || []
      } catch (error) {
        console.error('加载学生列表失败:', error)
        // 加载失败时使用模拟数据
        this.students = [
          {
            studentId: 1,
            name: '张三',
            phone: '13800138000',
            age: 18,
            gender: '男'
          },
          {
            studentId: 2,
            name: '李四',
            phone: '13900139000',
            age: 19,
            gender: '女'
          },
          {
            studentId: 3,
            name: '王五',
            phone: '13700137000',
            age: 20,
            gender: '男'
          }
        ]
      } finally {
        this.loading = false
      }
    },
    async loadColleges() {
      try {
        // 从后端加载学院数据
        const response = await axios.get('/api/college/findAll')
        this.colleges = response.data.data || []
        console.log('加载学院列表成功:', this.colleges)
      } catch (error) {
        console.error('加载学院列表失败:', error)
      }
    },
    async loadMajors() {
      try {
        // 从后端加载专业数据
        const response = await axios.get('/api/major/findAll')
        this.majors = response.data.data || []
        console.log('加载专业列表成功:', this.majors)
      } catch (error) {
        console.error('加载专业列表失败:', error)
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
        district: '',
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
          district: this.formData.district,
          detail: this.formData.detail
        }
        console.log('Address data:', addressData)
        
        // 先创建地址
        const addressResponse = await axios.post('/api/address/add', addressData)
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
          await axios.put(`/api/student/update`, submitData)
          console.log('Student updated')
        } else {
          // 添加新学生
          await axios.post('/api/student/add', submitData)
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
    async deleteStudent(studentId) {
      if (confirm('确定要删除这个学生吗？')) {
        try {
          // 调用后端 API 删除学生
          await axios.delete(`/api/student/delete/${studentId}`)
          await this.loadStudents() // 重新加载学生列表
          alert('删除成功')
        } catch (error) {
          console.error('删除学生失败:', error)
          alert('删除学生失败')
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