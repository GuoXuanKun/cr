import { createRouter, createWebHistory } from 'vue-router'
import AdminLayout from '../layout/AdminLayout.vue'
import {useUserStore} from "@/stores/user";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      component: AdminLayout,
      redirect: '/dashboard',
      children: [
        {
          path: 'dashboard',
          name: 'Dashboard',
          component: () => import('../views/Dashboard.vue'),
          meta: { title: '仪表盘' }
        },
        {
          path: 'users',
          name: 'Users',
          component: () => import('../views/Users.vue'),
          meta: { title: '用户管理' }
        },
        {
          path: 'roles',
          name: 'Roles',
          component: () => import('../views/Roles.vue'),
          meta: { title: '角色管理' }
        }
      ]
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/Login.vue'),
      meta: {
        title: '登录',
        requiresAuth: false
      }
    },
  ],
})

// 全局前置守卫
router.beforeEach((to, from, next) => {
  const userStore = useUserStore()

  // 如果页面需要认证（默认都需要认证，除非明确标记不需要）
  if (to.meta.requiresAuth !== false) {
    // 检查是否有 token
    if (!userStore.token) {
      // 如果没有 token，重定向到登录页
      next({
        path: '/login',
        query: { redirect: to.fullPath }
      })
      return
    }
  }

  // 如果已登录且要访问登录页，重定向到首页
  // if (to.path === '/login' && userStore.token) {
  //   next({ path: '/' })
  //   return
  // }

  next()
})

export default router
