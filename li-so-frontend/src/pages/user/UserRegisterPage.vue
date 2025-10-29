<template>
  <div id="userRegisterPage">
    <h2 class="title">
      用户注册
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
      <a-form-item
          label="确认密码"
          name="checkPassword"
          :rules="[{ required: true, message: 'Please input your password!' },{min:8,message:'密码长度不能小于8位'}]"
      >
        <a-input-password v-model:value="formState.checkPassword"
                          placeholder="请输入确认密码"
        />
      </a-form-item>
      <a-form-item :wrapper-col="{ offset: 12, span: 12 }">
        <a-button type="primary" html-type="submit">登录</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>
<script lang="ts" setup>
import {reactive} from 'vue';
import {userRegister} from "@/api/user.ts";
import {message} from "ant-design-vue";
import {useRouter} from "vue-router";

const router = useRouter();
//类型
interface FormState {
  username: string
  password: string
  checkPassword: string
}
//默认值
const formState = reactive<FormState>({
  username: '',
  password: '',
  checkPassword: ''
});

const handleSubmit = async (values: any) => {
  if(formState.password !== formState.checkPassword)
  {
    message.error("密码不一致")
    return
  }

  const res = await userRegister(values)
  //登录成功，保存登录态到全局状态
  if (res.data.code === 0 && res.data.data) {
    message.success('注册成功')
    router.push(
        {
          path: '/user/login',
          replace: true,
        }
    )
  }else{
    message.error("注册失败"+res.data.message)
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

</style>

