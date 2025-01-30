<template>
  <div class="dashboard">
    <div class="cards-grid">
      <el-card
        v-for="(card, index) in cardData"
        :key="index"
        shadow="hover"
        class="data-card"
      >
        <template #header>
          <div class="card-header">
            <span>{{ card.title }}</span>
            <el-tag :type="card.tag.type" effect="plain">{{ card.tag.text }}</el-tag>
          </div>
        </template>
        <div class="card-body">
          <div class="number">{{ card.value }}</div>
          <div class="compare">
            <span class="label">{{ card.compare.label }}</span>
            <span :class="['value', card.compare.trend]">
              <el-icon>
                <CaretTop v-if="card.compare.trend === 'up'" />
                <CaretBottom v-else />
              </el-icon>
              {{ card.compare.value }}%
            </span>
          </div>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script setup>
import { CaretTop, CaretBottom } from '@element-plus/icons-vue'

defineOptions({
  name: 'Dashboard'
})

// 卡片数据
const cardData = [
  {
    title: '总用户数',
    value: '1,234',
    tag: { type: 'success', text: '月' },
    compare: { label: '同比上月', value: 12, trend: 'up' }
  },
  {
    title: '今日访问',
    value: '234',
    tag: { type: 'warning', text: '日' },
    compare: { label: '同比昨日', value: 5, trend: 'down' }
  },
  {
    title: '订单数',
    value: '89',
    tag: { type: 'info', text: '周' },
    compare: { label: '同比上周', value: 8, trend: 'up' }
  },
  {
    title: '活跃度',
    value: '92%',
    tag: { type: 'danger', text: '实时' },
    compare: { label: '同比昨日', value: 3, trend: 'up' }
  }
]
</script>

<style scoped>
/* 使用 CSS 变量统一管理主题色 */
:root {
  --primary-color: #409EFF;
  --success-color: #67c23a;
  --warning-color: #e6a23c;
  --danger-color: #f56c6c;
  --info-color: #909399;
}

.dashboard {
  display: grid;
  gap: 20px;
  padding: 20px;
}

@media screen and (max-width: 768px) {
  .dashboard {
    gap: 10px;
    padding: 10px;
  }
}

/* 添加加载动画 */
.data-card {
  animation: card-in 0.3s ease-out;
}

@keyframes card-in {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.data-card {
  margin-bottom: 20px;
  transition: all 0.3s;
}

.data-card:hover {
  transform: translateY(-4px);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.card-body {
  padding: 20px 0;
}

.number {
  font-size: 36px;
  font-weight: 500;
  color: #303133;
  line-height: 1;
  margin-bottom: 16px;
}

.compare {
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
}

.label {
  color: #909399;
}

.value {
  display: flex;
  align-items: center;
}

.value.up {
  color: #67c23a;
}

.value.down {
  color: #f56c6c;
}

.el-icon {
  margin-right: 4px;
}

.cards-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
}

@media screen and (max-width: 1200px) {
  .cards-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media screen and (max-width: 768px) {
  .cards-grid {
    grid-template-columns: 1fr;
  }

  .dashboard {
    padding: 10px;
  }

  .number {
    font-size: 28px;
  }
}
</style>
