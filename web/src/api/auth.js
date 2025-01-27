import http from './http'

export function sendCode(mobile) {
  return http.post('/send-code', { mobile })
}
