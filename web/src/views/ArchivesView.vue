<template>
<ContentFieldCom>

    <div class="ui segment center aligned m-opacity">
        共 <h2 class="ui orange header m-text-thin m-inline-block">{{ blog_total }}</h2> 篇博客
    </div>



    <div class="ui big fluid vertical menu m-opacity" v-for="(blogs, year) in blog_list.records" :key="year">
        <h2 class="ui header m-margin-tb-tiny">
            <div  align="center">{{ year }}</div>
            <div style="text-align: right">
                共 <span style="color: orange">{{ blogs.length }}</span> 篇&nbsp;&nbsp;&nbsp;
            </div>
        </h2>

        <div class="item m-padded" v-for="blog in blogs" :key="blog.id" @click="getBlog(blog.id)">
                <span style="cursor: pointer;">
                    <i class="mini teal circle icon"></i>&nbsp;&nbsp; <span >{{blog.title}}</span>
                </span>
            <div class="ui mini blue basic button" style="margin-left: 10px; cursor: default" v-if="blog.top">置顶</div>
            <div class="ui teal basic left pointing label m-padded-tb-tiny">
                <span >{{blog.month}}</span>
                月
                <span >{{blog.day}}</span>
            </div>
        </div>

    </div>
</ContentFieldCom>
</template>

<script>
import ContentFieldCom from "@/components/ContentFieldCom";
import {onMounted, reactive, ref} from "vue";
import axios from "axios";
import router from "@/router";
export default {
    name: "ArchivesView",
    components: {ContentFieldCom},

    setup() {
        const blog_list = reactive({});
        const blog_total = ref("");
        const getBlogList = () => {
            axios.get(process.env.VUE_APP_API_BASE_URL + "/archives")
                .then(resp => {
                    blog_list.records = resp.data.data.blogs;
                    blog_total.value = resp.data.data.blotTotal;

                }).catch();
        }

        const getBlog = (blog_id) => {
            let routeUrl = router.resolve({
                name: 'blog',
                query: {
                    id: blog_id
                }
            })
            window.open(routeUrl.href, '_blank');
        }

        onMounted(() => {
            getBlogList()
        })

        return {
            blog_list,
            blog_total,
            getBlog
        }
    }
}

</script>

<style scoped>

</style>