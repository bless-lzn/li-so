<template>
  <div id="userManagerPage">
    <a-input-search
        v-model:value="searchValue"
        placeholder="输入用户名搜索"
        enter-button="搜索"
        size="large"
        @search="onSearch"
        style="max-width: 320px; margin-bottom: 20px"
    />
    <a-table :columns="columns" :data-source="data">

      <template #bodyCell="{ column, record }">
        <template v-if="column.dataIndex === 'avatarUrl'">
          <a-image :src="record.avatarUrl" :width="120"></a-image>
        </template>
        <template v-else-if="column.dataIndex === 'userRole'">
          <div v-if="record.userRole===1">
            <a-tag color="green">管理员</a-tag>
          </div>
          <div v-else>
            <a-tag color="blue">普通用户</a-tag>
          </div>
        </template>

        <template v-else-if="column.dataIndex === 'creatTime'">
          {{ dayjs(record.creatTime).format('YYYY-MM-DD HH:mm:ss') }}
        </template>

        <template v-else-if="column.key === 'action'">
          <!--        <a-button type="primary" @click="handleEdit(record)">编辑</a-button>-->
          <a-button danger @click="doDelete(record.id)">删除</a-button>
        </template>
      </template>
    </a-table>
  </div>

</template>
<script lang="ts" setup>
import {SmileOutlined, DownOutlined} from '@ant-design/icons-vue';
import {ref} from "vue";
import {deleteUser, searchUsers} from "@/api/user.ts";
import {message} from "ant-design-vue";
import dayjs from "dayjs";
const searchValue = ref("");
const columns = [
  {
    title: "id",
    dataIndex: "id",
  },
  {
    title: "用户名",
    dataIndex: "username",
  },
  {
    title: "账号",
    dataIndex: "userAccount",
  },
  {
    title: "头像",
    dataIndex: "avatarUrl",
  },
  {
    title: "性别",
    dataIndex: "gender",
  },
  {
    title: "创建时间",
    dataIndex: "createTime",
  },
  {
    title: "用户角色",
    dataIndex: "userRole",
  },
  {
    title: "操作",
    key: "action",
  },
];

// const onSearch=async (username: any) => {
//   const res = await searchUsers(username);
//   if (res.data.code === 0&&res.data.data) {
//     data.value = res.data.data;
//   }
// };
const onSearch=()=>{
  fetchData(searchValue.value);
}

// const data = [
//   {
//     key: '1',
//     name: 'John Brown',
//     age: 32,
//     address: 'New York No. 1 Lake Park',
//     tags: ['nice', 'developer'],
//   }]


const data = ref([]);
//获取数据
const fetchData = async (username="") => {//username=""不传递默认为空字符串
  const res = await searchUsers(username);
  if (res.data.code === 0 && res.data.data) {
    data.value = res.data.data;
  } else {
    message.error("获取数据失败" + res.data.message);
  }
}
fetchData();

// 删除数据
const doDelete = async (id: string) => {
  if (!id) {
    return;
  }
  const res = await deleteUser(id);
  if (res.data.code === 0) {
    message.success("删除成功");
  } else {
    message.error("删除失败");
  }
};

</script>

