/**
 * 全局权限校验
 */
import router from "@/router";
import {useLoginUSerStore} from "@/stores/counter.ts";
import {message} from "ant-design-vue";

router.beforeEach((to, from, next) => {
    const toUrl = to.fullPath;
//得到登录用户信息
   const loginUserStore = useLoginUSerStore();
    const loginUser = loginUserStore.loginUser;
    if(toUrl.startsWith('/admin')){
        if(!loginUser||loginUser.userRole!==1){
            message.error('没有权限')
            next(`/user/login?redirect=${to.fullPath}`)
            return
        }
    }
    next()

});