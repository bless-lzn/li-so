import myAxios from "@/request.ts";


export const getCurrentUser=async ()=>{
    return  await myAxios.request(
        {
            url:"/api/user/current",
            method:"GET",
        }
    )
}
/**
 * 用户注册接口
 * @param param
 */

export const userRegister = async (params: any) => {
    return myAxios.request({
        url: "/api/user/register",
        method: "POST",
        data: params,
    });
};

/**
 * 用户登录
 * @param params
 */
export const userLogin = async (params: any) => {
    return myAxios.request({
        url: "/api/user/login",
        method: "POST",
        data: params,
    });
};

/**
 * 用户注销
 * @param params
 */
export const userLogout = async (params: any) => {
    return myAxios.request({
        url: "/api/user/logout",
        method: "POST",
        data: params,
    });
};

/**
 * 获取用户列表
 * @param username
 */
export const searchUsers = async (username: any) => {
    return myAxios.request({
        url: "/api/user/search",
        method: "GET",
        params: {
            username,
        },
    });
};

/**
 * 删除用户
 * @param id
 */
export const deleteUser = async (id: string) => {
    return myAxios.request({
        url: "/api/user/delete",
        method: "POST",
        data: id,
        // 关键点：要传递 JSON 格式的值
        headers: {
            "Content-Type": "application/json",
        },
    });
};

