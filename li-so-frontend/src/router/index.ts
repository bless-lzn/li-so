import {createRouter, createWebHistory} from 'vue-router'
import HomePage from "@/pages/HomePage.vue";
import UserLoginPage from "@/pages/user/UserLoginPage.vue";
import UserRegisterPage from "@/pages/user/UserRegisterPage.vue";
import UserManagerPage from "@/pages/admin/UserManagerPage.vue";
import IndexPage from "@/pages/IndexPage.vue";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'home',
            component: HomePage,
        },
        {
            path: '/user/login',
            name: 'login',
            component: UserLoginPage

        },
        {
            path: '/user/register',
            name: 'register',
            component: UserRegisterPage
        },
        {
            path: '/admin/userManage',
            name: 'userManage',
            component: UserManagerPage
        },
        {
            path:'/user/search',
            name:'userSearch',
            component:IndexPage,
            children:[
                {
                    path:'/user/search/:category',
                    name:'userSearchCategory',
                    component:IndexPage
                }
            ]
        },
    ],
})

export default router
