<script setup>
import {useUserStore} from '@/stores/user'

const userStore = useUserStore()

console.log(userStore.id)
console.log(userStore.mobile)
console.log(userStore.token)
</script>

<template>
  <main>
    <h1>This is an home page</h1>

    <el-button type="primary">Primary</el-button>
  </main>
</template>
