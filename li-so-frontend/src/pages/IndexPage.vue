<
<template>
  <div id="indexPage">
    <!--    搜索条  点击搜索框将搜索的内容填充到url上   监听url的改变-->
    <a-input-search
        v-model:value="searchParams.text"
        placeholder="input search text"
        enter-button="Search"
        size="large"
        @search="onSearch"
        style="width: 100%;margin-bottom: 20px"
    />
    {{ JSON.stringify(searchParams) }}
    <!--    tab栏目-->
    <a-tabs v-model:activeKey="activeKey" @change="onTabChange">
      <a-tab-pane key="post" tab="文章">
        <PostList :postList="postList"/>
      </a-tab-pane>
      <a-tab-pane key="picture" tab="图片">
        <PictureList :picture-list="pictureList"/>
      </a-tab-pane>
      <a-tab-pane key="user" tab="用户">
        <UserList :userList="userList"/>
      </a-tab-pane>
    </a-tabs>
  </div>
</template>

<script setup lang="ts">
import {onMounted, ref, watch, watchEffect} from "vue";
import {useRoute, useRouter} from "vue-router";
import PostList from "@/components/PostList.vue";
import PictureList from "@/components/PictureList.vue";
import UserList from "@/components/UserList.vue";
import {editPostUsingPost, listPostByPageUsingPost} from "@/api/postController.ts";
import {listUserVoByPageUsingPost} from "@/api/userController.ts";
import {listPictureByPageUsingPost} from "@/api/pictureController.ts";
import {searchAllUsingPost} from "@/api/searchController.ts";
import {message} from "ant-design-vue";

const router = useRouter();
const route = useRoute();
const activeKey = ref<string>(route.params.category)
const initSearchParams = ref({
  text: route.query.text,
  pageSize: 10,
  pageNum: 1
})

const searchParams = ref<any>(initSearchParams.value)
// watchEffect(()=>{
//   console.log("监听")
//   searchParams.value.text
// })
const onSearch = () => {
  // console.log(route.params.category)
  router.push({
    query: searchParams.value
  })
  loadData()
}
const onTabChange = (key: string) => {
  router.push({
    path: `/user/search/${key}`,
    query: searchParams.value,
  })
}

const postList = ref<API.PostVO[]>([]);
const userList = ref<API.UserVO[]>([]);
const pictureList = ref<API.Picture[]>([]);

const loadData = async () => {
  const query = {
    ...searchParams.value,
    searchText: searchParams.value.text,
    type: activeKey.value
  }
  const type = query.type
  //对总接口进行查询
  // if (!type) {
  //   message.error("类别为空")
  //   return
  // }
  const res = await searchAllUsingPost(query)
  if (res.data.code === 0 && res.data.data) {

    if (type === "post") {
      postList.value = res.data.data?.postVOList
    } else if (type === "user") {
      userList.value = res.data.data?.userVOList
    } else if (type === "picture") {
      pictureList.value = res.data.data?.pictureList
    } else {
      postList.value = res.data.data?.postVOList
      userList.value = res.data.data?.userVOList
      pictureList.value = res.data.data?.pictureList
      console.log("没有匹配的")
    }
  }
}
// const userQuery={
//   ...searchParams.value,
//   userName:searchParams.value.text
// }
// const getUserData = async () => {
//   const res = await listUserVoByPageUsingPost(userQuery)
//   console.log(res.data.data?.records)
//   if (res.data.code === 0 && res.data.data) {
//     userList.value = res.data.data?.records
//   } else {
//     console.log(userList.value)
//   }
// }

// const postQuery={
//   ...searchParams.value,
//   searchText:searchParams.value.text
// }
// // Promise.all([getPostData(),getUserData(),getPictureData()])
// const getPostData = async () => {
//   const res = await listPostByPageUsingPost(postQuery)
//   console.log(res.data.data?.records)
//   if (res.data.code === 0 && res.data.data) {
//     postList.value = res.data.data?.records
//   } else {
//     console.log(postList.value)
//   }
// }
// const pictureQuery={
//   ...searchParams.value,
//   searchText:searchParams.value.text
// }
// const getPictureData = async () => {
//   const res = await listPictureByPageUsingPost(pictureQuery)
//   console.log(res.data.data?.records)
//   if (res.data.code === 0 && res.data.data) {
//     pictureList.value = res.data.data?.records
//   } else {
//     console.log(pictureList.value)
//   }
// }
onMounted(() => {
  // getPostData()
  // getUserData()
  // getPictureData()
  loadData()
})
watch([activeKey, searchParams], ([newKey, newParams]) => {
  // getPostData()
  // getUserData()
  // getPictureData()
  loadData()
})

</script>

<style scoped>
#indexPage {
  width: 720px;
  margin: 0 auto;
}
</style>
