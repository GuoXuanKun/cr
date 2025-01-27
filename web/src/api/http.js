import axios from 'axios'

const http = axios.create({
  baseURL: 'http://localhost:8000',
  timeout: 5000
})

// Request interceptor
http.interceptors.request.use(
  config => {
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// Response interceptor
http.interceptors.response.use(
  response => {
    return response.data
  },
  error => {
    return Promise.reject(error)
  }
)

export default http
