import axios from 'axios'

const http = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  timeout: 5000
})

// Request interceptor
http.interceptors.request.use(
  config => {
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
    return Promise.reject(error)
  }
)

export default http
