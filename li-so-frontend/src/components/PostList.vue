<template>
  <a-list item-layout="horizontal" :data-source="props.postList">
    <template #renderItem="{ item }">
      <a-list-item style="background-color: white;margin-bottom: 20px">
        <a-list-item-meta
            :description=item.content
        >
          <template #title>
            <a href="https://www.antdv.com/">{{ item.title }}</a>
          </template>
          <template #avatar>
            <a-avatar :src="favicon" />
          </template>
        </a-list-item-meta>
      </a-list-item>
    </template>
  </a-list>
</template>
<script lang="ts" setup>
import {withDefaults} from "vue";
import favicon from "../../public/favicon.ico"
const props=withDefaults(defineProps<{postList:API.PostVO[]}>(),{
  postList:()=>[]
})
</script>

