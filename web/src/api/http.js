import axios from 'axios'
import {useUserStore} from "@/stores/user";
import router from "@/router";

const http = axios.create({
  baseURL: "http://localhost:8000",
  timeout: 5000
})

// Request interceptor
http.interceptors.request.use(
  config => {
    const userStore = useUserStore()
    // 如果有 Token , 将它添加到请求头
    if (userStore.token) {
      config.headers.Authorization = `Bearer ${userStore.token}`
    }
    console.log('请求: ', config)
    return config
  },
  error => {
    console.error('请求错误: ', error)
    return Promise.reject(error)
  }
)

// Response interceptor
http.interceptors.response.use(
  response => {
    console.log('响应: ', response)
    return response.data
  },
  error => {
    console.error('响应错误: ', error)

    // 如果是 401 未授权错误（未登录或 token 失效）
    if (error.response?.status === 401) {
      const userStore = useUserStore()
      // 清除用户信息
      userStore.clearUserInfo()
      // 跳转到登录页
      router.push('/login')
    }

    return Promise.reject(error)
  }
)

export default http
