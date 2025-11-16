<template>
  <a-list item-layout="horizontal" :data-source="props.userList">
    <template #renderItem="{ item }">
      <a-list-item>
        <a-card hoverable style="width: 240px">
          <template #cover>
            <img alt="example" :src=item.userAvatar />
          </template>
          <a-card-meta :title=item.userName>
            <template #description>{{item.userProfile}}</template>
          </a-card-meta>
        </a-card>
      </a-list-item>
    </template>
  </a-list>
</template>
<script lang="ts" setup>
import {withDefaults} from "vue";
import favicon from "../../public/favicon.ico"
const props=withDefaults(defineProps<{userList:API.UserVO[]}>(),{
  userList:()=>[]
})
</script>

