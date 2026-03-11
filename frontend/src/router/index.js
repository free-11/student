import { createRouter, createWebHistory } from 'vue-router'
import StudentView from '../views/StudentView.vue'
import UserView from '../views/UserView.vue'
import CollegeView from '../views/CollegeView.vue'
import MajorView from '../views/MajorView.vue'
import RoleView from '../views/RoleView.vue'
import TestView from '../views/TestView.vue'
import LoginView from '../views/LoginView.vue'

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'login',
    component: LoginView
  },
  {
    path: '/student',
    name: 'student',
    component: StudentView,
    meta: { requiresAuth: true }
  },
  {
    path: '/user',
    name: 'user',
    component: UserView,
    meta: { requiresAuth: true }
  },
  {
    path: '/college',
    name: 'college',
    component: CollegeView,
    meta: { requiresAuth: true }
  },
  {
    path: '/major',
    name: 'major',
    component: MajorView,
    meta: { requiresAuth: true }
  },
  {
    path: '/role',
    name: 'role',
    component: RoleView,
    meta: { requiresAuth: true }
  },
  {
    path: '/test',
    name: 'test',
    component: TestView,
    meta: { requiresAuth: true }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  // 检查路由是否需要认证
  if (to.matched.some(record => record.meta.requiresAuth)) {
    // 检查是否有token
    const token = localStorage.getItem('token')
    if (!token) {
      // 没有token，跳转到登录页面
      next({ path: '/login' })
    } else {
      // 有token，继续访问
      next()
    }
  } else {
    // 不需要认证的路由，直接访问
    next()
  }
})

export default router