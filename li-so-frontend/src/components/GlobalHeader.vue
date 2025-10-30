<template>
  <div id="globalHeader">
    <a-row :wrap="false">
      <a-col flex="120px">
        <div class="title-bar">
          <img class="logo" src="../assets/logo.svg" alt="#">
          <div class="title">用户中心</div>
        </div>
      </a-col>
      <a-col flex="auto">
        <a-menu v-model:selectedKeys="current" mode="horizontal" :items="items" @click="doMenuClick"/>
      </a-col>
      <a-col flex="80px">
        <div class="user-login-status">
          <div v-if="loginUserStore.loginUser.userName">{{ JSON.stringify(loginUserStore.loginUser.userName) }}</div>
          <div v-else>        <!--  登录按钮-->
            <a-button type="primary" href="/user/login">登录</a-button>
          </div>
        </div>
      </a-col>
    </a-row>
  </div>
</template>
<script lang="ts" setup>
import {h, ref} from 'vue';
import {HomeOutlined, CrownOutlined} from '@ant-design/icons-vue';
import type {MenuProps} from 'ant-design-vue';
import {useRouter} from "vue-router";
import {useLoginUSerStore} from "@/stores/counter.ts";

const loginUserStore = useLoginUSerStore();
const current = ref<string[]>(['/']);
const router = useRouter();
router.afterEach((to) => {
  current.value = [to.path];
})

const doMenuClick = (e: any) => {
  router.push(e.key);
};

const items = ref<MenuProps['items']>([
  {
    key: '/',
    icon: () => h(HomeOutlined),
    label: '主页',
    title: '主页',
  },
  {
    key: "/admin/userManager",
    icon: () => h(CrownOutlined),
    label: '用户管理',
    title: '用户管理',
  },
  {
    key: '/admin/articleManage',
    label: '文章管理',
    title: '文章管理',
  },
  {
    key: 'alipay',
    label: h('a', {href: 'https://antdv.com', target: '_blank'}, 'Navigation Four - Link'),
    title: '其他',
  },
]);
</script>
<style scoped>
#globalHeader .title-bar {
  display: flex;
  align-items: center;
}

#globalHeader .title-bar .logo {
  width: 24px;
  /*  height: 32px;*/
  margin-right: 12px;
}

#globalHeader .title-bar .title {
  color: black;
  font-size: 18px;
}
</style>

