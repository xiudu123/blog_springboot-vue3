<template>
    <ContentFieldCom>

        <div class="ui segment center aligned m-opacity">
            共 <h2 class="ui orange header m-text-thin m-inline-block">{{ blog_total }}</h2> 篇博客
        </div>

    <div class="ui big fluid vertical menu m-opacity" v-for="year in blog_list.years" :key="year">
        <h2 class="ui header m-margin-tb-tiny">
            <div style="text-align: center">{{ year }}</div>
            <div style="text-align: right">
                共 <span style="color: orange">{{ blog_list.records[year].length }}</span> 篇 &nbsp;&nbsp;&nbsp;
            </div>
        </h2>

        <div class="item m-padded my-blog-div" v-for="blog in blog_list.records[year]" :key="blog.id" @click="getBlog(blog.id)">

                <span>
                    <i class="mini teal circle icon"></i>&nbsp;&nbsp; <span style="cursor: pointer;">{{blog.title}}</span>
                </span>
                <div class="ui mini blue basic button" style="margin-left: 10px; cursor: default" v-if="blog.top">置顶</div>
                <div class="ui teal basic left pointing label m-padded-tb-tiny">
                    <span >{{blog.month}}</span>
                    &nbsp; 月 &nbsp;
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
        const blog_list = reactive({
            records : null,
            years: null,
        });
        const blog_total = ref("");

        onMounted(() => {
            document.title = "时间轴";
            getBlogList()
        })

        /**
         * @description: API 获取博客列表
         * @return blog_list(博客列表), blog_total(博客总数)
         */
        const getBlogList = () => {
            axios.get(process.env.VUE_APP_API_BASE_URL + "/archives")
                .then(resp => {
                    blog_list.records = resp.data.data.blogs;
                    blog_list.years = resp.data.data.years;
                    blog_total.value = resp.data.data.blotTotal;
                }).catch(() => {
                    router.push({name:'500'})
                }
            );
        }
        /**
         * @description: 通过点击博客跳转到博客页面
         * @param {number} blog_id
         * @return void 跳转至博客页面
         */
        const getBlog = (blog_id) => {
            let routeUrl = router.resolve({
                name: 'blog',
                query: {
                    details: blog_id
                }
            })
            window.open(routeUrl.href, '_blank');
        }

        return {
            blog_list,
            blog_total,

            getBlog
        }
    }
}

</script>

<style scoped>
.my-blog-div:hover {
    background-color: #F0F0F2 !important;
}
.my-blog-div {
    background-color: #FFFFFF !important;
}
</style>
