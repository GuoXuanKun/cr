import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useUserStore = defineStore('user', () => {
  const id = ref(localStorage.getItem('userId') || '')
  const mobile = ref(localStorage.getItem('userMobile') || '')
  const token = ref(localStorage.getItem('userToken') || '')

  const setUserInfo = (userInfo) => {
    id.value = userInfo.id
    mobile.value = userInfo.mobile
    token.value = userInfo.token

    localStorage.setItem('userId', id.value);
    localStorage.setItem('userMobile', mobile.value);
    localStorage.setItem('userToken', token.value);
  }

  const clearUserInfo = () => {
    id.value = ''
    mobile.value = ''
    token.value = ''

    localStorage.removeItem('userId');
    localStorage.removeItem('userMobile');
    localStorage.removeItem('userToken');
  }

  return {
    id,
    mobile,
    token,
    setUserInfo,
    clearUserInfo
  }
})
