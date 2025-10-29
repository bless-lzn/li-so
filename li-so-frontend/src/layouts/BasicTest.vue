<template>
  <div id="basicLayout">
    <a-layout style="min-height: 100vh">
      <a-layout-header class="header">
        <GlobalHeader />
      </a-layout-header>
      <a-layout>
        <a-layout-content class="content">
          <RouterView />
        </a-layout-content>
      </a-layout>
      <a-layout-footer class="footer">
        鱼皮云图库 ©2023 Created by 程序员鱼皮
      </a-layout-footer>
    </a-layout>
  </div>
</template>

<script setup lang="ts">
import GlobalHeader from '@/components/GlobalHeader.vue'
</script>

<style scoped>
#basicLayout .footer {
  background-color: #efefef;
  bottom: 0;
  text-align: center;
  left: 0;
  right: 0;
  padding: 16px;
  position: fixed;
}

#basicLayout .content {
  padding: 20px;
  background: linear-gradient(to right, #efefef, #fff);
  margin-bottom: 28px;
}

#basicLayout .header {
  padding-inline: 20px;
  margin-bottom: 16px;
  color: unset;
  background: white;
}
</style>
