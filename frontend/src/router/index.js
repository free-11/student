import { createRouter, createWebHistory } from 'vue-router'
import StudentView from '../views/StudentView.vue'
import UserView from '../views/UserView.vue'
import CollegeView from '../views/CollegeView.vue'
import MajorView from '../views/MajorView.vue'
import RoleView from '../views/RoleView.vue'
import TestView from '../views/TestView.vue'

const routes = [
  {
    path: '/',
    redirect: '/student'
  },
  {
    path: '/student',
    name: 'student',
    component: StudentView
  },
  {
    path: '/user',
    name: 'user',
    component: UserView
  },
  {
    path: '/college',
    name: 'college',
    component: CollegeView
  },
  {
    path: '/major',
    name: 'major',
    component: MajorView
  },
  {
    path: '/role',
    name: 'role',
    component: RoleView
  },
  {
    path: '/test',
    name: 'test',
    component: TestView
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router