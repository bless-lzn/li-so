<template>
  <div id="basicLayout">
    <a-layout style="min-height: 100vh;">
      <a-layout-header class="header">
        <GlobalHeader></GlobalHeader>
      </a-layout-header>
      <a-layout-content class="content">
    <RouterView></RouterView>
      </a-layout-content>
      <a-layout-footer class="footer">Footer</a-layout-footer>
    </a-layout>
  </div>
</template>
<script setup lang="ts">

import GlobalHeader from "@/components/GlobalHeader.vue";
</script>
<style scoped>
#basicLayout .content{
  padding: 16px;
  margin-bottom: 20px;
  background: linear-gradient(to right, #fafafa, #fff);

}
#basicLayout .footer{
  text-align: center;
  background: #efefef;
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  padding: 16px;
}
#basicLayout .header{
  background: white;
  margin-bottom: 16px;
  color: unset;
  padding-inline: 20px;
}

</style>