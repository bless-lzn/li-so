<template>
  <a-list :grid="{ gutter: 16, xs: 1, sm: 2, md: 3, lg: 4, xl: 5, xxl: 6 }" item-layout="horizontal"
          :data-source="props.pictureList">
    <template #renderItem="{ item }">
      <a-list-item style="padding: 0">
        <a-card hoverable>
          <template #cover>
            <img alt="example" :src=item.url style="height: 180px; object-fit: cover"/>
          </template>
          <a-card-meta :title=item.title>
            <!--            <template #description>{{item.title}}</template>-->
          </a-card-meta>
        </a-card>
      </a-list-item>
    </template>
  </a-list>
</template>
<script lang="ts" setup>
import {withDefaults} from "vue";
import favicon from "../../public/favicon.ico"

interface PictureListProps {
  pictureList: API.Picture[]
}

const props = withDefaults(defineProps<PictureListProps>(), {
  pictureList: () => []
})
</script>

