<template>

    <ContentFieldCom>
        <div class="ui middle aligned container stacked grid">
            <div class="ui vertical segment">

                <!--博客-->
                <div class="ui padded segment m-opacity my-blog-div" v-for="blog in blog_list.records" :key="blog.id">

                    <div class="ui mobile reversed stackable grid  m-margin-top-large" @click="clickBlog(blog.id)">
                        <!--博文信息-->
                        <div class="eleven wide column">
                            <h3 class="ui header" >
                                <span class="m-black m-title-font" style="cursor: pointer"> {{blog.title}} </span>
                                <span class="ui mini blue basic button" style="margin-left: 10px" v-if="blog.top">置顶</span>
                            </h3>
                            <p class="m-text m-margin-top-max" style="min-width: 50vw;" >{{ blog.overview }}</p>
                            <div class="ui m-margin-top-max stackable grid">
                                <div class="eleven wide column">
                                    <div class="ui mini horizontal link list">
                                        <div class="item">
                                            <img src="@/assets/img/xiu.jpg" alt="" class="ui avatar image">
                                            <div class="content"><a href="#" class="header" >xiu du</a></div>
                                        </div>
                                        <div class="item">
                                            <i class="calendar icon"></i><span> {{blog.updateTime}} </span>
                                        </div>
                                        <div class="item">
                                            <i class="eye icon"></i> <span>{{ blog.views }}</span>
                                        </div>
                                        <!--                                        <div class="item">-->
                                        <!--                                            <i class="comment outline icon"></i> <span>2222</span>-->
                                        <!--                                        </div>-->
                                    </div>
                                </div>
                                <div class="right aligned five wide column">
                                    <a href="#" target="_blank" class="ui teal basic label m-padded-tiny m-text-thin">{{ blog.typeName }}</a>
                                </div>
                            </div>
                        </div>
                        <!--博文图片-->
                        <div class="five wide column">
                            <img :src="blog.firstPicture" alt="" class="ui rounded image" style="width: 400px; height: 200px">
                        </div>

                    </div>
                </div>

                <!-- 分页-->
                <div class="ui bottom segment m-opacity stackable grid">
                    <div class="three wide column center aligned">
                        <div class="ui teal basic button item" @click="pageTurning(blog_page.pre)">上一页</div>
                    </div>
                    <div class="ten wide column center aligned" style="margin: auto">
                        <p> <span> {{ blog_page.current }} </span> / <span> {{ blog_page.total }} </span> </p>
                    </div>
                    <div class="three wide column center aligned">
                        <div class="ui teal basic button item" @click="pageTurning(blog_page.next)">下一页</div>
                    </div>
                </div>
            </div>

        </div>
    </ContentFieldCom>
</template>

<script>
import ContentFieldCom from "@/components/ContentFieldCom";
import {onMounted, reactive} from "vue";
import axios from "axios";
import router from "@/router";
import {useRoute} from "vue-router";
export default {
    name: "IndexView",
    components: {ContentFieldCom},
    setup() {
        const route = useRoute();
        const blog_list = reactive({
            records : null,
        });
        const blog_page = reactive({
            next : 0,
            pre : 0,
            current : 0,
            total : 0,
        });
        const query_info = reactive({
            page_num: 1,
        })

        onMounted(() => {
            document.title = "首页";
            if(route.query.page_num) query_info.page_num = route.query.page_num;
            getBlogList(query_info.page_num);
        })

        /**
         * @description: 跳转上一页下一页（将参数显示在url上）
         * @param {String} page_num
         * @return void
         */
        const pageTurning = (page_num) => {
            const routeUrl = router.resolve({
                name: 'index',
                query: {
                    page_num: page_num
                }
            })
            window.open(routeUrl.href, '_self');
        }

        /**
         * @description: 通过点击博客跳转页面
         * @param {number} blog_id
         * @return {void} 跳转至博客页面
         */
        const clickBlog = (blog_id) => {
            let routeUrl = router.resolve({
                name: 'blog',
                query: {
                    details: blog_id
                }
            })
            window.open(routeUrl.href, '_blank');
        }

        /**
         * @description: API 获取博客列表
         * @param {string | LocationQueryValue[]} page_num
         * @return blog_list(博客列表), blog_page(页数信息)
         */
        const getBlogList = (page_num) => {
            axios.get(process.env.VUE_APP_API_BASE_URL + "/index", {
                params: {
                    "pageNum": page_num,
                },
                headers: {
                    'Content-Type': "application/x-www-form-urlencoded",
                },
            }).then(resp =>  {
                if(resp.data.error === "success") {
                    const data = resp.data.data;
                    blog_list.records = data.records;
                    blog_page.pre = data.pagePre;
                    blog_page.next = data.pageNext;
                    blog_page.current = data.pageCurrent;
                    blog_page.total = data.pageTotal;
                }
            }).catch(() => {router.push({name:'500'})})
        }


        return {
            blog_list,
            blog_page,

            getBlogList,
            pageTurning,
            clickBlog
        }
    }
}
</script>

<style scoped>
.my-blog-div:hover {
    background-color: #F0F0F2;
}
.my-blog-div {
    background-color: #FFFFFF;
}

</style>