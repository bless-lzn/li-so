<<template>
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
{{JSON.stringify(searchParams)}}
<!--    tab栏目-->
    <a-tabs v-model:activeKey="activeKey" @change="onTabChange">
      <a-tab-pane key="post" tab="文章"><PostList/></a-tab-pane>
      <a-tab-pane key="picture" tab="图片" ><PictureList/></a-tab-pane>
      <a-tab-pane key="user" tab="用户"><UserList/></a-tab-pane>
    </a-tabs>
  </div>
</template>

<script setup lang="ts">
import {onMounted, ref, watchEffect} from "vue";
import {useRoute, useRouter} from "vue-router";
import PostList from "@/components/PostList.vue";
import PictureList from "@/components/PictureList.vue";
import UserList from "@/components/UserList.vue";
const router = useRouter();
const route=useRoute();
const activeKey=ref<string>(route.params.category)
const initSearchParams=ref({
  text: route.query.text,
  pageSize:10,
  pageNum:1
})
const searchParams=ref<any>(initSearchParams.value)

// watchEffect(()=>{
//   console.log("监听")
//   searchParams.value.text
// })
const onSearch=()=>{
  // console.log(route.params.category)
  router.push({
    query:searchParams.value
  })
}
const onTabChange=(key:string)=>{
  router.push({
    path: `/user/search/${key}`,
    query: searchParams.value
  })
}
</script>

<style scoped>
#indexPage {
  width: 720px;
  margin: 0 auto;
}
</style>
