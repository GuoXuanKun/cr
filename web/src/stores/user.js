import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useUserStore = defineStore('user', () => {
  const id = ref('')
  const mobile = ref('')
  const token = ref('')

  const setUserInfo = (userInfo) => {
    id.value = userInfo.id
    mobile.value = userInfo.mobile
    token.value = userInfo.token
  }

  const clearUserInfo = () => {
    id.value = ''
    mobile.value = ''
    token.value = ''
  }

  return {
    id,
    mobile,
    token,
    setUserInfo,
    clearUserInfo
  }
}) 