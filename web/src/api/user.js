import http from './http'

export function getUserCount() {
  return http.get('/user/count')
}
