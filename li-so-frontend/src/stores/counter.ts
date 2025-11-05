import {ref, computed} from 'vue'
import {defineStore} from 'pinia'

import myAxios from "@/request.ts";
import {getLoginUserUsingGet} from "@/api/userController.ts";

export const useLoginUserStore = defineStore('loginUser', () => {
    const loginUser = ref({
        userName: '未登录',
        userRole: 0
    })
    async function fetchLoginUser() {
       const res=await getLoginUserUsingGet()
        if(res.data.code===0&&res.data.data){
            loginUser.value=res.data.data
        }
    }
    //单独设置信息
    function setLoginUser(newLoginUser: any) {
        loginUser.value = newLoginUser
    }
    /**
     * 用户注册
     * @param params
     */

    return {
        loginUser,
        fetchLoginUser,
        setLoginUser
    }
})
