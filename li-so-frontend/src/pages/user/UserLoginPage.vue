<template>
  <div id="userLoginPage">
    <h2 class="title">
      用户登录
    </h2>
    <a-form
        labelAlign="left"

        style="max-width: 480px; margin: 0 auto"
        :model="formState"
        name="basic"
        :label-col="{ span: 4 }"
        :wrapper-col="{ span: 20 }"
        autocomplete="off"
        @finish="handleSubmit"
        @finishFailed="onFinishFailed"
    >
      <a-form-item
          label="账号"
          name="username"
          :rules="[{ required: true, message: 'Please input your username!' }]"
      >
        <a-input
            v-model:value="formState.username"
            placeholder="请输入账号"
        />
      </a-form-item>

      <a-form-item
          label="密码"
          name="password"
          :rules="[{ required: true, message: 'Please input your password!' },{min:8,message:'密码长度不能小于8位'}]"
      >
        <a-input-password v-model:value="formState.password"
                          placeholder="请输入密码"
        />
      </a-form-item>

      <a-form-item name="remember" :wrapper-col="{ offset: 4, span: 20 }">

        <a-row>
          <a-checkbox v-model:checked="formState.remember">
            记住我
          </a-checkbox>
          <div class="tips">
            没有账号？
            <RouterLink to="/user/register">去注册</RouterLink>
          </div>
        </a-row>
      </a-form-item>
      <a-form-item :wrapper-col="{ offset :4,span: 20 }">
        <a-button type="primary" html-type="submit" style="width: 100%">登录</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>
<script lang="ts" setup>
import {reactive} from 'vue';
import {userLogin} from "@/api/user.ts";
import {useLoginUSerStore} from "@/stores/counter.ts";
import {message} from "ant-design-vue";
import {useRouter} from "vue-router";

const router = useRouter();

interface FormState {
  username: string
  password: string
  remember: boolean
}

const formState = reactive<FormState>({
  username: '',
  password: '',
  remember: true,
});
const loginUserStore = useLoginUSerStore();
const handleSubmit = async (values: any) => {
  const res = await userLogin(values)
  //登录成功，保存登录态到全局状态
  if (res.data.code === 0 && res.data.data) {
    await loginUserStore.fetchLoginUser()
    message.success('登录成功')
    router.push(
        {
          path: '/',
          replace: true,
        }
    );
  } else {
    message.error("注册失败" + res.data.message)
  }
};

const onFinishFailed = (errorInfo: any) => {
  console.log('Failed:', errorInfo)
};
</script>
<style scoped>
#userLoginPage .title {
  text-align: center;
  margin-bottom: 20px;

}

#userLoginPage .tips {
  margin-left: auto;
}
</style>

